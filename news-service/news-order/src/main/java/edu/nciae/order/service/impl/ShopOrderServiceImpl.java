package edu.nciae.order.service.impl;

import edu.nciae.common.constant.Constants;
import edu.nciae.common.mq.function.RabbitSender;
import edu.nciae.common.redis.utils.RedisUtils;
import edu.nciae.common.utils.DateUtils;
import edu.nciae.order.domain.ShopOrder;
import edu.nciae.order.lock.RedisLock;
import edu.nciae.order.mapper.ShopOrderMapper;
import edu.nciae.order.service.ShopOrderService;
import edu.nciae.order.utils.SnowflakeIdUtils;
import edu.nciae.order.vo.ShopOrderVo;
import edu.nciae.shop.domain.ShopProduct;
import edu.nciae.shop.feign.RemoteShopService;
import edu.nciae.system.domain.SysUser;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

@Service
@Slf4j
public class ShopOrderServiceImpl implements ShopOrderService {
    @Autowired
    private RemoteShopService remoteShopService;
    @Autowired
    private RabbitSender rabbitSender;
    @Autowired
    private RedisUtils redis;
    @Autowired
    private RedisLock redisLock;
    @Autowired
    private ShopOrderMapper shopOrderMapper;


    /**
     * 新增订单
     *
     * @param shopOrderVo 订单信息
     * @return 结果
     */
    @Override
    public  int insertOrder(SysUser sysUser, ShopOrderVo shopOrderVo) {
        // 加锁
        long time = System.currentTimeMillis() + RedisUtils.TIMEOUT;
        if(!redisLock.lock(shopOrderVo.getId().toString(), String.valueOf(time))){
            return -1;
        }
        // 判断用户是否重复下单
        // ShopOrder order = redis.get(Constants.ORDER_USER_PRODUCT + sysUser.getUserId() + "_" + shopOrderVo.getId(), ShopOrder.class);
        // if (order == null) {
        if (true) {
            // 改为查询 redis
            if (remoteShopService.checkStockWithRedis(shopOrderVo.getId())) {
                // 如果缓存数量足够先在redis中减去库存, 需要redis的分布式锁
                ShopProduct shopProduct = redis.get(Constants.PRODUCT_ID + shopOrderVo.getId(), ShopProduct.class);
                shopProduct.setStock(shopProduct.getStock() - 1);
                log.info("shopProduct, {}", shopProduct);
                redis.set(Constants.PRODUCT_ID + shopOrderVo.getId(), shopProduct);
                // 从redis中查询出商品信息
                ShopProduct product = remoteShopService.findProductById(shopOrderVo.getId());
                Long orderid = SnowflakeIdUtils.getNextId();
                ShopOrder shopOrder = new ShopOrder();
                shopOrder.setId(String.valueOf(orderid));
                shopOrder.setUserId(sysUser.getUserId());
                shopOrder.setOrderSn("201912230836");
                shopOrder.setCommentTime(DateUtils.getNowDate());
                shopOrder.setMemberUsername(sysUser.getUserName());
                shopOrder.setTotalAmount(BigDecimal.valueOf(shopOrderVo.getProductQuantity()).multiply(product.getPrice()));
                shopOrder.setPayType(0);
                shopOrder.setSourceType(0);
                shopOrder.setStatus(0);
                shopOrder.setOrderType(1);
                shopOrder.setReceiverPhone(shopOrderVo.getReceiverPhone());
                shopOrder.setReceiverName(shopOrderVo.getReceiverName());
                shopOrder.setReceiverPostCode(shopOrderVo.getReceiverPostCode());
                shopOrder.setReceiverProvince(shopOrderVo.getReceiverProvince());
                shopOrder.setReceiverCity(shopOrderVo.getReceiverCity());
                shopOrder.setReceiverRegion(shopOrderVo.getReceiverRegion());
                shopOrder.setReceiverDetailAddress(shopOrderVo.getReceiverDetailAddress());
                shopOrder.setNote(shopOrderVo.getNote());
                shopOrder.setConfirmStatus(0);
                shopOrder.setDeleteStatus(0);
                shopOrder.setProductId(shopOrderVo.getId());
                // 将订单先加入redis缓存中
                log.info("-----------------------------------------------------");
                Random random = new Random();
                redis.set(Constants.ORDER_USER_PRODUCT + sysUser.getUserId() + "_" + shopOrderVo.getId() + random.nextInt(1000), shopOrder);

                rabbitSender.send("news.queue", shopOrder);
            }

            // return 0;
        }

        // 解锁
        redisLock.unlock(shopOrderVo.getId().toString(), String.valueOf(time));
        return 0;
    }

    /**
     * 保存订单
     * @param shopOrder
     * @return
     */
    @GlobalTransactional(name = "my_test_tx_group")
    @Transactional
    @Override
    public int saveOrder(ShopOrder shopOrder) {
        log.info("保存订单");
        remoteShopService.reduceOneStock(shopOrder.getProductId(), 1);
        return shopOrderMapper.insert(shopOrder);
    }
}

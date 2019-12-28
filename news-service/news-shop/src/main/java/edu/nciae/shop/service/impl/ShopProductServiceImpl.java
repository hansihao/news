package edu.nciae.shop.service.impl;

import com.alibaba.fastjson.JSON;
import edu.nciae.common.constant.Constants;
import edu.nciae.common.core.text.Convert;
import edu.nciae.common.redis.utils.RedisUtils;
import edu.nciae.shop.domain.ShopProduct;
import edu.nciae.shop.mapper.ShopProductMapper;
import edu.nciae.shop.service.ShopProductService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ShopProductServiceImpl implements ShopProductService {
    @Autowired
    private RedisUtils redis;
    @Autowired
    private ShopProductMapper shopProductMapper;

    /**
     * 根据条件分页查询商品列表
     * @param product 商品信息
     * @return
     */
    @Override
    public List<ShopProduct> selectProductList(ShopProduct product) {
        return shopProductMapper.selectProductList(product);
    }

    /**
     * 商品状态修改
     * @param shopProduct 商品信息
     * @return
     */
    @Override
    public int changeStatus(ShopProduct shopProduct) {
        return shopProductMapper.updateShopProduct(shopProduct);
    }

    /**
     * 批量删除商品信息
     * @param ids 需要删除的数据ID
     * @return
     * @throws Exception
     */
    @Override
    public int deleteProductByIds(String ids) throws Exception {
        Long[] productIds = Convert.toLongArray(ids);
        return shopProductMapper.deleteProductByIds(productIds);
    }

    /**
     * 添加商品
     * @param shopProduct
     * @return
     */
    @Override
    public int insertProduct(ShopProduct shopProduct) {
        shopProduct.setDeleteStatus(0);
        shopProduct.setPublishStatus(1);
        return shopProductMapper.insertProduct(shopProduct);
    }

    /**
     * 修改商品信息
     * @param shopProduct
     * @return
     */
    @Override
    public int updateProduct(ShopProduct shopProduct) {
        return shopProductMapper.updateShopProduct(shopProduct);
    }

    /**
     * 根据id查询库存
     * @param id
     * @return
     */
    @Override
    public boolean getStockById(Long id) {
        int stock = shopProductMapper.getStockById(id);
        if (stock < 1) {
            log.info("库存不足");
            throw new RuntimeException("库存不足");
        }
        return true;
    }

    /**
     * 从redis中根据id查询库存
     * @param id
     * @return
     */
    @Override
    public boolean checkStockWithRedis(Long id) {
        ShopProduct shopProduct = redis.get(Constants.PRODUCT_ID + id, ShopProduct.class);
        if (shopProduct.getStock() < 1) {
            log.info("库存不足");
            throw new RuntimeException("库存不足");
        }
        return true;
    }

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    @Override
    public ShopProduct findProductById(Long id) {
        ShopProduct shopProduct = shopProductMapper.selectByPrimaryKey(id);
        return shopProduct;
    }

    /**
     * 根据id查询商品,并且添加至redis
     * @param id
     * @return
     */
    @Override
    public ShopProduct findProductByIdAndCache(Long id) {
        ShopProduct product = redis.get(Constants.PRODUCT_ID + id, ShopProduct.class);
        if (product == null) {
            ShopProduct shopProduct = shopProductMapper.selectByPrimaryKey(id);
            redis.set(Constants.PRODUCT_ID + id, shopProduct);
            return shopProduct;
        }
        return product;
    }

    /**
     * 减少库存
     * @param id
     * @param num
     * @return
     */
    @GlobalTransactional(name = "my_test_tx_group")
    @Transactional
    @Override
    public int reduceStock(Long id, Integer num) {
        log.info("减少库存");
        int i = shopProductMapper.reduceStock(id, num);
        System.out.println("i:" + i);
        return 1 / 0;
    }
}

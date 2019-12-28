package edu.nciae.common.mq.listener;

import edu.nciae.common.utils.SnowflakeIdWorker;
import edu.nciae.order.domain.ShopOrder;
import edu.nciae.order.feign.RemoteOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
// @Component
public class Listener {
    @Autowired
    private RemoteOrderService shopOrderMapper;

    // @KafkaListener(topics = {"order"})
    public void saveOrder(ShopOrder shopOrder){
        log.info("=============================================================");
        // 这里为插入数据库代码
        SnowflakeIdWorker snowFlake = new SnowflakeIdWorker(1, 1);
        shopOrder.setId(String.valueOf(snowFlake.nextId()));

        // shopOrderMapper.insert()
        System.out.println(shopOrder);
    }
}

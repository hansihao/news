package edu.nciae.order.resolver;

import cn.hutool.json.JSONUtil;
import edu.nciae.order.domain.ShopOrder;
import edu.nciae.order.feign.RemoteOrderService;
import edu.nciae.order.service.ShopOrderService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service("newsReceiver")
public class NewsReceiver {
    @Autowired
    private ShopOrderService shopOrderService;

    @SneakyThrows
    @RabbitListener(queues = "news.queue")
    @RabbitHandler
    public void onMessage(String message) {
        log.info("receive message. {}", message);

        ShopOrder shopOrder = JSONUtil.toBean(message, ShopOrder.class);
        // 这里为插入数据库代码
        shopOrderService.saveOrder(shopOrder);
    }
}

package edu.nciae.order.feign;

import edu.nciae.order.domain.ShopOrder;
import edu.nciae.order.feign.factory.RemoteOrderFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order", fallbackFactory = RemoteOrderFallbackFactory.class, contextId = "order")
public interface RemoteOrderService {

    @PostMapping("order/save")
    int save(@RequestBody ShopOrder shopOrder);
}

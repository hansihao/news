package edu.nciae.order.feign.factory;

import edu.nciae.order.domain.ShopOrder;
import edu.nciae.order.feign.RemoteOrderService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteOrderFallbackFactory implements FallbackFactory<RemoteOrderService> {
    @Override
    public RemoteOrderService create(Throwable throwable) {
        log.error(throwable.getMessage());
        return new RemoteOrderService() {
            @Override
            public int save(ShopOrder shopOrder) {
                return 0;
            }
        };
    }
}

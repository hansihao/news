package edu.nciae.shop.feign.factory;

import edu.nciae.shop.domain.ShopProduct;
import edu.nciae.shop.feign.RemoteShopService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteShopFallbackFactory implements FallbackFactory<RemoteShopService> {
    @Override
    public RemoteShopService create(Throwable throwable) {
        log.error(throwable.getMessage());
        return new RemoteShopService() {
            @Override
            public boolean checkStockWithRedis(Long id) {
                return false;
            }

            @Override
            public boolean checkStock(Long id) {
                return false;
            }

            @Override
            public ShopProduct findProductById(Long id) {
                return null;
            }

            @Override
            public int reduceOneStock(Long id, Integer num) {
                return 0;
            }
        };
    }
}

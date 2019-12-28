package edu.nciae.shop.feign;

import edu.nciae.shop.domain.ShopProduct;
import edu.nciae.shop.feign.factory.RemoteShopFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * 商品Feign服务层
 */
@FeignClient(name = "shop", fallbackFactory = RemoteShopFallbackFactory.class, contextId = "product")
public interface RemoteShopService {

    @GetMapping("product/stock/redis/{id}")
    boolean checkStockWithRedis(@PathVariable("id") Long id);

    @GetMapping("product/stock/mysql/{id}")
    boolean checkStock(@PathVariable("id") Long id);

    @GetMapping("product/find/{id}")
    ShopProduct findProductById(@PathVariable("id") Long id);

    @PutMapping("product/stockReduce/{id}/{num}")
    int reduceOneStock(@PathVariable("id") Long id, @PathVariable("num") Integer num);
}

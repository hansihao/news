package edu.nciae.system.feign;

import edu.nciae.system.feign.factory.RemoteMenuFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

/**
 * 菜单 Feign 服务层
 */
@FeignClient(name = "system", fallbackFactory = RemoteMenuFallbackFactory.class, contextId = "user")
public interface RemoteMenuService {
    @GetMapping("menu/perms/{userId}")
    Set<String> selectPermsByUserId(@PathVariable("userId") Integer userId);
}

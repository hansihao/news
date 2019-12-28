package edu.nciae.system.feign;

import edu.nciae.common.domain.R;
import edu.nciae.system.domain.SysUser;
import edu.nciae.system.feign.factory.RemoteUserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户 Feign服务层
 */

@FeignClient(name = "system", fallbackFactory = RemoteUserFallbackFactory.class, contextId = "menu")
public interface RemoteUserService {

    @GetMapping("user/find/{userName}")
    SysUser selectSysUserByUserName(@PathVariable("userName") String userName);

    @PostMapping("user/update/login")
    R updateUserLoginRecord(@RequestBody SysUser user);

    @GetMapping("user/get/{userId}")
    SysUser selectUserById(@PathVariable("userId") Integer userId);
}

package edu.nciae.auth.controller;

import edu.nciae.auth.service.AccessTokenService;
import edu.nciae.auth.service.SysLoginService;
import edu.nciae.auth.vo.LoginVo;
import edu.nciae.common.domain.R;
import edu.nciae.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TokenController {
    @Autowired
    private SysLoginService sysLoginService;
    @Autowired
    private AccessTokenService accessTokenService;

    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {
        // 用户登录
        SysUser user = sysLoginService.login(loginVo.getUsername(), loginVo.getPassword());
        // 获取登录token
        Map<String, Object> token = accessTokenService.createToken(user);
        return R.ok(token);
    }

}

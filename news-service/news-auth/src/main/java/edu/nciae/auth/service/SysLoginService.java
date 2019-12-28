package edu.nciae.auth.service;

import edu.nciae.common.constant.Constants;
import edu.nciae.common.constant.UserConstants;
import edu.nciae.common.enums.UserStatus;
import edu.nciae.common.exception.BaseException;
import edu.nciae.common.log.publish.PublishFactory;
import edu.nciae.common.utils.DateUtils;
import edu.nciae.common.utils.IpUtils;
import edu.nciae.common.utils.ServletUtils;
import edu.nciae.system.domain.SysUser;
import edu.nciae.system.feign.RemoteUserService;
import edu.nciae.system.util.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLoginService {
    @Autowired
    private RemoteUserService userService;

    public SysUser login(String username, String password) {
        if (StringUtils.isAnyBlank(username, password)) {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, "not.null");
            throw new BaseException("user", "登录信息不能为空");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, "user.username.not.match");
            throw new BaseException("user", "登录信息错误");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, "user.password.not.match");
            throw new BaseException("user", "登录信息错误");
        }
        // 查询用户信息
        SysUser user = userService.selectSysUserByUserName(username);
        if (user == null) {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, "user.not.exists");
            throw new BaseException("user", "用户不存在");
        }
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, "user.delete");
            throw new BaseException("user", "用户不存在");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, "user.blocked", user.getRemark());
            throw new BaseException("user", "用户已锁定");
        }
        if (!PasswordUtil.matches(user, password)) {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, "user.password.error");
            throw new BaseException("user", "登录信息错误");
        }
        PublishFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, "user.login.success");
        recordLoginInfo(user);
        return user;

    }

    /**
     * 记录登录信息
     */
    public void recordLoginInfo(SysUser user) {
        user.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        user.setLoginDate(DateUtils.getNowDate());
        userService.updateUserLoginRecord(user);
    }

    public void logout(String loginName) {
        PublishFactory.recordLogininfor(loginName, Constants.LOGOUT, "user.logout.success");
    }

}

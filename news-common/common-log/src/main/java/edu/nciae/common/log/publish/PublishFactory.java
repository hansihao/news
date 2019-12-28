package edu.nciae.common.log.publish;


import edu.nciae.common.constant.Constants;
import edu.nciae.common.log.event.SysLogininforEvent;
import edu.nciae.common.utils.AddressUtils;
import edu.nciae.common.utils.IpUtils;
import edu.nciae.common.utils.ServletUtils;
import edu.nciae.common.utils.spring.SpringContextHolder;
import edu.nciae.system.domain.SysLogininfor;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
public class PublishFactory {

    /**
     * 记录登陆信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     */
    public static void recordLogininfor(final String username, final String status, final String message, final Object... args) {
        HttpServletRequest request = ServletUtils.getRequest();
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(request);
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        SysLogininfor logininfor = new SysLogininfor();
        logininfor.setLoginName(username);
        logininfor.setIpaddr(ip);
        logininfor.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        logininfor.setBrowser(browser);
        logininfor.setOs(os);
        logininfor.setMsg(message);
        // 日志状态
        if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status)) {
            logininfor.setStatus(Constants.SUCCESS);
        } else if (Constants.LOGIN_FAIL.equals(status)) {
            logininfor.setStatus(Constants.FAIL);
        }

        // 发布事件
        SpringContextHolder.publishEvent(new SysLogininforEvent(logininfor));
    }

}

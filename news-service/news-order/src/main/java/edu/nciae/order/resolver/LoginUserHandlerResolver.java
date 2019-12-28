package edu.nciae.order.resolver;

import edu.nciae.common.annotation.LoginUser;
import edu.nciae.common.constant.Constants;
import edu.nciae.system.domain.SysUser;
import edu.nciae.system.feign.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 定制method参数和返回值。
 * 有@LoginUser注解的方法参数，注入当前登录用户
 */
@Configuration
public class LoginUserHandlerResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private RemoteUserService userService;

    /**
     * 判断是否支持要转换的参数类型
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(SysUser.class) &&  methodParameter.hasParameterAnnotation(LoginUser.class);
    }

    /**
     * 当支持后进行相应的转换 做业务操作
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        // 获取用户ID
        Integer userid = Integer.valueOf(request.getHeader(Constants.CURRENT_ID));
        if (userid == null) {
            return null;
        }
        return userService.selectUserById(userid);
    }
}

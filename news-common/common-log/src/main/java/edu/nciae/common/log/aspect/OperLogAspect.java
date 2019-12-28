package edu.nciae.common.log.aspect;

import com.alibaba.fastjson.JSON;
import edu.nciae.common.constant.Constants;
import edu.nciae.common.log.annotation.OperLog;
import edu.nciae.common.log.enums.BusinessStatus;
import edu.nciae.common.log.event.SysOperLogEvent;
import edu.nciae.common.utils.AddressUtils;
import edu.nciae.common.utils.IpUtils;
import edu.nciae.common.utils.ServletUtils;
import edu.nciae.common.utils.StringUtils;
import edu.nciae.common.utils.spring.SpringContextHolder;
import edu.nciae.system.domain.SysOperLog;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 操作日志记录处理
 */

@Aspect
@Slf4j
@Component
@AllArgsConstructor
public class OperLogAspect {

    /**
     * spring事件使用步骤如下:
     *
     * 1.先自定义事件：事件需要继承 ApplicationEvent
     *
     * 2.定义事件监听器: 需要实现 ApplicationListener
     *
     * 3.使用容器对事件进行发布
     */

    // 配置织入点
    @Pointcut("@annotation(edu.nciae.common.log.annotation.OperLog)")
    public void logPointcut() {

    }

    @AfterReturning(pointcut = "logPointcut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e) {
        try {
            // 获得注解
            OperLog controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            // 数据库日志
            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            HttpServletRequest request = ServletUtils.getRequest();
            String ip = IpUtils.getIpAddr(request);
            operLog.setOperIp(ip);
            operLog.setOperUrl(request.getRequestURI());
            operLog.setOperLocation(AddressUtils.getRealAddressByIP(ip));

            String username = URLDecoder.decode(request.getHeader(Constants.CURRENT_USERNAME), "utf-8");
            operLog.setOperName(username);
            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(request.getMethod());
            // 处理设置注解上的参数
            Object[] args = joinPoint.getArgs();
            getControllerMethodDescription(controllerLog, operLog, args);
            // 发布事件
            SpringContextHolder.publishEvent(new SysOperLogEvent(operLog));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * @param log  日志
     * @param operLog 操作日志
     * @param args
     */
    public void getControllerMethodDescription(OperLog log, SysOperLog operLog, Object[] args) {
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据中
            setRequestValue(operLog, args);
        }
    }

    /**
     * 获取请求的参数，放到log中
     * @param operLog 操作日志
     * @param args
     */
    private void setRequestValue(SysOperLog operLog, Object[] args) {
        List<?> param = new ArrayList<>(Arrays.asList(args)).stream().filter(p -> (p instanceof ServletResponse)).collect(Collectors.toList());
        log.debug("args: {}", param);
        String params = JSON.toJSONString(param, true);
        operLog.setOperParam(StringUtils.substring(params, 0, 2000));
    }

    // 是否存在注解，如果存在就获取
    private OperLog getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(OperLog.class);
        }
        return null;
    }

}

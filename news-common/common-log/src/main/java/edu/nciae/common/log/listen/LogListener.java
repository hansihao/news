package edu.nciae.common.log.listen;

import edu.nciae.common.log.event.SysLogininforEvent;
import edu.nciae.common.log.event.SysOperLogEvent;
import edu.nciae.system.domain.SysLogininfor;
import edu.nciae.system.domain.SysOperLog;
import edu.nciae.system.feign.RemoteLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步监听日志事件
 */

@Slf4j
@AllArgsConstructor
@Component
public class LogListener {
    private final RemoteLogService remoteLogService;

    @Async
    @Order
    @EventListener(SysOperLogEvent.class)
    public void listenOperLog(SysOperLogEvent event) {
        log.info("监听到事件");
        SysOperLog sysOperLog = (SysOperLog) event.getSource();
        remoteLogService.insertOperlog(sysOperLog);
        log.info("远程操作日志记录成功：{}", sysOperLog);
    }

    @Async
    @Order
    @EventListener(SysLogininforEvent.class)
    public void listenLoginifor(SysLogininforEvent event) {
        log.info("监听到事件");
        SysLogininfor sysLogininfor = (SysLogininfor) event.getSource();
        remoteLogService.insertLoginlog(sysLogininfor);
        log.info("远程访问日志记录成功：{}", sysLogininfor);
    }

}

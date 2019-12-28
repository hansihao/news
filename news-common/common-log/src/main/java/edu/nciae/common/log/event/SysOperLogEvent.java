package edu.nciae.common.log.event;

import edu.nciae.system.domain.SysOperLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 * 系统日志事件
 */
@Slf4j
public class SysOperLogEvent extends ApplicationEvent {
    public SysOperLogEvent(Object source) {
        super(source);
       log.info("事件入队");
    }
}

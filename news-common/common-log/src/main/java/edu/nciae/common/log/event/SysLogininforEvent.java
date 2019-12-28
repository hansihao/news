package edu.nciae.common.log.event;

import edu.nciae.system.domain.SysLogininfor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 * 系统登陆日志事件
 */
@Slf4j
public class SysLogininforEvent extends ApplicationEvent {

    public SysLogininforEvent(SysLogininfor source) {
        super(source);
        log.info("事件入队");
    }
}

package edu.nciae.system.feign.factory;

import edu.nciae.system.domain.SysLogininfor;
import edu.nciae.system.domain.SysOperLog;
import edu.nciae.system.feign.RemoteLogService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService> {
    @Override
    public RemoteLogService create(Throwable throwable) {
        log.error(throwable.getMessage());
        return new RemoteLogService() {
            @Override
            public void insertOperlog(SysOperLog operLog) {
            }

            @Override
            public void insertLoginlog(SysLogininfor logininfor) {
            }
        };
    }
}

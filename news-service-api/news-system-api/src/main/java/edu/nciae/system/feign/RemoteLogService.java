package edu.nciae.system.feign;

import edu.nciae.system.domain.SysLogininfor;
import edu.nciae.system.domain.SysOperLog;
import edu.nciae.system.feign.factory.RemoteLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 日志Feign服务层
 *
 * @author zmr
 * @date 2019-05-20
 */
@FeignClient(name = "system", fallbackFactory = RemoteLogFallbackFactory.class, contextId = "log")
public interface RemoteLogService {
    @PostMapping("operLog/save")
    void insertOperlog(@RequestBody SysOperLog operLog);

    @PostMapping("logininfor/save")
    void insertLoginlog(@RequestBody SysLogininfor logininfor);
}

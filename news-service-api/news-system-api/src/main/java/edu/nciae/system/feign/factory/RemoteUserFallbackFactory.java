package edu.nciae.system.feign.factory;

import edu.nciae.common.domain.R;
import edu.nciae.system.domain.SysUser;
import edu.nciae.system.feign.RemoteUserService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 类似于断容器
 */
@Slf4j
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {

    public RemoteUserService create(Throwable throwable) {
        log.error(throwable.getMessage());
        return new RemoteUserService() {
            public SysUser selectSysUserByUserName(String userName) {
                return null;
            }

            public R updateUserLoginRecord(SysUser user) {
                return R.error();
            }

            @Override
            public SysUser selectUserById(Integer userId) {
                return null;
            }
        };
    }
}

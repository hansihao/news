package edu.nciae.auth.service;

import cn.hutool.core.util.IdUtil;
import edu.nciae.common.constant.Constants;
import edu.nciae.common.redis.utils.RedisUtils;
import edu.nciae.system.domain.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccessTokenService {
    @Autowired
    private RedisUtils redis;

    // 12 小时后过期
    private final static long EXPIRE = 12 * 60 * 60;

    private final static String ACCESS_TOKEN  = Constants.ACCESS_TOKEN;

    private final static String ACCESS_USERID = Constants.ACCESS_USERID;

    public SysUser queryByToken(String token) {
        return redis.get(ACCESS_TOKEN + token, SysUser.class);
    }

    public Map<String, Object> createToken(SysUser sysUser) {
        // 生成token
        String token = IdUtil.simpleUUID();
        // 保存或更新用户token
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", sysUser.getUserId());
        map.put("token", token);
        map.put("expire", EXPIRE);
        expireToken(sysUser.getUserId());
        redis.set(ACCESS_TOKEN + token, sysUser, EXPIRE);
        redis.set(ACCESS_USERID + sysUser.getUserId(), token, EXPIRE);
        return map;

    }

    public void expireToken(long userId) {
        String token = redis.get(ACCESS_USERID + userId);
        if (StringUtils.isNotBlank(token)) {
            redis.delete(ACCESS_USERID + userId);
            redis.delete(ACCESS_TOKEN + token);
        }
    }
}

package edu.nciae.order.lock;

import edu.nciae.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisLock {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key 商品的唯一标识
     * @param value 当前时间 + 超时时间 也就是时间戳
     * @return
     */
    public boolean lock(String key, String value) {
        // setIfAbsent相当于jedis中的setnx，如果能赋值就返回true，如果已经有值了，就返回false
        // 即：在判断这个key是不是第一次进入这个方法
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            // 第一次，即：这个key还没有被赋值的时候
            return true;
        }
        // 判断锁超时，防止原来的操作异常，没有运行解锁操作，防止死锁
        String current_value = (String) redisTemplate.opsForValue().get(key);
        // 如果锁过期
        if (StringUtils.isNotEmpty(current_value) && Long.parseLong(current_value) < System.currentTimeMillis()) {
            // 获取上一个 锁的时间value
            String oldValue = (String) redisTemplate.opsForValue().getAndSet(key, value);
            // 假设两个线程同时进来这里，因为key被占用了，而且锁过期了。获取的值currentValue=A(get取的旧的值肯定是一样的),两个线程的value都是B,key都是K.锁时间已经过期了。
            // 而这里面的getAndSet一次只会一个执行，也就是一个执行之后，上一个的value已经变成了B。只有一个线程获取的上一个值会是A，另一个线程拿到的值是B。
            if (StringUtils.isNotEmpty(oldValue) && oldValue.equals(current_value)) {
                //oldValue不为空且oldValue等于currentValue，也就是校验是不是上个对应的商品时间戳，也是防止并发
                return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key, String value) {
        try {
            String currentValue = (String) redisTemplate.opsForValue().get(key);
            if (StringUtils.isNotEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            log.error("解锁出现异常, {}", e);
        }
    }

}

package edu.nciae.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * 路由限流配置
 * 可以使用 jmeter 进行压力测试
 */
@Configuration
public class RateLimiterConfiguration {

    @Bean
    public KeyResolver remoteAddrKeyResolver() {
        // eactor 是一个用于JVM的完全非阻塞的响应式编程框架, Mono是针对0到1个元素进行操作
        // 按URL限流,即以每秒内请求数按URL分组统计
        return exchange -> Mono.just(exchange.getRequest().getURI().getPath());
    }
}

package edu.nciae.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient // 本服务启动后会自动注册eureka
@EnableDiscoveryClient // 服务发现
@EnableCircuitBreaker // 对Hystrix熔断机制的支持TEST
// 注解@SpringCloudApplication包括：@SpringBootApplication、@EnableDiscoveryClient、@EnableCircuitBreaker，分别是SpringBoot注解、注册服务中心Eureka注解、断路器注解。
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}

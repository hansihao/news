package edu.nciae.common.mq.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

// 把同类的配置信息自动封装成实体类
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Data
@Accessors(chain = true)
public class RabbitProperties {
	String host;

	String port;

	String username;

	String password;
}

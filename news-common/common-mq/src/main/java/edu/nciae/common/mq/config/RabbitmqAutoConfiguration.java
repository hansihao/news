package edu.nciae.common.mq.config;

import edu.nciae.common.mq.entity.RabbitProperties;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitmqAutoConfiguration {
}

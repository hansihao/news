package edu.nciae.common.mq.annotation;

import edu.nciae.common.mq.config.KafkaProducerConfig;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;

import java.lang.annotation.*;

@EnableKafka
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(KafkaProducerConfig.class)
public @interface EnableNewsKafka {
}

package edu.nciae.common.mq.annotation;

import edu.nciae.common.mq.config.RabbitmqAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(RabbitmqAutoConfiguration.class)
public @interface EnableNewsRabbitmq {
}

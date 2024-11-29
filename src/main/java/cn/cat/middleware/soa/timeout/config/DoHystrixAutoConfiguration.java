package cn.cat.middleware.soa.timeout.config;

import cn.cat.middleware.soa.timeout.DoHystrixPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "soa.timeout.hystrix", value = "enabled")
public class DoHystrixAutoConfiguration {

    @Bean
    public DoHystrixPoint doJointPoint() {
        return new DoHystrixPoint();
    }

}

package cn.cat.middleware.soa.config.timeout;

import cn.cat.middleware.soa.DoHystrixPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "hystrix", value = "enabled")
public class DoHystrixAutoConfiguration {

    @Bean
    public DoHystrixPoint doJointPoint() {
        return new DoHystrixPoint();
    }

}

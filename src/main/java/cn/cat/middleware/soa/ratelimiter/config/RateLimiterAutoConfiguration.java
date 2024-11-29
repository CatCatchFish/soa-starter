package cn.cat.middleware.soa.ratelimiter.config;

import cn.cat.middleware.soa.ratelimiter.DoRateLimiterPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "soa.ratelimiter", value = "enabled")
public class RateLimiterAutoConfiguration {

    @Bean
    public DoRateLimiterPoint doRateLimiterPoint() {
        return new DoRateLimiterPoint();
    }

}

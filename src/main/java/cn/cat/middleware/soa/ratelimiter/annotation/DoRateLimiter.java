package cn.cat.middleware.soa.ratelimiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DoRateLimiter {

    String type() default "";

    // hytrix
    double permitsPerSecond() default 0D;   // 限流许可量

    int timeRate() default 1;               // 时间窗口

    // 令牌桶
    int bucketSize() default 1;            // 桶容量

    String returnJson() default "";         // 失败结果 JSON

}

package cn.cat.middleware.soa.ratelimiter.valve.impl;

import cn.cat.middleware.soa.ratelimiter.annotation.DoRateLimiter;
import cn.cat.middleware.soa.ratelimiter.valve.IValveService;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;

import javax.annotation.Resource;
import java.lang.reflect.Method;

public class RRateLimiterValveImpl implements IValveService {
    @Resource
    private RedissonClient redissonClient;

    @Override
    public Object access(ProceedingJoinPoint jp, Method method, DoRateLimiter doRateLimiter, Object[] args) throws Throwable {
        // 判断是否开启
        if (0 == doRateLimiter.permitsPerSecond()) return jp.proceed();

        String clazzName = jp.getTarget().getClass().getName();
        String methodName = method.getName();
        String key = clazzName + "." + methodName;

        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        // 设置限流
        if (rateLimiter.trySetRate(RateType.OVERALL, doRateLimiter.bucketSize(), doRateLimiter.timeRate(), RateIntervalUnit.SECONDS)) {
            return jp.proceed();
        }
        return JSON.parseObject(doRateLimiter.returnJson(), method.getReturnType());
    }
}

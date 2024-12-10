package cn.cat.middleware.soa.ratelimiter;

import cn.cat.middleware.soa.ratelimiter.annotation.DoRateLimiter;
import cn.cat.middleware.soa.ratelimiter.valve.IValveService;
import cn.cat.middleware.soa.ratelimiter.valve.ValveServiceFactory;
import cn.cat.middleware.soa.ratelimiter.valve.impl.RRateLimiterValveImpl;
import cn.cat.middleware.soa.ratelimiter.valve.impl.RateLimiterValveImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class DoRateLimiterPoint {

    @Pointcut("@annotation(cn.cat.middleware.soa.ratelimiter.annotation.DoRateLimiter)")
    public void aopPoint() {
    }

    @Around("aopPoint() && @annotation(doRateLimiter)")
    public Object doRouter(ProceedingJoinPoint jp, DoRateLimiter doRateLimiter) throws Throwable {
        IValveService valveService = ValveServiceFactory.getValveService(doRateLimiter.type());
        return valveService.access(jp, getMethod(jp), doRateLimiter, jp.getArgs());
    }

    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

}

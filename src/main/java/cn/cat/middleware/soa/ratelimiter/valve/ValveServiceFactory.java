package cn.cat.middleware.soa.ratelimiter.valve;

import cn.cat.middleware.soa.ratelimiter.valve.impl.RRateLimiterValveImpl;
import cn.cat.middleware.soa.ratelimiter.valve.impl.RateLimiterValveImpl;

import java.util.HashMap;
import java.util.Map;

public class ValveServiceFactory {

    public static Map<String, IValveService> valveServices = new HashMap<>();

    static {
        valveServices.put("redis", new RRateLimiterValveImpl());
        valveServices.put("normal", new RateLimiterValveImpl());
    }

    public static IValveService getValveService(String type) {
        switch (type) {
            case "redis":
                return valveServices.get("redis");
            case "normal":
                return valveServices.get("normal");
            default:
                return valveServices.get("normal");
        }
    }

}

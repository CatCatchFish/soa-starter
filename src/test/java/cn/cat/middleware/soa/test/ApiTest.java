package cn.cat.middleware.soa.test;

import org.junit.Before;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiTest {
    private static final Logger logger = LoggerFactory.getLogger(ApiTest.class);
    private RedissonClient redissonClient;

    @Before
    public void setUp() {
        Config config = new Config();
        // 根据需要可以设定编解码器；https://github.com/redisson/redisson/wiki/4.-%E6%95%B0%E6%8D%AE%E5%BA%8F%E5%88%97%E5%8C%96
        // config.setCodec(new RedisCodec());
        config.setCodec(new JsonJacksonCodec());

        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
//                .setPassword(properties.getPassword())
                .setConnectionPoolSize(10)
                .setConnectionMinimumIdleSize(5)
                .setIdleConnectionTimeout(30000)
                .setConnectTimeout(5000)
                .setRetryAttempts(3)
                .setRetryInterval(1000)
                .setPingConnectionInterval(60000)
                .setKeepAlive(true)
        ;

        redissonClient = Redisson.create(config);
    }

    @Test
    public void test() throws InterruptedException {
        String key = "test_rate_limiter";
        // 1、 声明一个限流器
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);

        // 2、 设置速率，5秒中产生3个令牌
        rateLimiter.trySetRate(RateType.OVERALL, 1, 1, RateIntervalUnit.SECONDS);

        // 3、试图获取一个令牌，获取到返回true
        for (int i = 0; i < 10; i++) {
            Thread.sleep(300);
            boolean acquired = rateLimiter.tryAcquire(1);
            logger.info("是否获取到令牌：{}", acquired);
        }
    }
}

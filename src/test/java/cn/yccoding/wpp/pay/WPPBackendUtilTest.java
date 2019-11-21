package cn.yccoding.wpp.pay;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class WPPBackendUtilTest {

    @Autowired
    private WPPBackendUtil wppBackendUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testRedis() {
        // redisTemplate.boundValueOps("k1").set("K1");
        // execute().set("k2".getBytes(), "v2".getBytes());
        String accessToken = wppBackendUtil.getAccessTokenInRedis("k5");
        System.out.println("accessToken=" + accessToken);
    }

    @Test
    public void test01() {
        redisTemplate.boundValueOps("k1").set("v1");
    }

    @Test
    void getAccessToken() {
        System.out.println(wppBackendUtil.getAccessToken());
    }

    @Test
    void getTicket() {
        System.out.println(wppBackendUtil.getJsApiTicket(wppBackendUtil.getAccessToken()));
    }

    private RedisConnection execute() {
        return (RedisConnection)redisTemplate.execute((RedisCallback)connection -> connection);
    }
}
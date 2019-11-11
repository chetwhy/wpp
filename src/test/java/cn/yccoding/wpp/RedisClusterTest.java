package cn.yccoding.wpp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import cn.yccoding.wpp.pay.WPPBackendUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class RedisClusterTest {

    @Autowired
    private WPPBackendUtil wppBackendUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testRedis() {
        stringRedisTemplate.opsForValue().get("k2");
    }

    @Test
    void getAccessToken() {
        System.out.println(wppBackendUtil.getAccessToken());
    }

    @Test
    void getTicket() {
        System.out.println(wppBackendUtil.getJsApiTicket(wppBackendUtil.getAccessToken()));
    }

}
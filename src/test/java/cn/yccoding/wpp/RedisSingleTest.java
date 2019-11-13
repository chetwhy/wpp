package cn.yccoding.wpp;

import cn.yccoding.wpp.service.IRedisService;
import cn.yccoding.wpp.service.impl.RedisServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import cn.yccoding.wpp.pay.WPPBackendUtil;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class RedisSingleTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IRedisService redisService;

    @Test
    public void contextLoads() {
        redisTemplate.boundValueOps("k1").set("v1");
        stringRedisTemplate.boundValueOps("k2").set("v2");
    }

    @Test
    public void test01() {
        System.out.println(redisService.getRedisInfo());
        Expiration.milliseconds(10000);
        execute().set("k3".getBytes(), "v3".getBytes(), Expiration.milliseconds(10000), RedisStringCommands.SetOption.UPSERT);
    }


    private RedisConnection execute() {
        return (RedisConnection) redisTemplate.execute((RedisCallback<RedisConnection>) connection -> connection);
    }
}
package cn.yccoding.wpp.pay;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WPPBackendUtilTest {

    @Autowired
    private WPPBackendUtil wppBackendUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testRedis() {
        //redisTemplate.boundValueOps("k1").set("K1");
        //execute().set("k2".getBytes(), "v2".getBytes());
        String accessToken = wppBackendUtil.getAccessToken();
        System.out.println("accessToken="+accessToken);
        String key = "wpp:"+accessToken;
        if (execute().get(key.getBytes()) != null) {
            System.out.println("已存在");
            System.out.println(key.getBytes());
        }else {
            System.out.println("不存在，新储存");
            execute().set(key.getBytes(), accessToken.getBytes());
        }
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
        return (RedisConnection) redisTemplate.execute((RedisCallback) connection -> connection);
    }
}
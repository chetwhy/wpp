package cn.yccoding.wpp.service.impl;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Service;

import cn.yccoding.wpp.model.redis.RedisInfoBean;
import cn.yccoding.wpp.service.IRedisService;

/**
 * @author : Chet
 * @description :
 * @date : 2019/11/13
 */
@Service
public class RedisServiceImpl implements IRedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private RedisConnection execute() {
        return redisTemplate.execute((RedisCallback<RedisConnection>)connection -> connection);
    }

    @Override
    public List<RedisInfoBean> getRedisInfo() {
        try {
            List<RedisInfoBean> redisInfoList = new ArrayList<>();
            Properties info = execute().info();
            for (String name : info.stringPropertyNames()) {
                RedisInfoBean redisInfoBean = new RedisInfoBean();
                redisInfoBean.setKey(name);
                redisInfoBean.setValue(info.getProperty(name));
                redisInfoList.add(redisInfoBean);
            }
            return redisInfoList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private Map<String, Object> getData(String name, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("create_time", LocalDateTime.now());
        map.put(name, data);
        return map;
    }

    @Override
    public Map<String, Object> getRedisMemory() {
        return getData("memory", execute().info("memory").get("used_memory"));
    }

    @Override
    public Map<String, Object> getRedisDbSize() {
        return getData("dbsize", execute().dbSize());
    }

    @Override
    public Set<String> getKeys(String pattern) {
        try {
            return redisTemplate.keys(pattern);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    @Override
    public String get(String key) {
        try {
            byte[] bytes = execute().get(key.getBytes());
            if (bytes != null) {
                return new String(bytes, "utf-8");
            } else {
                return null;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean set(String key, String value) {
        return execute().set(key.getBytes(), value.getBytes());
    }

    @Override
    public Boolean set(String key, String value, Long timeout) {
        return execute().set(key.getBytes(), value.getBytes(), Expiration.milliseconds(timeout),
            RedisStringCommands.SetOption.UPSERT);
    }

    @Override
    public Long del(String... keys) {
        long result = 0;
        for (String key : keys) {
            result += execute().del(key.getBytes());
        }
        return result;
    }

    @Override
    public Long exists(String... keys) {
        long result = 0;
        for (String key : keys) {
            if (execute().exists(key.getBytes())) {
                result++;
            }
        }
        return result;
    }

    @Override
    public Long pttl(String key) {
        return execute().pTtl(key.getBytes());
    }

    @Override
    public Long pexpire(String key, Long time) {
        if (execute().pExpire(key.getBytes(), time)) {
            return 1L;
        }
        return 0L;
    }
}

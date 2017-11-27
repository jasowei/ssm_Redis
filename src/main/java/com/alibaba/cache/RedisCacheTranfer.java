package com.alibaba.cache;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * Created by dllo on 17/11/27.
 */
public class RedisCacheTranfer {

    public void setJedisConnectionFactory(JedisConnectionFactory factory){
        RedisCache.setConnectionFactory(factory);
    }

}

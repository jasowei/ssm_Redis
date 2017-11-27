package com.alibaba.cache;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * Created by dllo on 17/11/27.
 */

public class RedisCache implements Cache{
    /*Redis连接工厂*/
    private static JedisConnectionFactory connectionFactory;

    /*缓存的唯一标识*/
    private String id;

    private ReadWriteLock readWriteLock;


    public static void setConnectionFactory(JedisConnectionFactory connectionFactory) {
        RedisCache.connectionFactory = connectionFactory;
    }

    public RedisCache(String id) {
        if (id == null){
            throw new IllegalArgumentException("缓存需要一个id");
        }
        this.id = id;
    }

    /**
     * 返回缓存对象的唯一标示
     * @return
     */
    public String getId() {
        return this.id;
    }

    /**
     * 保存key/value到缓存对象中
     * @param key
     * @param value
     */
    public void putObject(Object key, Object value) {

        JedisConnection connection = null;
        try {
            connection = (JedisConnection) connectionFactory.getConnection();

            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();

            connection.set(serializer.serialize(key),
                    serializer.serialize(value));

        }catch (JedisConnectionException e){
            e.printStackTrace();
        }finally {
            //关闭
            if (connection != null){
                connection.close();
            }
        }

    }

    /**
     * 根据key从缓存中取值
     * @param key
     * @return
     */
    public Object getObject(Object key) {
        JedisConnection connection = null;
        try {
            connection = (JedisConnection) connectionFactory.getConnection();

            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();

            return serializer.deserialize(connection.get(serializer.serialize(key)));

        }catch (JedisConnectionException e){
            e.printStackTrace();
        }finally {
            //关闭
            if (connection != null){
                connection.close();
            }
        }
        return null;
    }

    /**
     * 根据key移除对应的值
     * @param key
     * @return
     */
    public Object removeObject(Object key) {
        JedisConnection connection = null;
        try{
            connection = (JedisConnection) connectionFactory.getConnection();

            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();

            return connection.expire(serializer.serialize(key),0);

        }catch (JedisConnectionException e) {
            e.printStackTrace();
        }finally {
            if (connection != null){
                connection.close();
            }
        }
        return null;
    }

    /**
     * 清空缓存
     */
    public void clear() {
        JedisConnection connection = null;
        try {
            connection = (JedisConnection) connectionFactory.getConnection();

            connection.flushDb();
            connection.flushAll();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }finally {
            if (connection != null){
                connection.close();
            }
        }

    }

    /**
     * 获取缓存中键值对的数量
     * @return
     */
    public int getSize() {
        JedisConnection connection = null;
        try {
            connection = (JedisConnection) connectionFactory.getConnection();

            return connection.dbSize().intValue();

        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }finally {
            if (connection != null){
                connection.close();
            }
        }
        return 0;
    }

    /**
     * 获取读写锁
     * 任何需要的锁都必须由缓存供应方来提供
     * @return
     */
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}

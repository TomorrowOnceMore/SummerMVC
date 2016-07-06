package com.summer.rest.component;

/**
 * Created by toy on 7/5/16.
 */
public interface JedisClient {

    String set(String key, String value);
    String get(String key);
    Long hset(String key, String item, String value);
    String hget(String key, String item);
    Long incr(String key);
    Long decr(String key);
    Long expire(String key, int sec);
    Long ttl(String key);
    Long hdel(String key, String item);
}

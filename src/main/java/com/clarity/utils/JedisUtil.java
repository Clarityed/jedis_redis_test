package com.clarity.utils;

import redis.clients.jedis.Jedis;

/**
 * 获取 Jedis 对象
 *
 * @author: clarity
 * @date: 2022年10月28日 15:25
 */
public class JedisUtil {

    public static Jedis getLocalRedisJedisObj() {
        return new Jedis("192.168.254.129", 6379);
    }

}

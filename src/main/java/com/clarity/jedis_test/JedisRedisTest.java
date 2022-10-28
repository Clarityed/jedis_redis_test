package com.clarity.jedis_test;

import com.clarity.utils.JedisUtil;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * 使用 jedis 操作 Redis 6
 *
 * @author: clarity
 * @date: 2022年10月28日 15:03
 */
public class JedisRedisTest {

    // 测试是否成功链接到 Redis
    @Test
    public void test1() {
        // 创建 jedis 对象
        Jedis jedis = new Jedis("192.168.254.129", 6379);
        String value = jedis.ping();
        System.out.println(value);
        jedis.close();
    }

    // 测试 key
    @Test
    public void test2() {
        Jedis jedis = JedisUtil.getLocalRedisJedisObj();
        Set<String> keys1 = jedis.keys("*");
        for (String key : keys1) {
            System.out.println(key);
        }
        jedis.set("k1", "keQing");
        jedis.set("k2", "huTao");
        jedis.set("k3", "niLu");
        Set<String> keys2 = jedis.keys("*");
        for (String key : keys2) {
            System.out.println(key);
        }
        System.out.println(jedis.exists("k1"));
        System.out.println(jedis.exists("k4"));
        System.out.println(jedis.type("k1"));
        System.out.println(jedis.del("k1"));
        Set<String> keys3 = jedis.keys("*");
        for (String key : keys3) {
            System.out.println(key);
        }
        System.out.println(jedis.unlink("k2"));
        Set<String> keys4 = jedis.keys("*");
        for (String key : keys4) {
            System.out.println(key);
        }
        jedis.set("k1", "keQing");
        jedis.expire("k1", 10);
        System.out.println(jedis.ttl("k1"));
        System.out.println(jedis.ttl("k3"));
        System.out.println(jedis.select(15));
        System.out.println(jedis.select(0));
        System.out.println(jedis.dbSize());
        jedis.close();
    }

}

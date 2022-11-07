package com.clarity.jedis_test;

import com.clarity.utils.JedisUtil;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;

import java.util.List;
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
        jedis.flushAll();
        jedis.close();
    }

    // 测试 string
    @Test
    public void test3() {
        Jedis jedis = JedisUtil.getLocalRedisJedisObj();
        System.out.println(jedis.setnx("k1", "keQing"));
        System.out.println(jedis.setnx("k1", "keQing1"));
        System.out.println(jedis.get("k1"));
        System.out.println(jedis.append("k1", "是我的"));
        System.out.println(jedis.get("k1"));
        System.out.println(jedis.strlen("k1"));
        jedis.set("k2", "100");
        System.out.println(jedis.type("k2"));
        jedis.incr("k2");
        System.out.println(jedis.get("k2"));
        jedis.decr("k2");
        System.out.println(jedis.get("k2"));
        jedis.incrBy("k2", 100);
        System.out.println(jedis.get("k2"));
        jedis.decrBy("k2", 200);
        System.out.println(jedis.get("k2"));
        jedis.flushAll();
        Set<String> keys1 = jedis.keys("*");
        for (String key : keys1) {
            System.out.println(key);
        }
        jedis.mset("k1", "v1", "k2", "v2", "k3", "v3");
        Set<String> keys2 = jedis.keys("*");
        for (String key : keys2) {
            System.out.println(key);
        }
        List<String> mget = jedis.mget("k1", "k2", "k3");
        for (String s : mget) {
            System.out.println(s);
        }
        System.out.println(jedis.msetnx("k3", "v3", "k4", "v4", "k5", "v5"));
        Set<String> keys3 = jedis.keys("*");
        for (String key : keys3) {
            System.out.println(key);
        }
        jedis.msetnx("k4", "v4", "k5", "v5", "k6", "v6");
        Set<String> keys4 = jedis.keys("*");
        for (String key : keys4) {
            System.out.println(key);
        }
        jedis.set("role", "keQing");
        System.out.println(jedis.get("role"));
        System.out.println(jedis.getrange("role", 1, 2));
        jedis.setrange("role", 5, "andniLu");
        System.out.println(jedis.get("role"));
        jedis.setrange("role", 1, "123");
        System.out.println(jedis.get("role"));
        jedis.setrange("role", 12, "123");
        System.out.println(jedis.get("role"));
        jedis.setex("k7", 5, "1");
        System.out.println(jedis.ttl("k7"));
        Set<String> keys5 = jedis.keys("*");
        for (String key : keys5) {
            System.out.println(key);
        }
        System.out.println(jedis.getSet("role", "keQing"));
        System.out.println(jedis.get("role"));
        jedis.flushAll();
        jedis.close();
    }

    // 测试 List
    @Test
    public void test4() {
        Jedis jedis = JedisUtil.getLocalRedisJedisObj();
        jedis.lpush("k1", "v1", "v2", "v3");
        List<String> k1 = jedis.lrange("k1", 0, -1);
        k1.forEach(System.out::println);
        jedis.rpush("k2", "v1", "v2", "v3");
        List<String> k2 = jedis.lrange("k2", 0, -1);
        k2.forEach(System.out::println);
        System.out.println(jedis.lpop("k1"));
        System.out.println(jedis.rpop("k1"));
        System.out.println(jedis.rpoplpush("k2", "k1"));
        List<String> k11 = jedis.lrange("k1", 0, -1);
        k11.forEach(System.out::println);
        System.out.println(jedis.lindex("k1", 2));
        System.out.println(jedis.lindex("k1", 0));
        System.out.println(jedis.llen("k1"));
        System.out.println(jedis.linsert("k1", ListPosition.BEFORE, "v2", "new"));
        List<String> k111 = jedis.lrange("k1", 0, -1);
        k111.forEach(System.out::println);
        System.out.println(jedis.linsert("k1", ListPosition.AFTER, "v2", "new"));
        List<String> k1111 = jedis.lrange("k1", 0, -1);
        k1111.forEach(System.out::println);
        System.out.println(jedis.lrem("k1", 2, "new"));
        List<String> k11111 = jedis.lrange("k1", 0, -1);
        k11111.forEach(System.out::println);
        jedis.lrem("k1", 2, "v3");
        List<String> k111111 = jedis.lrange("k1", 0, -1);
        k111111.forEach(System.out::println);
        jedis.lset("k1", 0, "v1");
        List<String> k1111111 = jedis.lrange("k1", 0, -1);
        k1111111.forEach(System.out::println);
        jedis.close();
    }

    // 测试 Set 集合
    @Test
    public void test5() {
        Jedis jedis = JedisUtil.getLocalRedisJedisObj();
        System.out.println(jedis.sadd("role", "keQing", "huTao", "niLu"));
        Set<String> roles = jedis.smembers("role");
        for (String role : roles) {
            System.out.println(role);
        }
        System.out.println();
        System.out.println(jedis.sismember("role", "keQing"));
        System.out.println();
        System.out.println(jedis.sismember("role", "v1"));
        System.out.println();
        System.out.println(jedis.scard("role"));
        System.out.println();
        System.out.println(jedis.srem("role", "huTao", "niLu"));
        System.out.println();
        Set<String> roles2 = jedis.smembers("role");
        for (String role : roles2) {
            System.out.println(role);
        }
        System.out.println();
        System.out.println(jedis.spop("role"));
        Set<String> roles3 = jedis.smembers("role");
        for (String role : roles3) {
            System.out.println(role);
        }
        System.out.println();
        System.out.println(jedis.sadd("role", "keQing", "huTao", "niLu"));
        System.out.println(jedis.srandmember("role"));
        System.out.println(jedis.srandmember("role"));
        System.out.println(jedis.srandmember("role"));
        System.out.println();
        System.out.println(jedis.sadd("role2", "ganYu"));
        System.out.println(jedis.smove("role", "role2", "keQing"));
        Set<String> roles4 = jedis.smembers("role");
        for (String role : roles4) {
            System.out.println(role);
        }
        System.out.println();
        Set<String> sinter = jedis.sinter("role", "role2");
        for (String s : sinter) {
            System.out.println(s);
        }
        Set<String> sunion = jedis.sunion("role", "role2");
        for (String s : sunion) {
            System.out.println(s);
        }
        jedis.sadd("role", "keQing");
        Set<String> sdiff = jedis.sdiff("role", "role2");
        for (String s : sdiff) {
            System.out.println(s);
        }
        System.out.println();
        Set<String> roles5 = jedis.smembers("role");
        for (String role : roles5) {
            System.out.println(role);
        }
        System.out.println();
        Set<String> roles6 = jedis.smembers("role2");
        for (String role : roles6) {
            System.out.println(role);
        }
        jedis.close();
    }

}

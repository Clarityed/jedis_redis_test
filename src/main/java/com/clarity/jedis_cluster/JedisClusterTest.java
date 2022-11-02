package com.clarity.jedis_cluster;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * jedis 操作 Redis 集群
 *
 * @author: clarity
 * @date: 2022年11月02日 21:38
 */
public class JedisClusterTest {

    public static void main(String[] args) {

        // 创建对象，因为是集群从哪个入口进去都一样，计入或者读取值会自动切换 Redis 服务
        // 所以没必要用 	Set<HostAndPort>set =new HashSet<HostAndPort>();
        HostAndPort hostAndPort = new HostAndPort("192.168.254.129", 6380);
        JedisCluster jedisCluster = new JedisCluster(hostAndPort);

        // 进行相关操作
        jedisCluster.set("k1", "v1");

        String k1 = jedisCluster.get("k1");
        System.out.println("k1：" + k1);

        jedisCluster.close();
    }

}

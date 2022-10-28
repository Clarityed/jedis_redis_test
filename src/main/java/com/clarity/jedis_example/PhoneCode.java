package com.clarity.jedis_example;

import com.clarity.utils.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * 模拟手机验证码，生成，有效时间和验证过程
 *
 * @author: clarity
 * @date: 2022年10月28日 16:26
 */
public class PhoneCode {

    // 1. 生成随机 6 位数字的验证码
    public static String getVerifyCode() {
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            // nextInt() 参数 bound 是一个 （0 - 输入值）的范围
            int middleCode = random.nextInt(10);
            code += middleCode;
        }
        return code;
    }

    // 2. 每天只能发送三次验证码，把验证码放到 Redis 里面，并求设置过期时间
    public static void setCode(String phoneNumber) {
        // 声明存储在 Redis 验证码 key 和 记录发送次数的 key，用用户手机号区分（手机号唯一）
        // 定义规则
        // 验证码 key
        String codeKey = "verifyCode" + phoneNumber + ":code";
        // 请求验证码的次数
        String countKey = "verifyCount" + phoneNumber + ":count";

        // 获取 jedis
        Jedis localRedisJedisObj = JedisUtil.getLocalRedisJedisObj();
        // 每天只能发送三次验证码
        String count = localRedisJedisObj.get(countKey);
        if (count == null) {
            localRedisJedisObj.setex(countKey, 24 * 60 * 60, "1");
        } else if (Integer.parseInt(count) <= 2) {
            localRedisJedisObj.incr(countKey);
        } else {
            System.out.println("今天发送验证码超过三次了");
            localRedisJedisObj.close();
            return;
        }

        // 把验证码放到 Redis 里面
        localRedisJedisObj.setex(codeKey, 120, getVerifyCode());
        localRedisJedisObj.close();
    }

    // 判断验证码是否一致
    public static void verify(String code, String phoneNumber) {
        String codeKey = "verifyCode" + phoneNumber + ":code";
        Jedis localRedisJedisObj = JedisUtil.getLocalRedisJedisObj();
        String redisCode = localRedisJedisObj.get(codeKey);
        if (code.equals(redisCode)) {
            System.out.println("验证码正确");
        } else {
            System.out.println("验证码失败");
        }
        localRedisJedisObj.close();
    }

    public static void main(String[] args) {
        // 测试生成随机 6 位数字的验证码的方法
        // System.out.println(getVerifyCode());
        setCode("13655556987");
        // verify("9083401", "13655556987");
    }

}

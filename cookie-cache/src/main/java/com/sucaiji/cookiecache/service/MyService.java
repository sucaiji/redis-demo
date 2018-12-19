package com.sucaiji.cookiecache.service;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service
public class MyService {

    GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

    JedisPool jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379);



    /**
     * 检查登陆是否成功，account和password一样即可登录成功
     * @param account
     * @param password
     * @return
     */
    public String checkLogin(String account, String password) {
        if (account.equals(password)) {
            String token = UUID.randomUUID().toString();
            Jedis jedis = jedisPool.getResource();
            jedis.hset("login", token, account);
            jedis.close();
            updateToken(token);
            return token;
        }
        return null;
    }

    /**
     * 检查token是否有效
     * @param token
     * @return 返回token对应的account
     */
    public String checkToken(String token) {
        updateToken(token);
        Jedis jedis = jedisPool.getResource();
        String account = jedis.hget("login", token);
        jedis.close();
        return account;
    }

    /**
     * 更新token的最后访问时间
     * @param token
     */
    public void updateToken(String token) {
        long time = new Date().getTime();
        Jedis jedis = jedisPool.getResource();
        jedis.zadd("recent", time, token);
        jedis.close();
    }

    private static final long LIMIT = 100000L;

    @Scheduled(cron = "1,10,20,30,40,50 * * * * ?")
    public void cleanToken() {
        try (Jedis jedis = jedisPool.getResource()){
            long size = jedis.zcard("recent");
            if (LIMIT >= size) {
                System.out.println("当前token数为:" + size + "未超过，return");
                return;
            }
            Set<String> tokens = jedis.zrange("recent", 0, size - LIMIT - 1);
            System.out.println("size" + size);
            System.out.println("tokensize" + tokens.size());
            for (String token: tokens) {
                jedis.zrem("recent", token);
                jedis.hdel("login", token);
            }
            System.out.println("删除了" + (size - LIMIT) + "条数据,目前token数为:" +  jedis.zcard("recent"));
        }

    }



}

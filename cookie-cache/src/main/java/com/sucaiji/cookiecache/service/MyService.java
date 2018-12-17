package com.sucaiji.cookiecache.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.UUID;

@Service
public class MyService {

    private Jedis jedis = new Jedis("localhost");

    /**
     * 检查登陆是否成功，account和password一样即可登录成功
     * @param account
     * @param password
     * @return
     */
    public String checkLogin(String account, String password) {
        if (account.equals(password)) {
            String token = UUID.randomUUID().toString();
            jedis.hset("login", token, account);
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
        return jedis.hget("login", token);
    }

    /**
     * 更新token的最后访问时间
     * @param token
     */
    public void updateToken(String token) {
        long time = new Date().getTime();
        jedis.zadd("recent", time, token);
    }



}

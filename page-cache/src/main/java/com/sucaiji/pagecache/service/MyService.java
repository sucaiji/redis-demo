package com.sucaiji.pagecache.service;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class MyService {

    private GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

    //redis连接池
    private JedisPool jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379);

    //从cache中读取
    public String getHtmlFromCache(String uri) {
        Jedis jedis = jedisPool.getResource();
        return jedis.hget("cache", uri);
    }

    //保存到cache中
    public void saveCache(String uri, String html) {
        Jedis jedis = jedisPool.getResource();
        jedis.hset("cache", uri, html);
        jedis.close();
    }

}

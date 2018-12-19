package com.sucaiji.cookiecache;

import com.sucaiji.cookiecache.controller.MyController;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CookieCacheApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void contextLoads() {
        int count = 1000;
        long sum = 0;

        while (true) {
            CountDownLatch cdl = new CountDownLatch(count);

            for (int i = 0; i < count; i++) {
                final long I = sum;
                sum ++;
                new Thread(() -> {
                    cdl.countDown();
                    System.out.println(restTemplate.getForObject("http://localhost:8080/login?account=" + I + "&password=" + I, String.class));
                }).start();
            }
            try {
                cdl.await();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}


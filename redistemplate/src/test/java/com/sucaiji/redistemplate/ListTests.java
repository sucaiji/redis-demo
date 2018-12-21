package com.sucaiji.redistemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void pushAndPop() {
        ListOperations<String, String> ops = redisTemplate.opsForList();
        ops.leftPush("listtest", "a");
        ops.leftPushAll("listtest", "b", "c", "d");
        System.out.println("listtest:" + ops.range("listtest", 0L, -1L));

        ops.rightPush("listtest", "e");
        ops.rightPushAll("listtest", "f", "g", "h");
        System.out.println("listtest:" + ops.range("listtest", 0L, -1L));

        //LINDEX
        System.out.println("listtest[2]:" + ops.index("listtest", 2L));

        ops.trim("listtest", 1L, 0L);
        System.out.println("listtest:" + ops.range("listtest", 0L, -1L));

    }

    @Test
    public void blockPopAndPush() {
        ListOperations<String, String> ops = redisTemplate.opsForList();
        //ops.leftPushAll("blocklisttest", "a", "b", "c", "d");
        //System.out.println("blocklisttest:" + ops.range("blocklisttest", 0L, -1L));

        new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ops.leftPush("blocklisttest", "qwer");
        }).start();

        //BLPOP
        System.out.println("blockleftpop:" + ops.leftPop("blocklisttest", 50L, TimeUnit.SECONDS));

        ops.trim("blocklisttest", 1L, 0L);
    }
}

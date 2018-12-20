package com.sucaiji.redistemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StringTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void IncrAndDecr() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        //INCR
        for (int i = 0; i < 20; i++) {
            ops.increment("incr1");
        }
        System.out.println("incr1:" + ops.get("incr1"));

        //相当于INCRBY
        ops.increment("incr1", 200L);
        System.out.println("incr1:" + ops.get("incr1"));

        //DECR
        for (int i = 0; i < 20; i++) {
            ops.decrement("incr1");
        }
        System.out.println("incr1:" + ops.get("incr1"));

        //DECRBY
        ops.decrement("incr1", 200L);
        System.out.println("incr1:" + ops.get("incr1"));

        //INCRBYFLOAT
        ops.increment("incr1", 20.1);
        System.out.println("incr1:" + ops.get("incr1"));
    }


    @Test
    public void stringEdit() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        //APPEND
        ops.append("edit", "qwerty");
        System.out.println("edit:" + ops.get("edit"));

        //GETRANGE
        System.out.println(ops.get("edit", 0L, 4L));

        //SETRANGE
        ops.set("edit", "asdf", 4L);
        System.out.println("edit:" + ops.get("edit"));

        //GETBIT SETBIT
        ops.setBit("bit", 0, true);
        System.out.println("bit:" + ops.getBit("bit", 0L));

    }


}

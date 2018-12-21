package com.sucaiji.redistemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SetTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void set() {
        SetOperations<String, String> ops = redisTemplate.opsForSet();

        //SADD
        System.out.println("添加了" + ops.add("settest", "a", "b", "c", "d", "e", "f") + "个新元素");
        System.out.println("settest:" + ops.members("settest"));

        //SREM
        System.out.println("移除了" + ops.remove("settest", "a", "b", "c") + "个元素");
        System.out.println("settest:" + ops.members("settest"));

        //SISMEMBER
        System.out.println("a存在于settest吗:" + ops.isMember("settest", "a"));
        System.out.println("e存在于settest吗:" + ops.isMember("settest", "e"));

        ops.add("settest", "g", "h", "i", "j", "k", "l");
        //SRANDMEMBER
        System.out.println("随机返回5个元素:" + ops.randomMembers("settest", 5L));

        //SPOP
        System.out.println("settest:" + ops.members("settest"));
        System.out.println("随机移除2个元素:" + ops.pop("settest", 2));
        System.out.println("settest:" + ops.members("settest"));


    }
}

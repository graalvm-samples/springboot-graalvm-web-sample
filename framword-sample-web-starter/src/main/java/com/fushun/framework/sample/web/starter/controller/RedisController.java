package com.fushun.framework.sample.web.starter.controller;

import com.fushun.framework.redis.utils.RedisUtil;
import com.fushun.framework.redisson.util.RedissonUtil;
import com.fushun.framework.sample.mybatis.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RedisController {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedissonUtil redissonUtil;

    @Autowired
    RedissonClient redisClient;

    @RequestMapping("/app/redis")
    public String list() {
        redisUtil.setString("key","test");
        String str=redisUtil.getString("key");
        return str;
    }

    @RequestMapping("/app/redission")
    public String listRedisson() {
        RLock redisClientLock=null;
        try{
            redisClientLock = redisClient.getLock("ddd");
            redisClientLock.lock();
            return "ok";
        }finally {
            if(redisClientLock!=null){
                redisClientLock.unlock();
            }
        }
    }
}

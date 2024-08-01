package com.fushun.framework.sample.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fushun.framework.sample.mybatis.entity.User;
import com.fushun.framework.sample.mybatis.mapper.UserMapper;
import com.fushun.framework.sample.mybatis.service.User1Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 社区公示公告 实现类
 */
@Service
@Slf4j
public class User1ServiceImpl extends ServiceImpl<UserMapper, User> implements User1Service {


}

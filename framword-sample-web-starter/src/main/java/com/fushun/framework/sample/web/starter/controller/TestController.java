package com.fushun.framework.sample.web.starter.controller;

import com.fushun.framework.sample.mybatis.entity.SysUser;
import com.fushun.framework.sample.mybatis.mapper.SysUserMapper;
import com.fushun.framework.sample.mybatis.service.SysUser2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUser2Service sysUser2Service;

    @RequestMapping("/app/user/{id}")
    public SysUser list(@PathVariable Long id) {
        SysUser user=  sysUserMapper.selectById(id);
        SysUser user2=  sysUser2Service.getById(id);
        log.info(user.toString());
        log.info(user2.toString());
        return user2;
    }

    @RequestMapping("/admin/user/{id}")
    public SysUser adminList(@PathVariable Long id) {
        log.info("druid.log.stmt.executableSql:{}",System.getProperty("druid.log.stmt.executableSql"));
//        SysUser user=  sysUserMapper.selectById(id);
//        SysUser user2=  sysUser2Service.getById(id);
        SysUser user3=  sysUser2Service.getByUserId(id);
//        SysUser user4=  sysUser2Service.getPageUser(id);
//        log.info(user.toString());
//        log.info(user2.toString());
//        log.info(user3.toString());
//        log.info(user4.toString());
        return user3;
    }

    @RequestMapping("/admin/adduser")
    public void adminAdduser() {
//        SysUser user=  sysUserMapper.selectById(id);
//        SysUser user2=  sysUser2Service.getById(id);
        sysUser2Service.adminAdduser();
    }
}

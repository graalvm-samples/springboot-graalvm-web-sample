package com.fushun.framework.sample.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fushun.framework.dynamic.datasource.UseDataSource;
import com.fushun.framework.sample.mybatis.entity.SysUser;
import com.fushun.framework.sample.mybatis.enums.SexEnum;
import com.fushun.framework.sample.mybatis.mapper.SysUserMapper;
import com.fushun.framework.sample.mybatis.service.SysUser2Service;
import com.fushun.framework.sample.mybatis.service.SysUser3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 社区公示公告 实现类
 */
@Service
@Slf4j
@UseDataSource("first")
public class SysUser2ServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUser2Service {

    @Autowired
    private SysUser3Service sysUser3Service;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUser getByUserId(Long id){
        LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper<>();
        query.eq(SysUser::getId,id)
                .isNotNull(SysUser::getName)
                .ge(SysUser::getAge, 1);
        List<SysUser> sysUserList = this.list(query);
        if(sysUserList.isEmpty()){
            return new SysUser();
        }
        return sysUserList.get(0);
    }

    @Override
    public SysUser getPageUser(Long id){
        IPage<SysUser> page=new Page<>(0,100);
        page = this.page(page,new LambdaQueryWrapper<SysUser>().eq(SysUser::getId,id));
        if(page.getRecords().isEmpty()){
            return new SysUser();
        }
        return page.getRecords().get(0);
    }

    @Override
    public void adminAdduser() {
        SysUser sysUser=new SysUser();
        sysUser.setAge(1);
        sysUser.setName("名字");
        sysUser.setSex(SexEnum.BOY);
        sysUser.setEmail("邮件");
        this.save(sysUser);
    }
}

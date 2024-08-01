package com.fushun.framework.sample.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fushun.framework.dynamic.datasource.UseDataSource;
import com.fushun.framework.sample.mybatis.entity.SysUser;
import com.fushun.framework.sample.mybatis.mapper.SysUserMapper;
import com.fushun.framework.sample.mybatis.service.SysUser2Service;
import com.fushun.framework.sample.mybatis.service.SysUser3Service;
import com.fushun.framework.sample.mybatis.service.SysUser4Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 社区公示公告 实现类
 */
@Service
@Slf4j
@UseDataSource("second")
public class SysUser3ServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUser3Service {

    @Autowired
    private SysUser4Service sysUser4Service;

    @Override
    public SysUser getByUserId(Long id){
        List<SysUser> sysUserList = this.list(new LambdaQueryWrapper<SysUser>().eq(SysUser::getId,id));
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
}

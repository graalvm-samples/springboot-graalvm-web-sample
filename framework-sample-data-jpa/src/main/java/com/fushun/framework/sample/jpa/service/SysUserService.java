package com.fushun.framework.sample.jpa.service;


import com.fushun.framework.sample.jpa.model.SysUser;

public interface SysUserService {
    SysUser findById(Long id);

    void save(SysUser sysUser);
}

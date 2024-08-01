package com.fushun.framework.sample.jpa.dao;


import com.fushun.framework.sample.jpa.model.SysUser;
import org.springframework.data.repository.CrudRepository;

public interface SysUserDao extends CrudRepository<SysUser, Long> {}


package com.fushun.framework.sample.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fushun.framework.sample.mybatis.entity.SysUser;


/**
 * 社区公示公告
 */
public interface SysUser2Service extends IService<SysUser> {

    SysUser getByUserId(Long id);

    SysUser getPageUser(Long id);

    void adminAdduser();
}

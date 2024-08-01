package com.fushun.framework.sample.security.admin.service;

import com.fushun.framework.security.model.UserDTO;

/**
 * admin 管理端登陆
 */
public interface AdminLoginUserService {
    /**
    * <b>Description:根据用户名查询用户信息</b>
    */
    UserDTO findLoginByUserName(String username);
}

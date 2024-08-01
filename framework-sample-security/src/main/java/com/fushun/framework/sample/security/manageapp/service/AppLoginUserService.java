package com.fushun.framework.sample.security.manageapp.service;

import com.fushun.framework.security.model.UserDTO;

/**
 * api登陆
 */
public interface AppLoginUserService {

    /**
    * <pre>
    * <b>Description:根据用户名查询用户信息</b>
    * </pre>
    */
    UserDTO findLoginByUserName(String username);

}

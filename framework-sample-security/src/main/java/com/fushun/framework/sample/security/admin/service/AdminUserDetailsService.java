package com.fushun.framework.sample.security.admin.service;

import com.fushun.framework.sample.security.admin.model.AdminLoginInfo;
import com.fushun.framework.security.model.UserDTO;
import com.fushun.framework.util.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class AdminUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(AdminUserDetailsService.class);

    @Autowired
    private AdminLoginUserService adminLoginUserService;


    @Override
    public UserDetails loadUserByUsername(String username) {
        //查数据库
        logger.info("后台用户登录获取用户信息 username:{}",username);
        UserDTO user = adminLoginUserService.findLoginByUserName(username);
        if (StringUtils.isEmpty(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        return new AdminLoginInfo(user);
    }

}

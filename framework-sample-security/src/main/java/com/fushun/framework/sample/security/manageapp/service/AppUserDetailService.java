package com.fushun.framework.sample.security.manageapp.service;

import com.fushun.framework.sample.security.manageapp.model.AppUserLoginInfo;
import com.fushun.framework.security.model.UserDTO;
import com.fushun.framework.util.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class AppUserDetailService implements UserDetailsService {

    /**
     * 没有实现， 就不注入
     */
   @Autowired
   private AppLoginUserService appLoginUserService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        //查数据库
        UserDTO user = appLoginUserService.findLoginByUserName(username);
        if (StringUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        return new AppUserLoginInfo(user);
    }

}

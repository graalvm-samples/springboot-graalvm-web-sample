package com.fushun.framework.sample.security.manageapp.service.impl;

import com.fushun.framework.sample.security.manageapp.service.AppLoginUserService;
import com.fushun.framework.security.model.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class AppLoginUserServiceImpl implements AppLoginUserService {
    @Override
    public UserDTO findLoginByUserName(String username) {
        UserDTO userDTO=new UserDTO();
        userDTO.setUserName("1");
        userDTO.setUserAccount("1");
        userDTO.setUserPassword("1");
        return userDTO;
    }
}

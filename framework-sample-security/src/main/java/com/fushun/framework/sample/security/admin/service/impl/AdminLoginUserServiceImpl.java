package com.fushun.framework.sample.security.admin.service.impl;

import com.fushun.framework.sample.security.admin.service.AdminLoginUserService;
import com.fushun.framework.security.model.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class AdminLoginUserServiceImpl implements AdminLoginUserService {
    @Override
    public UserDTO findLoginByUserName(String username) {
        UserDTO userDTO=new UserDTO();
        userDTO.setUserName("admin");
        userDTO.setUserAccount("admin");
        userDTO.setUserPassword("$2a$10$jBNe5kZT1IlQvy0Deqp76el74Fmw.Txpfap0leTC7kJ1O.zxXF/va");
        return userDTO;
    }
}

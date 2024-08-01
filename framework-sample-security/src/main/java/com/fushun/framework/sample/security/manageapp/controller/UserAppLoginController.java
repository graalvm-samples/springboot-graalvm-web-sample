package com.fushun.framework.sample.security.manageapp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class UserAppLoginController {

    /**
     * 登录
     * @return 登录是否成功
     */
    @PostMapping("/login")
    public void login(){
    }
}

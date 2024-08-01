package com.fushun.framework.sample.security.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
@Slf4j
public class AdminLoginController {

    /**
     * 登录
     * @return 登录是否成功
     */
    @PostMapping("/login")
    public void login(){ }
}

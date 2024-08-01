package com.fushun.framework.sample.jpa.web;


import com.fushun.framework.sample.jpa.model.User;
import com.fushun.framework.sample.jpa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController  {

    @Autowired
    private UserService userService;


    @RequestMapping("/app/jpa/user/{id}")
    public User list(@PathVariable Long id) {
        User user= this.userService.findById(id);
        log.info(user.toString());
        return user;
    }

    @RequestMapping("/admin/jpa/user/{id}")
    public User adminlist(@PathVariable Long id) {
        User user= this.userService.findById(id);
        log.info(user.toString());
        return user;
    }
}

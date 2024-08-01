package com.fushun.framework.sample.jpa.service;


import com.fushun.framework.sample.jpa.model.User;

public interface UserService {
    User findById(Long id);
    void save(User user);
}

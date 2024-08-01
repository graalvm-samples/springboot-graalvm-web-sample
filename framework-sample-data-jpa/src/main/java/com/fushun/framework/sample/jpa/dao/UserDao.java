package com.fushun.framework.sample.jpa.dao;


import com.fushun.framework.sample.jpa.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {}


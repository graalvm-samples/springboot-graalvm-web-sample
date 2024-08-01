package com.fushun.framework.sample.jpa.service;

//import com.baomidou.dynamic.datasource.annotation.DS;
import com.fushun.framework.sample.jpa.dao.UserDao;
import com.fushun.framework.sample.jpa.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
//@DS("slave")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findById(Long id) {
        Optional<User> result = userDao.findById(id);
        if (!result.isPresent()) {
            return null;
        }

        return result.get();
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }
}

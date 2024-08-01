package com.fushun.framework.sample.jpa.service;

//import com.baomidou.dynamic.datasource.annotation.DS;
import com.fushun.framework.sample.jpa.dao.SysUserDao;
import com.fushun.framework.sample.jpa.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
//@DS("slave")
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public SysUser findById(Long id) {
        Optional<SysUser> result = sysUserDao.findById(id);
        if (!result.isPresent()) {
            return null;
        }

        return result.get();
    }

    @Override
    public void save(SysUser sysUser) {
        sysUserDao.save(sysUser);
    }

}

package com.zei.shiro.shirojwt.service;

import com.zei.shiro.shirojwt.entity.User;
import com.zei.shiro.shirojwt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User getUser(String userName){
        return userMapper.getUser(userName);
    }
}

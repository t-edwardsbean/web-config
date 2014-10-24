package com.baidu.service;

import com.baidu.model.User;
import com.baidu.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by edwardsbean on 2014/8/23 0023.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUser(int userId) {
        return userMapper.getUser(userId);
    }

    @Override
    public void InsertUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public boolean userLogin(String userName,String password) {
        return false;
    }
}

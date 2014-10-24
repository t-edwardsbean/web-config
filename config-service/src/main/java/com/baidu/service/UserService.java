package com.baidu.service;

import com.baidu.model.User;

/**
 * Created by edwardsbean on 2014/8/23 0023.
 */
public interface UserService {
    User getUser(int userId);
    void InsertUser(User user);
    boolean userLogin(String userName,String password);
}

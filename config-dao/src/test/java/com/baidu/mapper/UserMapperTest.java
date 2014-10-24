package com.baidu.mapper;

import com.baidu.model.User;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by edwardsbean on 2014/8/23 0023.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dao.xml")
public class UserMapperTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserMapper userMapper;


    @Test
    public void testInsertUser() throws Exception {
        User user = new User();
        user.setUserName("edwardsbean");
        userMapper.insertUser(user);
    }

    @Test
    public void testGetUser() throws Exception {
        User user = userMapper.getUser(1);
        System.out.println(user.getUserName());
        Assert.assertEquals(user.getUserName(),"edwardsbean");
    }
}

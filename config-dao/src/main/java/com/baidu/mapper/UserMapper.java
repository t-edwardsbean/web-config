package com.baidu.mapper;

import com.baidu.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by edwardsbean on 2014/8/23 0023.
 */
//@Repository(value="userMapper")
public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{userId}")
    User getUser(@Param("userId") int userId);

    @Insert("INSERT INTO user(userName) VALUES (#{userName})")
    @Options(useGeneratedKeys=true, flushCache=true)
    void insertUser(User user);
}
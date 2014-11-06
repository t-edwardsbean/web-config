package com.baidu.mapper;

import com.baidu.model.TestConfig;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by edwardsbean on 14-11-4.
 */
@Repository(value="testMapper")
public interface TestMapper {

    @Insert("INSERT INTO test_config(serviceName,describe,groupName,requestDelay,failDelay,serverNum,threadNum) VALUES " +
            "(#{serviceName},#{describe},#{groupName},#{requestDelay},#{failDelay},#{serverNum},#{threadNum})")
    @Options(useGeneratedKeys=true, flushCache=true)
    void insertTestConfig(TestConfig testConfig);

    @Select("SELECT * FROM test_config WHERE id = #{id}")
    TestConfig getTestConfig(@Param("id") int id);

    @Update("UPDATE STUDENTS SET NAME=#{name}, EMAIL=#{email},PHONE=#{phone} WHERE STUD_ID=#{studId}")
    void updateTestConfig(TestConfig testConfig);

    @Delete("DELETE FROM test_config WHERE id=#{id}")
    int deleteTestConfig(int id);

}

package com.baidu.mapper;

import com.baidu.model.TestConfig;
import com.baidu.model.TestConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestConfigMapper {
    int countByExample(TestConfigExample example);

    int deleteByExample(TestConfigExample example);

    int deleteByPrimaryKey(Integer configId);

    int insert(TestConfig record);

    int insertSelective(TestConfig record);

    List<TestConfig> selectByExample(TestConfigExample example);

    TestConfig selectByPrimaryKey(Integer configId);

    int updateByExampleSelective(@Param("record") TestConfig record, @Param("example") TestConfigExample example);

    int updateByExample(@Param("record") TestConfig record, @Param("example") TestConfigExample example);

    int updateByPrimaryKeySelective(TestConfig record);

    int updateByPrimaryKey(TestConfig record);

    List<TestConfig> findAll();

    List<TestConfig> findAllSimple();
}
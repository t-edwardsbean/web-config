package com.baidu.mapper;

import com.baidu.model.TestMethod;

import java.util.List;

public interface TestMethodMapper {
    int deleteByPrimaryKey(Integer methodId);

    int insert(TestMethod record);

    int insertSelective(TestMethod record);

    TestMethod selectByPrimaryKey(Integer methodId);

    int updateByPrimaryKeySelective(TestMethod record);

    int updateByPrimaryKey(TestMethod record);

    List<TestMethod> findMethodByConfig(int configId);
}
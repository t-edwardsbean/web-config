package com.baidu.mapper;

import com.baidu.model.MethodParam;

import java.util.List;

public interface MethodParamMapper {
    int deleteByPrimaryKey(Integer methodParamId);

    int insert(MethodParam record);

    int insertSelective(MethodParam record);

    MethodParam selectByPrimaryKey(Integer methodParamId);

    int updateByPrimaryKeySelective(MethodParam record);

    int updateByPrimaryKey(MethodParam record);

    List<MethodParam> findParamByMethod(int methodId);
}
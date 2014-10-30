package com.baidu.service;


import java.util.List;

/**
 * Created by edwardsbean on 14-10-29.
 */
public interface StressTestService {
    public String queryService(String serviceName) throws Exception;
    public void download(String id) throws Exception;

    /**
     * 为每个压测隔离一个独立的jar环境，以免接口jar版本升级时，影响到正在测试的旧接口jar
     * @param id
     * @param testId
     */
    public void download(String id,int testId);

    public List<String> getMethods(String serviceName,String id);



}

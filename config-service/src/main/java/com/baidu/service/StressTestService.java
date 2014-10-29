package com.baidu.service;


import com.baidu.service.vo.Status;

/**
 * Created by edwardsbean on 14-10-29.
 */
public interface StressTestService {
    public String queryService(String serviceName) throws Exception;
    public Status download(String id) throws Exception;
}

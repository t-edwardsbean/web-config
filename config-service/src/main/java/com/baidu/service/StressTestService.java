package com.baidu.service;


import com.baidu.service.vo.SimpleMethod;

import java.io.File;
import java.util.List;

/**
 * Created by edwardsbean on 14-10-29.
 */
public interface StressTestService {
    public String queryService(String serviceName) throws Exception;

    /**
     * 下载到libext目录，id.jar
     * @param id
     * @return
     * @throws Exception
     */
    public File download(String id) throws Exception;

    /**
     * 为每个压测隔离一个独立的jar环境，以免接口jar版本升级时，影响到正在测试的旧接口jar
     * 下载到libext目录，testId.jar
     * @param id
     * @param testId
     */
    public File download(String id,int testId) throws Exception;

    /**
     * 加载完方法列表，是否删除该jar,以免用户更新服务接口时，缓存的jar没有得到更新。
     * @param serviceName
     * @param jar
     * @param clean 是否删除jar
     * @return
     * @throws ClassNotFoundException
     */
    public List<SimpleMethod> loadService(String serviceName,String jar,boolean clean) throws ClassNotFoundException;

    public void run(int testId) throws Exception;
}

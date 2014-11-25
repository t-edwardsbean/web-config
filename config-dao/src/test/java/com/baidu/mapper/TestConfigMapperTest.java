package com.baidu.mapper;

import com.baidu.model.TestConfig;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by edwardsbean on 14-11-13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:dao.xml"})
public class TestConfigMapperTest {
    @Autowired
    TestConfigMapper testConfigMapper;

    @Test
    public void testInsert() throws Exception {
        TestConfig testConfig = new TestConfig();
        testConfig.setServiceName("test v1.0");
        testConfig.setDescription("测试描述");
        testConfig.setFailDelay(1000);
        testConfig.setGroupName("com.baidu");
        testConfig.setRequestDelay(1000);
        testConfig.setServerNum(2);
        testConfig.setServiceId("123123123");
        testConfig.setThreadNum(1);
        testConfigMapper.insert(testConfig);
    }

    @Test
    public void testGet() throws Exception {
        TestConfig config = testConfigMapper.selectByPrimaryKey(1);
        System.out.println(config);
    }

    @Test
    public void testFindAll() throws Exception {
        RowBounds rowBounds = new RowBounds(0,5);
    }
}

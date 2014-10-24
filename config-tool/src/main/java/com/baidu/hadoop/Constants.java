package com.baidu.hadoop;

/**
 * Created by edwardsbean on 14-8-20.
 */
public class Constants {
    public static String NAMENODE = "hdfs://192.168.253.149:8020";
    public static String JOBTRACKER = "192.168.253.149:8021";
    public static String TMP_PATH = "/tmp/";
    public static String HIVE_BASE_PATH = "/user/oozie/91report/";
    public static String OOZIE_URL = "http://192.168.253.149:11000/oozie";
    public static String OOZIE_COORDINATOR_PATH = NAMENODE + "/user/oozie/91report/";
    public static String OOZIE_WORKFLOW_PATH = NAMENODE + "/user/oozie/91report/";
    public static String HIVE_METASTORE = "thrift://localhost:9083";
    //oozie是否用UTC时间，而不是中国这个timezone
    public static String OOZIE_UTC = "true";
    public static String DATABASE = "'jdbc:mysql:/192.168.253.149:3306;username=root;password=123;database=91report'";
}

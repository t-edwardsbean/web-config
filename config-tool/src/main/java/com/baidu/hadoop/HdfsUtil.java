package com.baidu.hadoop;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by edwardsbean on 14-8-20.
 */
public class HdfsUtil {
    public static final Log LOG = LogFactory.getLog(HdfsUtil.class);
    public static FileSystem getFileSystem(String master) {
        FileSystem fs = null;
        Configuration config = new Configuration();
        //master: hdfs://...
        config.set("fs.default.name", master);
        try {
            fs = FileSystem.get(config);
        } catch (Exception e) {
            LOG.error("getFileSystem failed :"
                    + ExceptionUtils.getFullStackTrace(e));
        }
        return fs;
    }

    public static void upload(String local,String remote) {
        FileSystem fs = HdfsUtil.getFileSystem(Constants.NAMENODE);
        Path dst = new Path(remote);
        Path src = new Path(local);
        try {
            fs.copyFromLocalFile(true, true, src, dst);
            fs.close();
            LOG.debug("upload " + local + " to  " + remote + " successed. ");
        } catch (Exception e) {
            LOG.error("upload " + local + " to  " + remote + " failed :"
                    + ExceptionUtils.getFullStackTrace(e));
        }
    }

    public static String read(String remote) {
        FileSystem fs = HdfsUtil.getFileSystem(Constants.NAMENODE);
        String content = "";
        Path dst = new Path(remote);
        try {
            // reading
            FSDataInputStream dis = fs.open(dst);
            BufferedReader bis = new BufferedReader(new InputStreamReader(dis,"UTF-8"));
            String temp;
            while ((temp = bis.readLine()) != null) {
                if(content != ""){
                    content += "\n";
                }
                content += temp;
            }
            bis.close();
            dis.close();
            fs.close();
            LOG.info("read content from " + remote + " successed. ");
        } catch (Exception e) {
            LOG.error("read content from " + remote + " failed :"
                    + ExceptionUtils.getFullStackTrace(e));
        }
        return content;
    }
}

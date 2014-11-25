package com.baidu.tools;

import com.google.common.io.Files;
import com.google.gson.Gson;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by edwardsbean on 14-10-17.
 */
public class WebPlatTool {
    private final static Logger log = LoggerFactory.getLogger(WebPlatTool.class.getName());

    public static final String ROOT = "webtoolplat";

    public static String getServiceId(String serviceName, List<Page> pages) throws Exception {
        for (Page page : pages) {
            for (ServiceDefine serviceDefine : page.getRows()) {
                if (serviceName.equals(serviceDefine.getRegname())) {
                    return serviceDefine.getId();
                }
            }
        }
        throw new Exception("Service not found from web tool platform");
    }

    public static List<Page> getAllService(String webToolAddrs) throws Exception {
        List<Page> list = new ArrayList<Page>();
        getService(webToolAddrs, 1, 20, list);
        return list;
    }

    private static void getService(String webToolAddrs, int pageNum, int rows, List<Page> list) throws Exception {
        HttpUtil httpUtil = new HttpUtil();
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();// 设置表格参数
        formParams.add(new BasicNameValuePair("page", pageNum + ""));
        formParams.add(new BasicNameValuePair("rows", rows + ""));
        UrlEncodedFormEntity uefEntity = uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");//获取实体对象
        HttpResponse response = httpUtil.doPost(webToolAddrs + "/" + ROOT + "/" + "infSerList_intfSer.action", uefEntity, null);
        String json = EntityUtils.toString(response.getEntity());
        Gson gson = new Gson();
        Page page = gson.fromJson(json, Page.class);
        httpUtil.releaseConnection();
        list.add(page);
        log.debug("Got service define:" + page.toString());
        rows = page.getRows().size();
        if (page.getTotal() > rows) {
            getService(webToolAddrs, pageNum + 1, rows, list);
        }
    }

    public static File download(String webToolAddrs, String serviceId, String path) throws Exception {
        File file = new File(path + File.separator + serviceId + ".zip");
        log.info("Zip location:" + file.getAbsolutePath());
        if (file.delete()) {
            log.info("清空缓存zip");
        }
        try {
            file.createNewFile();
            log.debug("Downloading file:" + file.getAbsolutePath());
            HttpUtil httpUtil = new HttpUtil();
            HttpResponse response = httpUtil.doGet(webToolAddrs + "/" + ROOT + "/" + "download.action?language=java&id=" + serviceId, null);
            FileOutputStream outputStream = new FileOutputStream(file);
            InputStream inputStream = response.getEntity().getContent();
            byte buff[] = new byte[4096];
            int counts;
            while ((counts = inputStream.read(buff)) != -1) {
                outputStream.write(buff, 0, counts);
            }
            httpUtil.releaseConnection();
            return file;
        } catch (Exception e) {
            log.error("Download fail,delete file " + file.getName(), e);
            file.delete();
            throw e;
        }
    }

    public static void unZipToFolder(String zipfilename, String outputdir) throws IOException {
        File zipfile = new File(zipfilename);
        if (zipfile.exists()) {
            outputdir = outputdir + File.separator;
            FileUtils.forceMkdir(new File(outputdir));

            ZipFile zf = new ZipFile(zipfile, "GBK");
            Enumeration zipArchiveEntrys = zf.getEntries();
            while (zipArchiveEntrys.hasMoreElements()) {
                ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) zipArchiveEntrys.nextElement();
                if (zipArchiveEntry.isDirectory()) {
                    FileUtils.forceMkdir(new File(outputdir + zipArchiveEntry.getName() + File.separator));
                } else {
                    IOUtils.copy(zf.getInputStream(zipArchiveEntry), FileUtils.openOutputStream(new File(outputdir + zipArchiveEntry.getName())));
                }
            }
        } else {
            throw new IOException("指定的解压文件不存在：\t" + zipfilename);
        }
    }

    /**
     * 在zip的目录下生成jar包
     *
     * @param zip
     * @throws IOException
     */
    public static File parseZip2Jar(File zip, String shell) throws IOException {
        String path = zip.getAbsolutePath();
        WebPlatTool.unZipToFolder(path, zip.getParent());
        String sourceFile = zip.getParent() + File.separator + "gen-java";
        try {
            String result = new ProcessExecutor()
                    .command(shell, sourceFile, zip.getParent())
                    .destroyOnExit()
                    .readOutput(true)
                    .redirectOutput(Slf4jStream.of(LoggerFactory.getLogger(WebPlatTool.class.getName() + ".MavenProcess")).asInfo())
                    .execute().outputUTF8();
            System.out.println("maven结果:" + result);
        } catch (Exception e) {
            throw new RuntimeException("maven编译打包接口包出错:" + e.getMessage());
        }
        return new File(path + File.separator + "thrift-package-1.0-SNAPSHOT.jar");


    }

}

package com.baidu.tools;

import com.google.common.io.Files;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
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
        //查找本地cache
        File file = new File(path + File.separator + serviceId + ".jar");
        log.info("Jar location:" + file.getAbsolutePath());
        try {
            file.createNewFile();
            log.debug("Downloading file:" + file.getAbsolutePath());
            HttpUtil httpUtil = new HttpUtil();
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();// 设置表格参数
            formParams.add(new BasicNameValuePair("language", "java"));
            formParams.add(new BasicNameValuePair("id", serviceId));
            HttpResponse response = httpUtil.doGet(webToolAddrs + "/" + ROOT + "/" + "download.action", null);
            InputStream inputStream = response.getEntity().getContent();
            byte buff[] = new byte[4096];
            int counts = 0;
            while ((counts = inputStream.read(buff)) != -1) {
                Files.write(buff, file);
            }
            httpUtil.releaseConnection();
            return file;
        } catch (Exception e) {
            log.error("Download fail,delete file " + file.getName(), e);
            file.delete();
            throw e;
        }
    }

}

package com.baidu.tools;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

/**
 * Created by edwardsbean on 14-10-17.
 */
public class HttpUtil {
    public static final Logger log = LoggerFactory.getLogger(HttpUtil
            .class);

    private HttpRequestBase httpRequest;

    public HttpClient getClient() {
        return new DefaultHttpClient();// 获取HttpClient对象        client;
    }

    public HttpClient getClient(String proxy) {
        HttpClient client = new DefaultHttpClient();// 获取HttpClient对象
        client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(proxy));
        return client;
    }

    public void releaseConnection() {
        if (httpRequest != null) {
            httpRequest.releaseConnection();
        }
    }

    private void showResponse(HttpResponse response) throws ParseException,
            IOException {
        log.debug("requset result:");
        log.debug(response.getStatusLine().toString());// 响应状态
        log.debug("-----------------------------------");

        Header[] heard = response.getAllHeaders();// 响应头
        log.debug("response heard:");
        for (int i = 0; i < heard.length; i++) {
            log.debug(heard[i].toString());
        }
        log.debug("-----------------------------------");
        HttpEntity entity = response.getEntity();// 响应实体/内容
        log.debug("response content length:" + entity.getContentLength());
        log.debug("response content:");

        log.debug(EntityUtils.toString(entity));

    }

    public HttpResponse doGet(String uri, String proxy) throws Exception {// get方法提交
        HttpGet getMethod = null;
        if (!uri.startsWith("http")) {
            uri = "http://" + uri;
        }
        getMethod = new HttpGet(uri);// 获取HttpGet对象，使用该对象提交get请求
        return exctueRequest(getMethod, proxy);
    }

    public HttpResponse doPost(String uri, HttpEntity entity, String proxy) throws Exception {// post方法提交
        HttpPost postMethod = null;
        if (!uri.startsWith("http")) {
            uri = "http://" + uri;
        }
        postMethod = new HttpPost(uri);
        postMethod.setEntity(entity);//设置请求实体，例如表单数据
        return exctueRequest(postMethod, proxy); // 执行请求，获取HttpResponse对象

    }

    private HttpResponse exctueRequest(HttpRequestBase request, String proxy) throws Exception {
        HttpResponse response = null;
        this.httpRequest = request;
        try {
            log.debug("excute request:" + request.getURI());
            log.debug("-----------------------------------");

            if (proxy != null) {
                response = this.getClient(proxy).execute(request);
            } else {
                response = this.getClient().execute(request);//执行请求，获取HttpResponse对象
            }
//            showResponse(response);
            int statuscode = response.getStatusLine().getStatusCode();//处理重定向
            if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) || (statuscode == HttpStatus.SC_MOVED_PERMANENTLY)
                    || (statuscode == HttpStatus.SC_SEE_OTHER) || (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {

                Header redirectLocation = response.getFirstHeader("Location");
                String newuri = redirectLocation.getValue();
                if ((newuri != null) && (!newuri.equals(""))) {
                    log.debug("redirect to " + newuri);
                    request.setURI(new URI(newuri));
                    response = this.getClient().execute(request);
//                    showResponse(response);
                } else {
                    log.debug("Invalid redirect");
                }

            }
        } catch (Exception e) {
            throw e;
        }
        return response;

    }
}

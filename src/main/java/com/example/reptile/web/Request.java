package com.example.reptile.web;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Request {


    private HttpPost httpPost;
    private int cycleTime = 3;//循环重试次数
    private static Logger LOGGER = LoggerFactory.getLogger(Request.class);

    public Request(String url, Map<String,String> params) throws Exception{
        getReq(url,params);
    }

    public Request(String url, Map<String,String> params,int cycleTime) throws Exception{
        this.cycleTime = cycleTime;
        getReq(url,params);
    }

    public  HttpPost getReq(String url, Map<String,String> params) throws ParseException, IOException {

        //创建post方式请求对象
        httpPost = new HttpPost(url);

        //设置参数到请求对象中
        List<NameValuePair> paramList = new ArrayList<>();
        if(params!=null){
            for (Map.Entry<String,String> entry : params.entrySet()) {
                paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(paramList));

        LOGGER.debug("请求地址：{}",url);
        LOGGER.debug("请求参数：{}",params.toString());

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.81 Safari/537.36");

        return httpPost;
    }

    public int getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(int cycleTime) {
        this.cycleTime = cycleTime;
    }

    public HttpPost getHttpPost() {
        return httpPost;
    }

    public void setHttpPost(HttpPost httpPost) {
        this.httpPost = httpPost;
    }
}

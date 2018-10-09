package com.example.reptile.web;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {


    private Connection connection;
    private int cycleTime = 3;//循环重试次数
    private static Logger LOGGER = LoggerFactory.getLogger(Request.class);

    public Request(String url, Map<String,String> params) throws Exception{
        this.connection = getReq(url,params);
    }

    public Request(String url, Map<String,String> params,int cycleTime) throws Exception{
        this.cycleTime = cycleTime;
        this.connection = getReq(url,params);
    }

    public Connection getReq(String url, Map<String,String> params) throws ParseException, IOException {

        Map header = new HashMap();
        header.put("Accept","application/json, text/javascript, */*; q=0.01");
        header.put("Accept-Encoding","gzip, deflate");
        header.put("Accept-Language","zh-CN,zh;q=0.9");
        header.put("Connection","keep-alive");
        header.put("Cookie","_gscu_322517208=39090331d8nftb58; _gscbrs_322517208=1; _gscs_322517208=39090331bt8s4c58|pv:1; uid=112; JSESSIONID=Ku1Y7g8N2tcURBInvcG8WC7Yr9z2xSLyd3JDLjN55Kk3i8NXpUbs!805921943; SERVERID=s1");
        header.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        header.put("X-Requested-With","XMLHttpRequest");
        header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.81 Safari/537.36");
        return Jsoup.connect(url).headers(header).
                method(Connection.Method.POST).timeout(1200000).ignoreContentType(true).data(params);

    }

    public int getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(int cycleTime) {
        this.cycleTime = cycleTime;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

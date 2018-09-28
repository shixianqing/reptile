package com.example.reptile.processor;

import com.alibaba.fastjson.JSONObject;
import com.example.reptile.mapper.HospitalMapper;
import com.example.reptile.model.Hospital;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @Author:shixianqing
 * @Date:2018/9/2118:17
 * @Description:
 **/
@Service
public class SpiderMedicalBusinessSite {

    private List<String> provinces = Arrays.asList("7180","7182","7184","7186","7188","7190",
            "7192","7194","7196","7198","7200","7202","7204","7206","7208","7210","7212","7214","7216","7218",
            "7220","7222","7224","7226","7228","7230","7232","7234","7236","7238","7240","21508");

    @Autowired
    private HospitalMapper hospitalMapper;

    private String url = "https://www.hqms.org.cn/usp/roster/rosterInfo.jsp?provinceId={0}&htype=&hgrade=&hclass=&hname=&_={1}";
    public void process(){

        for (String province:provinces){
            String reqUrl = MessageFormat.format(url,province,new Date().getTime());
            System.out.println(reqUrl);
            Connection connection = Jsoup.connect(reqUrl).timeout(120000).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");
            Connection.Response response = null;
            try {
                response = connection.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String body = response.body();
            List<Map> result = JSONObject.parseObject(body,List.class);
            hospitalMapper.insert(result);
//            System.out.println(body);
        }

    }

    public static void main(String[] args) {
        new SpiderMedicalBusinessSite().process();
    }

}

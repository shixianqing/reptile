package com.example.reptile.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author:shixianqing
 * @Date:2018/9/2114:44
 * @Description:
 **/
public class JsoupDemo {
    public static void main(String[] s){
        try {
           Document document = Jsoup.connect("https://yyk.99.com.cn/sanjia/").get();
           List<Element> eles = document.getElementsByClass("tablist");
            Map<String,Object> map = new HashMap<>();
           for (Element element:eles){
               String text = element.getElementsByAttribute("href").get(1).text();
               List<String> list = element.getElementsByAttribute("target").eachText();
               map.put(text,list);
//               System.out.println(list);
           }
            System.out.println(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

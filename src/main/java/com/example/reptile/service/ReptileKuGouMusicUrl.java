package com.example.reptile.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:shixianqing
 * @Date:2018/9/2115:32
 * @Description:
 **/
public class ReptileKuGouMusicUrl {

    public void process(String url) throws Exception{
        Document document = Jsoup.connect(url).get();
        Element ulElement = document.getElementsByClass("f-hide").get(0);
        List<Element> elements = ulElement.getElementsByTag("li");
        Map map = new HashMap();
        for (Element element : elements){
            Element songEle = element.getElementsByTag("a").get(0);
            String attrVal = songEle.attr("href");
            String text = songEle.text();
            map.put(text,attrVal);
        }
        System.out.println(map);
    }

    public static void main(String[] s) throws Exception{
        new ReptileKuGouMusicUrl().process("https://music.163.com/discover/toplist?id=3779629");
    }
}

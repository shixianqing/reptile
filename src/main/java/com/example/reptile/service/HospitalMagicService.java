package com.example.reptile.service;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:shixianqing
 * @Date:2018/9/2110:36
 * @Description:
 **/
public class HospitalMagicService implements PageProcessor {

    //抓取网站的相关配置  包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetrySleepTime(3).setSleepTime(1);
    @Override
    public void process(Page page) {
        String urls = page.getUrl().toString();
        System.out.println(urls);
        List<String> links = page.getHtml().links().regex("https://yyk\\.99\\.com\\.cn/sanjia/").all();
        page.addTargetRequests(links);
        List<String> province = page.getHtml().xpath("//div[@class='fontlist']/ul/li/a/text()").all();
        System.out.println(province);
        List<String> areas = page.getHtml().xpath("//div/[@class='tablist']/h4/a[1]/text()").all();
        Map<String,Object> map = new HashMap<>();
        for (int i=0;i<areas.size();i++){

            List<String> list = page.getHtml().xpath("//div[@class='tablist']["+i+"]").xpath("//ul/li/a/text()").all();
//            map.put(areas.get(i),list);

            System.out.println(list);

        }
//
//        List<String> list = page.getHtml().xpath("/body//div/[@class='tablist']").all();
        System.out.println(map);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args){
        Spider.create(new HospitalMagicService()).addUrl("https://yyk.99.com.cn/sanjia/").run();
    }
}

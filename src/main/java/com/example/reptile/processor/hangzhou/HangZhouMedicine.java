package com.example.reptile.processor.hangzhou;

import com.example.reptile.pipeline.HangZhouMedicinePipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import java.text.MessageFormat;

/**
 * @Author:shixianqing
 * @Date:2018/10/2614:38
 * @Description: 杭州药品
 **/
public class HangZhouMedicine implements PageProcessor {

    private static final String URL = "http://www.zjylbx.com/drug_catalogue.aspx?page={0}";
    private static final String START_URL = "http://www.zjylbx.com/drug_catalogue.aspx";
    public static final String START_URL_REGEX = "http://www.zjylbx.com\\/drug_catalogue.aspx";
    private static final int TOTAL_PAGES = 197;
    @Override
    public void process(Page page) {
        String url = page.getUrl().get();
        System.out.println("请求地址为：-----------》》》》"+url);

        page.putField("html",page.getHtml());

        if (url.matches(START_URL_REGEX)){
            for (int i=1;i<=TOTAL_PAGES;i++){
                page.addTargetRequest(MessageFormat.format(URL,String.valueOf(i)));
            }
        }

    }

    @Override
    public Site getSite() {
        return Site.me().setCycleRetryTimes(3).setTimeOut(120000).setSleepTime((int)(Math.random()*1000 + 10000)).setUserAgent( (int)(Math.random()*2 ) == 1 ? "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.146 Safari/537.36" : "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134");
    }

    public static void main(String[] args) {
        Spider.create(new HangZhouMedicine()).addPipeline(new HangZhouMedicinePipeline()).addUrl(START_URL).thread(5).run();
    }
}

package com.example.reptile.processor;

import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * @Author:shixianqing
 * @Date:2018/9/2118:17
 * @Description:
 **/
public class SpiderMedicalBusinessSite implements PageProcessor {

    private static final Site site = Site.me().setRetryTimes(3).setSleepTime((int)(Math.random()*1000 + 4000)).setUserAgent( (int)(Math.random()*2 ) == 1 ? "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.146 Safari/537.36" : "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134");
    private static final String regexUrl = "http://yy\\.ylsw\\.net/list/yy.html";
    @Override
    public void process(Page page) {
        Html html = page.getHtml();
      List<String> links = html.links().regex(regexUrl).all();

      page.addTargetRequests(links);
    }

    @Override
    public Site getSite() {
        return site;
    }
}

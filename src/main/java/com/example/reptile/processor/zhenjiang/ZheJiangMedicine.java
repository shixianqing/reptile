package com.example.reptile.processor.zhenjiang;

import com.example.reptile.pipeline.ZheJiangMedicinePipeline;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.text.MessageFormat;

/**
 * 浙江
 */
public class ZheJiangMedicine implements PageProcessor {

    private static String startUrl = "http://www.zjylbx.com/drug_catalogue.aspx";

    private String startUrlRegex = "http://www\\.zjylbx\\.com/drug_catalogue.aspx";

    private String pageCommonUrl = "http://www.zjylbx.com/drug_catalogue.aspx?page={0}";

    public static String pageCommonUrlRegex = "http://www\\.zjylbx\\.com/drug_catalogue.aspx\\?page=[0-9]+";

    private Site site = Site.me().setCycleRetryTimes(3).setTimeOut(120000).setSleepTime((int)(Math.random()*1000 + 4000)).setUserAgent( (int)(Math.random()*2 ) == 1 ? "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.146 Safari/537.36" : "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134");

    @Override
    public void process(Page page) {

       String url = page.getUrl().get();
       System.out.println("请求路径：-----------》"+url);
       Document document = page.getHtml().getDocument();
       page.putField("html",page.getHtml());

       if (url.matches(startUrlRegex)){
          for (int i=1;i<=197;i++){
              page.addTargetRequest(MessageFormat.format(pageCommonUrl,i));
          }
       }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new ZheJiangMedicine()).addUrl(startUrl).thread(5).addPipeline(new ZheJiangMedicinePipeline()).run();
    }
}

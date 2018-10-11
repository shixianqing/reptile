package com.example.reptile.processor.hainan;

import com.example.reptile.pipeline.HaiNanTreamentPipeline;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * @Author:shixianqing
 * @Date:2018/10/1117:11
 * @Description: 海南省人力资源和社会保障厅
 **/
public class HaiNanTreamentSit implements PageProcessor {

    private Site site = Site.me().setCycleRetryTimes(3).setTimeOut(120000).setSleepTime(1).setUserAgent( (int)(Math.random()*2 ) == 1 ? "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.146 Safari/537.36" : "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134");

    private static final String START_URL = "http://hi.lss.gov.cn/hnsi/sbbmfwybzlxm/";
    private static final String START_URL_REGEX = "http://hi\\.lss\\.gov\\.cn/hnsi/sbbmfwybzlxm/";

    private static String PAGE_COMMON_URL = "http://hi.lss.gov.cn/hnsi/sbbmfwybzlxm/index_{0}.html";
    public static String PAGE_COMMON_URL_REGEX = "http://hi\\.lss\\.gov\\.cn/hnsi/sbbmfwybzlxm/index_[0-9]+.html";

    private static String FIRST_PAGE_URL = "http://hi.lss.gov.cn/hnsi/sbbmfwybzlxm/index.html";
    public static String FIRST_PAGE_URL_REGEX = "http://hi\\.lss\\.gov\\.cn/hnsi/sbbmfwybzlxm/index.html";

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        System.out.println(html.get());
        page.putField("html",html);
        Document document = html.getDocument();
        String url = page.getUrl().get();
        if (url.matches(START_URL_REGEX)){
            Elements elements = document.select("body>div>div.mattle>div.you>dd>div.page>div>select>option");
            int totalPages = elements.size();
            for (int i=1;i<=1;i++ ){
                if (i == 1){
                    page.addTargetRequest(FIRST_PAGE_URL);
                }else {
                    page.addTargetRequest(MessageFormat.format(PAGE_COMMON_URL_REGEX,i+1));
                }
            }
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
//        String regex = "http://hi.lss.gov.cn/hnsi/sbbmfwybzlxm/index[_]?[0-9]+.html";
//        System.out.println("http://hi.lss.gov.cn/hnsi/sbbmfwybzlxm/index99.html".matches(regex));


        System.setProperty("selenuim_config", "E:\\project\\reptile\\src\\main\\resources\\config.ini");
        Spider.create(new HaiNanTreamentSit())//调用一个webmagic中封装好的一个网页爬取类
                .addUrl(START_URL)//要爬取的网页
                //浏览器驱动（动态网页信息通过模拟浏览器启动获取）
                .setDownloader(new SeleniumDownloader("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe"))
                .thread(3)//启动n个线程（此语句表示启动3个线程）
                .run();//启动爬虫，会阻塞当前线程执行（及n个线程不是同时执行的）

    }
}

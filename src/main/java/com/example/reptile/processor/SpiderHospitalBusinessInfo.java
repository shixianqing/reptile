package com.example.reptile.processor;

import com.example.reptile.pipeline.PipelineHospital;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:shixianqing
 * @Date:2018/9/2510:21
 * @Description:
 **/
@Service
public class SpiderHospitalBusinessInfo implements PageProcessor {
    private Site site = Site.me().setCycleRetryTimes(3).setTimeOut(120000).setSleepTime((int)(Math.random()*1000 + 4000)).setUserAgent( (int)(Math.random()*2 ) == 1 ? "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.146 Safari/537.36" : "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134");
    private static final String LIST_PAGE_URL_REGEX = "http://yy.ylsw.net/list/province-([0-9]\\d*)_p-([0-9]\\d*)_yy.html";
    private static final String PRIVINCE_COMMON_URL = "http://yy.ylsw.net/list/province-{0}_p-{1}_yy.html";
    private static final String PRIVINCE_LIST_URL = "http://yy.ylsw.net/list/province-{0}_yy.html";
    private static final String PRIVINCE_LIST_URL_REGEX = "http://yy.ylsw.net/list/province-([0-9]\\d*)_yy.html";
    private static final String START_URL_REGEX = "http://yy\\.ylsw\\.net/list/yy.html";
    private static final Logger LOGGER = LoggerFactory.getLogger(SpiderHospitalBusinessInfo.class);
    private static final int PAGE_SIZE = 15;
    @Autowired
    private PipelineHospital pipelineHospital;

    private List<Element> privinceList = new ArrayList<>();
    @Override
    public void process(Page page) {

        Html html = page.getHtml();
        Document doc = html.getDocument();
        Selectable url = page.getUrl();
        if(url.regex(START_URL_REGEX).match()){
            privinceList = doc.select("#province1>option");
            //遍历省份
            for (Element element:privinceList){
                String value = element.attr("value");
                if (ObjectUtils.isEmpty(value))
                    continue;
                page.addTargetRequest(MessageFormat.format(PRIVINCE_LIST_URL,value));

                /*for (int i=1;i<=totalPage;i++){
                    page.addTargetRequest(MessageFormat.format(PRIVINCE_COMMON_URL,value,i));
                }*/


            }
        }

        page.putField("html",html);

        if (url.regex(PRIVINCE_LIST_URL_REGEX).match()){
            Element totalNum = doc.select("#SearchPlaceBar>div>span").first();
            String num = totalNum.text().replace("共找到","").replaceAll(" ","").replace("条符合条件查询结果","");
            int totalPage = Integer.parseInt(num)/PAGE_SIZE;
            if (totalPage%PAGE_SIZE>0) {
                totalPage += 1;
            }
            String value = url.toString().replace("http://yy.ylsw.net/list/province-","").replace("_yy.html","");
            for (int i=1;i<=totalPage;i++){
                page.addTargetRequest(MessageFormat.format(PRIVINCE_COMMON_URL,value,i));
            }
        }

        if (url.regex(LIST_PAGE_URL_REGEX).match()){
            Elements eleList = doc.select(".list>.offer>.content1>.info_font>.blue>a");
            for (Element element:eleList){
                String detailUrl = element.attr("href");
                page.addTargetRequest(detailUrl);
                LOGGER.debug("详情页URL------------》》》》{}",detailUrl);
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void spider(){
        Spider.create(new SpiderHospitalBusinessInfo())
                .addUrl("http://yy.ylsw.net/list/yy.html")
                //开启5个线程抓取
                .thread(5)
                .addPipeline(pipelineHospital)
                //启动爬虫
                .run();
    }

    public static void main(String[] args) {
        Spider.create(new SpiderHospitalBusinessInfo())
                .addUrl("http://yy.ylsw.net/list/yy.html")
                //开启5个线程抓取
                .thread(5)
                .addPipeline(new PipelineHospital())
                //启动爬虫
                .run();
    }


}

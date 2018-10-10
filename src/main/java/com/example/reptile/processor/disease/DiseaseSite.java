package com.example.reptile.processor.disease;

import com.example.reptile.mapper.ZhongYiBaoDianDiseaseMapper;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.io.*;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Author:shixianqing
 * @Date:2018/10/1010:24
 * @Description:
 **/
@Service
public class DiseaseSite implements PageProcessor {
    private static final String START_URL = "http://jb.9939.com/jbzz_t1/";
//    private static final String START_URL = "http://jb39.com/jibing/jibing-all.htm";
//    private static final String START_URL = "http://jb39.com/";
//    private static final String START_URL = "http://jb39.com/neike/";
    private static final String START_URL_REGEX = "http://jb\\.9939\\.com/jbzz_t1/";
//    private static final String START_URL_REGEX = "http://jb39\\.com/";
//    private static final String START_URL_REGEX = "http://jb39\\.com/neike/";
    private String PAGE_COMMON_URL = "http://jb.9939.com/jbzz_t1/?page={0}";
    private static String PAGE_COMMON_URL_REGEX = "http://jb.9939.com/jbzz_t1/\\?page=[0-9]+";
//    private String PAGE_COMMON_URL = "http://jb39.com/jibing-keshi/NeiKe39-{0}.htm";
    private Site site = Site.me().setCycleRetryTimes(3).setTimeOut(120000).setSleepTime(1).setUserAgent( (int)(Math.random()*2 ) == 1 ? "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.146 Safari/537.36" : "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134");

    @Autowired
    private ZhongYiBaoDianDiseaseMapper zhongYiBaoDianDiseaseMapper;

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        Document document = html.getDocument();
        if (page.getUrl().get().matches(START_URL_REGEX)){
//            Element element = document.select(".mulu-page>span").last().child(0);
//            String href = element.attr("href");
//            href = href.replace("/jibing-keshi/NeiKe39-","").replace(".htm","");
//            int totalPages = Integer.parseInt(href);
//            for (int i=1;i<=totalPages;i++){
//
//            }
//            System.out.println(href);

//            List<String> keshiList = document.select("#divMain>div>div>ul:nth-child(7)>li>a").eachAttr("href");
//            List<String> buweiList = document.select("#divMain>div>div>ul:nth-child(9)>li>a").eachAttr("href");
//            List<String> renqunList = document.select("#divMain>div>div>ul.ul-zzrq>li>a").eachAttr("href");
//            for (String keshi:keshiList){
//                page.addTargetRequest(keshi);
//            }
//            for (String buwei:keshiList){
//                page.addTargetRequest(buwei);
//            }
//            for (String renqun:keshiList){
//                page.addTargetRequest(renqun);
//            }

//            List<String> texts = document.select("#divMain>div>div>ul.post-mulu>li>a").eachText();
//            zhongYiBaoDianDiseaseMapper.insert(texts);

            String text = document.select("#tagContent>div>div.paget.paint>a:nth-child(9)").text();
            int totalPages = Integer.parseInt(text);
            //添加分页url
            for (int i=1;i<=totalPages;i++){
                page.addTargetRequest(MessageFormat.format(PAGE_COMMON_URL,i));
            }

        } else if (page.getUrl().get().matches(PAGE_COMMON_URL_REGEX)){
            //添加每页记录url
            List<String> urlList = document.select("#tagContent>div>div>div.subtit.dyh>div>h3>a").eachAttr("href");
            page.addTargetRequests(urlList);
        } else {
           String name = document.select("body>div.art_wra>div.art_l>div.widsp>b").text();
           String alias = document.select("body>div.art_wra>div.art_l>ul.niname>li:nth-child(1)>p:nth-child(1)").attr("title");
           StringBuffer stringBuffer = new StringBuffer();
           if (StringUtils.isEmpty(alias)){
               alias = "暂无";
               stringBuffer.append(name).append("\t").append(alias).append("\r").append("\n");
           } else {
               String[] aliasArr = alias.split("，");
               for (String str:aliasArr){
                   stringBuffer.append(name).append("\t").append(str).append("\r").append("\n");
               }
               writeFile(stringBuffer.toString());
           }

        }

    }

    private void writeFile(String str){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File("D:\\a.txt"),true);
            fileWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fileWriter != null){
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void run(){
        Spider.create(this).addUrl(START_URL).thread(5).run();
    }

    public static void main(String[] args) throws Exception{
        Spider.create(new DiseaseSite()).addUrl(START_URL).thread(5).run();
//        Spider.create(new DiseaseSite()).addUrl(START_URL).run();

//        boolean flag = "http://jb.9939.com/jbzz_t1/".matches(PAGE_COMMON_URL_REGEX);
//        Pattern pattern = Pattern.compile(PAGE_COMMON_URL_REGEX);
//        boolean flag = pattern.matcher("http://jb.9939.com/jbzz_t1/?page=1").find();
//        System.out.println(flag);

    }
}

package com.example.reptile.processor.disease;

import com.example.reptile.mapper.ZhongYiBaoDianDiseaseMapper;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

/**
 * @Author:shixianqing
 * @Date:2018/10/1016:17
 * @Description:
 **/
public class JIBinBaiKeSite implements PageProcessor {
    private static final String START_URL = "http://jbk.39.net/bw_t1/";
    private static final String START_URL_REGEX = "http://jbk\\.39\\.net/bw_t1/";
    private String PAGE_COMMON_URL = "http://jbk.39.net/bw_t1_p{0}#ps";
    private static String PAGE_COMMON_URL_REGEX = "http://jbk\\.39\\.net/bw_t1_p[0-9]+#ps";
    private Site site = Site.me().setCycleRetryTimes(3).setTimeOut(120000).setSleepTime(1).setUserAgent( (int)(Math.random()*2 ) == 1 ? "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.146 Safari/537.36" : "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134");

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        Document document = html.getDocument();
        if (page.getUrl().get().matches(START_URL_REGEX)){

            for (int i=1;i<=199;i++){
                page.addTargetRequest(MessageFormat.format(PAGE_COMMON_URL,i));
            }
            List<String> urlList = document.select("#res_tab_2>div>dl>dt>h3>a").eachAttr("href");
            page.addTargetRequests(urlList);

        } else if (page.getUrl().get().matches(PAGE_COMMON_URL_REGEX)){
            //添加每页记录url
            List<String> urlList = document.select("#res_tab_2>div>dl>dt>h3>a").eachAttr("href");
            page.addTargetRequests(urlList);
        } else {
            writeFile(document);
        }

    }

    private void writeFile(Document document){
        String name = document.select("body > section.chronic.wrap > div.subnav.clearfix > span > b").text();
        String alias = document.select("body>section.chronic.wrap>div.content.clearfix>div.fl730.mr20>div>div.info>ul>li:nth-child(1)").first().ownText();
        StringBuffer stringBuffer = new StringBuffer();
        if (StringUtils.isEmpty(alias)){
            alias = "暂无";
            stringBuffer.append(name).append("\t").append(alias).append("\r").append("\n");
        } else {
            alias = alias.replace(",","，");
            String[] aliasArr = alias.split("，");
            for (String str:aliasArr){
                stringBuffer.append(name).append("\t").append(str).append("\r").append("\n");
            }
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File("D:\\b.txt"),true);
            fileWriter.write(stringBuffer.toString());
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
        Spider.create(new JIBinBaiKeSite()).addUrl(START_URL).thread(5).run();
//        Spider.create(new DiseaseSite()).addUrl(START_URL).run();

//        boolean flag = "http://jb.9939.com/jbzz_t1/".matches(PAGE_COMMON_URL_REGEX);
//        Pattern pattern = Pattern.compile(PAGE_COMMON_URL_REGEX);
//        boolean flag = pattern.matcher("http://jb.9939.com/jbzz_t1/?page=1").find();
//        System.out.println(flag);

    }
}

package com.example.reptile.processor.wuxi;

import org.apache.bcel.generic.IF_ACMPEQ;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:shixianqing
 * @Date:2018/10/2613:40
 * @Description:无锡药品目录
 **/
public class WuXiMedicine implements PageProcessor {

    private Site site = Site.me().setCycleRetryTimes(3).setTimeOut(120000).setSleepTime(1).setUserAgent( (int)(Math.random()*2 ) == 1 ? "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.146 Safari/537.36" : "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134");
    private static final String URL = "http://218.90.158.61/query/yaopin_query.html";
    private static final String REGEX_URL = "http://218.90.158.61\\/query\\/yaopin_query.html";

    private static final String START_URL = "http://218.90.158.61/public_page.html";
    private static final String START_URL_REGEX = "http://218.90.158.61\\/public_page.html";

    private static final String DETAIL_URL = "http://218.90.158.61/query/yaopin_query_dialog.html?aka080={0}";
    @Override
    public void process(Page page) {
        String url = page.getUrl().get();
        System.out.println("请求url-------->>>>"+url);
        Document document = page.getHtml().getDocument();

        if (url.matches(START_URL_REGEX)){
            String href = document.select("body>div.contain>div.box_persent>div.main_left>div.title_public>div>dl.public_left>dd.icon_public03>a").attr("href");
//            page.addTargetRequest(href);
            for (int i=1;i<=1;i++){
                Request request = new Request();
                request.setMethod(HttpConstant.Method.POST);
                request.setUrl("http://218.90.158.61"+href);
                List<NameValuePair> nvs = new ArrayList<NameValuePair>();
                nvs.add(new BasicNameValuePair("pageNo", ""+i));
                nvs.add(new BasicNameValuePair("cka061", ""));
                nvs.add(new BasicNameValuePair("aka080", ""));
                nvs.add(new BasicNameValuePair("aka061", ""));
                if (i==1){
                    nvs.add(new BasicNameValuePair("captcha", "wxnp"));
                }
                //转换为键值对数组
                NameValuePair[] values = nvs.toArray(new NameValuePair[] {});
                //将键值对数组添加到map中
                Map<String, Object> params = new HashMap<>();
                //key必须是：nameValuePair
                params.put("nameValuePair", values);
                request.setExtras(params);
                page.addTargetRequest(request);
            }
        } else if (url.matches(REGEX_URL)){
            List<String> codes = document.select("#query_div>div:nth-child(3)>div.content_box>div>dl:nth-child(4)>dd").eachText();
            System.out.println(codes);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }


    public static void main(String[] args) {

//        List<Request> requests = new ArrayList<>();
//        for (int i=1;i<=1;i++){
//            Request request = new Request();
//            request.setMethod(HttpConstant.Method.POST);
//            request.setUrl(URL);
//            List<NameValuePair> nvs = new ArrayList<NameValuePair>();
//            nvs.add(new BasicNameValuePair("pageNo", ""+i));
//            nvs.add(new BasicNameValuePair("cka061", ""));
//            nvs.add(new BasicNameValuePair("aka080", ""));
//            nvs.add(new BasicNameValuePair("aka061", ""));
//            nvs.add(new BasicNameValuePair("captcha", ""));
//            //转换为键值对数组
//            NameValuePair[] values = nvs.toArray(new NameValuePair[] {});
//            //将键值对数组添加到map中
//            Map<String, Object> params = new HashMap<>();
//            //key必须是：nameValuePair
//            params.put("nameValuePair", values);
//            request.setExtras(params);
//            requests.add(request);
//        }

        Spider.create(new WuXiMedicine()).addUrl(START_URL).thread(5).run();
    }
}

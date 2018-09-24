package com.example.reptile.processor;

import com.alibaba.fastjson.JSONObject;
import com.example.reptile.mapper.HospitalMapper;
import com.example.reptile.model.Hospital;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:shixianqing
 * @Date:2018/9/2118:17
 * @Description:
 **/
@Service
public class SpiderMedicalBusinessSite {

    private static final String ENTRY_URL = "http://yy.ylsw.net/list/yy.html";
    private static final String PAGE_URL = "http://yy.ylsw.net/list/p-{0}_yy.html";
    private static final int TOTAL_NUM = 667;
    private static final int START_NUM = 1;
    private static final int TIME_OUT = 1200000;

    @Autowired
    private HospitalMapper hospitalMapper;

    public void process() throws Exception{

        //分页
        for (int i = START_NUM; i<TOTAL_NUM; i++){
            String url = "";
            if (i == START_NUM){
                url = ENTRY_URL;
            }else {
                url = MessageFormat.format(PAGE_URL,i);
            }
//            String userAgent = (int)(Math.random()*2 ) == 1 ? "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36" +
//                    " (KHTML, like Gecko) Chrome/65.0.3325.146 Safari/537.36" : "Mozilla/5.0 (Windows NT 10.0;" +
//                    " Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134";
            String userAgent =  "Mozilla/5.0 (Windows NT 10.0;" +
                    " Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134";
            Document document = Jsoup.connect(url).userAgent(userAgent).ignoreHttpErrors(true).maxBodySize(0).ignoreContentType(true).timeout(TIME_OUT).get();
            if (document == null){
                System.out.println("url--------->>>>>>>"+url);
            }
            Element element = document.getElementById("SearchList");
            List<Element> spans = element.getElementsByClass("blue");

            List<Hospital> hospitals = new ArrayList<>();
            for (Element span:spans){
                Element a = span.getElementsByTag("a").first();
                String htmlUrl = a.attr("href");
                Document detailDoc = Jsoup.connect(htmlUrl).userAgent(userAgent).maxBodySize(0).ignoreHttpErrors(true).ignoreContentType(true).timeout(TIME_OUT).get();
                Hospital hospital = new Hospital();
                Element hospitalModule = detailDoc.getElementsByClass("hospital_left").first();
                if (hospitalModule == null){
                    System.out.println("detailDoc-------------->>>"+detailDoc);
                    System.out.println("htmlUrl-------------->>>"+htmlUrl);
                    System.out.println("url--------->>>>>>>"+url);
                }

                Element contact = hospitalModule.getElementsByClass("pbox").get(0);//联系方式
                Element baseInfo = hospitalModule.getElementsByClass("pbox").get(1);//基本资料


                parseContact(contact,hospital);
                parseBaseInfo(baseInfo,hospital);

                hospitals.add(hospital);

            }

            hospitalMapper.insert(hospitals);

//            System.out.println(JSONObject.toJSONString(hospitals,true));
        }


    }

    private void parseBaseInfo(Element contact, Hospital hospital) {
        List<String> strongText = contact.select("strong").eachText();
        List<TextNode> textNodes = contact.textNodes().subList(1,contact.textNodes().size());
        for (int i = 0;i<strongText.size();i++){
            String text = strongText.get(i);
            if (text == null || text == "" || text == " ")
                continue;

            if (text.contains("医院名称")){
                hospital.setHospitalName(textNodes.get(i).text());
            }
            if (text.contains("所在地区")){
                hospital.setHospitalLocation(textNodes.get(i).text());
            }

            if (text.contains("院长姓名")){
                hospital.setHospitalLeaderName(textNodes.get(i).text());
            }

            if (text.contains("建院年份")){
                hospital.setBuildTime(textNodes.get(i).text());
            }

            if (text.contains("医院类型")){
                hospital.setHospitalType(textNodes.get(i).text());
            }

            if (text.contains("医院等级")){
                hospital.setHospitalLevel(textNodes.get(i).text());
            }
            if (text.contains("是否医保")){
                hospital.setIsMedicalInsurance(textNodes.get(i).text());
            }
        }
    }

    private void parseContact(Element contact, Hospital hospital) {
        List<String> strongText = contact.select("strong").eachText();
        List<TextNode> textNodes = contact.textNodes().subList(1,contact.textNodes().size());

        for (int i = 0;i<strongText.size();i++){
            String text = strongText.get(i);
            if (text == null || text == "" || text == " ")
                continue;

            if (text.contains("医院网址")){
                hospital.setHospitalUrl(textNodes.get(i).text());
            }

            if (text.contains("联系电话")){
                hospital.setTelephoneNo(textNodes.get(i).text());
            }

            if (text.contains("医院地址")){
                hospital.setHospitalAddr(textNodes.get(i).text());
            }

            if (text.contains("医院邮编")){
                hospital.setHospitalPost(textNodes.get(i).text());
            }
        }
    }

    public static void main(String[] args) throws Exception{
        new SpiderMedicalBusinessSite().process();
    }
}

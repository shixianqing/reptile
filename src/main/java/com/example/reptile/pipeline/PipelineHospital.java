package com.example.reptile.pipeline;

import com.example.reptile.model.Hospital;
import com.example.reptile.service.HospitalMagicService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.Html;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Author:shixianqing
 * @Date:2018/9/25 11:20
 * @Description:
 **/
@Component
public class PipelineHospital implements Pipeline {

    @Autowired
    private HospitalMagicService hospitalMagicService;
    @Override
    public void process(ResultItems resultItems, Task task) {
        String url = resultItems.getRequest().getUrl();
        Html html = (Html) resultItems.get("html");
        Document doc = html.getDocument();
       if (!Pattern.matches("http://yy.ylsw.net/hospitaldetail-([A-Za-z0-9]+).html", url)){
           return;
       }

        Element contactEle = doc.select(".hospital_left").get(0).getElementsByClass("pbox").first();
        Element baseInfoEle = doc.select(".hospital_left").get(0).getElementsByClass("pbox").get(1);
        Hospital hospital = new Hospital();
        parseBaseInfo(baseInfoEle,hospital);
        parseContact(contactEle,hospital);
        hospitalMagicService.addHospital(hospital);

    }

    private void parseBaseInfo(Element contact, Hospital hospital) {
        List<String> strongText = contact.select("strong").eachText();
        List<TextNode> textNodes = contact.textNodes().subList(1,contact.textNodes().size());
        for (int i = 0;i<strongText.size();i++){
            String text = strongText.get(i);
            if (text.contains("医院名称")){
                hospital.setHospitalName(textNodes.get(i).text());
                continue;
            }
            if (text.contains("所在地区")){
                hospital.setHospitalLocation(textNodes.get(i).text());
                continue;
            }

            if (text.contains("院长姓名")){
                hospital.setHospitalLeaderName(textNodes.get(i).text());
                continue;
            }

            if (text.contains("建院年份")){
                hospital.setBuildTime(textNodes.get(i).text());
                continue;
            }

            if (text.contains("医院类型")){
                hospital.setHospitalType(textNodes.get(i).text());
                continue;
            }

            if (text.contains("医院等级")){
                hospital.setHospitalLevel(textNodes.get(i).text());
                continue;
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

            if (text.contains("医院网址")){
                hospital.setHospitalUrl(textNodes.get(i).text());
                continue;
            }

            if (text.contains("联系电话")){
                hospital.setTelephoneNo(textNodes.get(i).text());
                continue;
            }

            if (text.contains("医院地址")){
                hospital.setHospitalAddr(textNodes.get(i).text());
                continue;
            }

            if (text.contains("医院邮编")){
                hospital.setHospitalPost(textNodes.get(i).text());
            }
        }
    }
}

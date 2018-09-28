package com.example.reptile.processor.medicine;

import com.alibaba.fastjson.JSONObject;
import com.example.reptile.mapper.MedicineMapper;
import com.example.reptile.model.Medicine;
import com.example.reptile.web.HttpUtil;
import org.apache.http.client.methods.HttpPost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:shixianqing
 * @Date:2018/9/2811:25
 * @Description: 药品爬虫
 **/
@Service
public class MedicineSpider implements Process {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String url = "http://szsbzx.jsszhrss.gov.cn:9900/web/website/pubQuery/pubQueryAction?frameControlSubmitFunction=getPagesAjax";
    @Autowired
    private MedicineMapper medicineMapper;
    public void process() throws Exception{

        Map<String,String> params = new HashMap<>();
        params.put("querytype","ypml");
        params.put("sfcf","");
        params.put("tym","");
        params.put("spm","");
        params.put("sfzf","1");//是否作废 0-是  1-否
        params.put("pageIndex","1");
        params.put("pageCount","20");
        String result = HttpUtil.send(HttpUtil.getReq(url,params));
        logger.debug("结果集：{}",result);

        long totalPages = 0;
        Map map = new HashMap();
        if (!ObjectUtils.isEmpty(result)){
            map = JSONObject.parseObject(result,Map.class);
            String html = map.get("pageBarStr").toString();
            Document document = Jsoup.parse(html);
           //获取总页数
            Elements elements = document.select("#pages>option");
            totalPages = elements.size();
            logger.debug("总页数：{}",totalPages);
        }

        //遍历每页数据
        for (int i=1;i<=totalPages;i++){
            params.put("pageIndex",String.valueOf(i));
            params.put("pageCount","20");
            Spider.blockingQueue.put(HttpUtil.getReq(url,params));
        }

        Spider.getInstance(this).run();

    }

    public void parseHtml(HttpPost httpPost) throws Exception{
        String response = HttpUtil.send(httpPost);
        logger.debug("响应结果：{}",response);
        if (ObjectUtils.isEmpty(response)){
           return;
        }
        Map map = JSONObject.parseObject(response,Map.class);
        Object html = map.get("pagelistajax");
        if (html == null)
            return;
        Document document = Jsoup.parse(html.toString());
        List<Medicine> medicines = new ArrayList<>();
        Elements elements = document.select("tr");
        for (int i=1;i<elements.size();i++){
            Element element = elements.get(i);
            Medicine medicine = new Medicine();
//            int size = element.childNodeSize();
            Elements tdList = element.getElementsByTag("td");
            medicine.setMedicineName(tdList.get(0).text());
            medicine.setMedicineNameCommon(tdList.get(1).text());
            medicine.setProductEnterprise(tdList.get(2).text());
            medicine.setMedicineStandard(tdList.get(3).text());
            medicine.setConvertRatio(Integer.parseInt(tdList.get(4).text()));
            medicine.setPackagingMaterial(tdList.get(5).text());
            medicine.setUnit(tdList.get(6).text());
            medicine.setMedicineForm(tdList.get(7).text());
            medicine.setSelfPaymentRatio(tdList.get(8).text());
            medicine.setChildRatio(tdList.get(9).text());
            medicine.setBearRatio(tdList.get(10).text());
            medicine.setInjuryRatio(tdList.get(11).text());
            medicine.setPrescriptionFlag(tdList.get(12).text());
            medicine.setCancleFlag(tdList.get(13).text());
            medicine.setMedicineClassify(Long.parseLong(tdList.get(14).text()));
            medicine.setPayRange(tdList.get(15).text());

            medicines.add(medicine);
        }


        //入数据库
        medicineMapper.insert(medicines);
    }


    public static void main(String[] args) throws Exception{
        new MedicineSpider().process();
    }
}

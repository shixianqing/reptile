package com.example.reptile.processor.liaoNin.shengyang;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.reptile.util.FileUtil;
import com.google.gson.JsonObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.*;

/**
 * 沈阳医保三目录
 */
public class ShengYangMedicineInsure {

    //诊疗
    private static String treamentReqUrl = "http://ydjy.syyb.gov.cn/data/AjaxService.aspx?method=selectzlml&pageSize=10";
    //药品
    private static String medicineReqUrl = "http://ydjy.syyb.gov.cn/data/AjaxService.aspx?method=selectypml&pageSize=10";

    //诊疗总数
    private static final Integer treamentTotalPages = 475;

    //药品总数
    private static final Integer medicineTotalPages = 1134;

    private static final BlockingQueue<List<Map>> QUEUE = new LinkedBlockingQueue();

    private Object object = new Object();


    private void parseInsure(String reqUrl,Integer totalPages) throws Exception{
        for (int i = 687;i<=totalPages;i++){
            String url = reqUrl+"&pageIndex="+i+"&_="+new Date().getTime();
            System.out.println("请求地址为："+url);
            String response = "";
            try{
                response = execute(url);
            }catch (Exception e){
                e.printStackTrace();
                response = execute(url);
            }

            System.out.println("请求地址为："+url+"，响应结果为："+response+"");
//            LOGGER.debug("请求地址为：{}，响应结果为：{}",url,response);

            if (ObjectUtils.isEmpty(response)){
                continue;
            }

            Map respMap = JSONObject.parseObject(response,Map.class);

            if (ObjectUtils.isEmpty(respMap.get("data"))){
                continue;
            }

            List<Map> data = (List) respMap.get("data");

//            QUEUE.add(data);
            saveMedicineData(data);
//            saveTreamentData(data);
        }

//        parseData();
    }

    private String execute(String url) throws Exception{
        return getConnection(url).execute().body();
    }

    private void parseData(){
        ExecutorService executor = new ThreadPoolExecutor(5,10,60, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        while (QUEUE.size() != 0){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    synchronized (object){
                       List<Map> list = QUEUE.poll();
                        if (list != null ){
                            try {
//                                saveTreamentData(list);
                                saveMedicineData(list);
                            } catch (Exception e) {
                                e.printStackTrace();
                                executor.shutdown();
                            }
                        }else {
                            executor.shutdown();
                        }
                    }
                }
            });
        }
    }

    private void saveTreamentData(List<Map> list) {
        for (Map map:list){
            StringBuffer stringBuffer = new StringBuffer();

            stringBuffer.append(map.get("bm")).append("\t").append(map.get("xmmc")).append("\t")
                    .append(map.get("xmnh")).append("\t").append(map.get("cwnr")).append("\t")
                    .append(map.get("jjdw")).append("\t").append(map.get("xmsm")).append("\t")
                    .append(map.get("yblb")).append("\t").append(map.get("bz")).append("\t")
                    .append(map.get("xxzfbl")).append("\r").append("\n");

            FileUtil.writeData(stringBuffer,"D:/shenyangTreament.txt");
        }
    }

    private void saveMedicineData(List<Map> list) {
        for (Map map:list){
            StringBuffer stringBuffer = new StringBuffer();

            stringBuffer.append(map.get("ypzcmc")).append("\t").append(map.get("ypfl")).append("\t")
                    .append(map.get("ypjx")).append("\t").append(map.get("yblb")).append("\t")
                    .append(map.get("xxzfbl")).append("\t").append(map.get("ypxzbz")).append("\t")
                    .append("\r").append("\n");

            FileUtil.writeData(stringBuffer,"D:/shenyangMedicine.txt");
        }
    }


    private Connection getConnection(String url){

        Map header = new HashMap();
        header.put("Accept","application/json, text/javascript, */*; q=0.01");
        header.put("Accept-Encoding","gzip, deflate");
        header.put("Accept-Language","zh-CN,zh;q=0.9");
        header.put("Connection","keep-alive");
        header.put("Cookie","_gscu_322517208=39090331d8nftb58; _gscbrs_322517208=1; _gscs_322517208=39090331bt8s4c58|pv:1; uid=112; JSESSIONID=Ku1Y7g8N2tcURBInvcG8WC7Yr9z2xSLyd3JDLjN55Kk3i8NXpUbs!805921943; SERVERID=s1");
        header.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        header.put("X-Requested-With","XMLHttpRequest");
        header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.81 Safari/537.36");
        return Jsoup.connect(url).headers(header).
                method(Connection.Method.GET).timeout(1200000).ignoreContentType(true);
    }

    public static void main(String[] args) throws Exception{
         new ShengYangMedicineInsure().parseInsure(medicineReqUrl,medicineTotalPages);
//         new ShengYangMedicineInsure().parseInsure(treamentReqUrl,treamentTotalPages);
    }

}

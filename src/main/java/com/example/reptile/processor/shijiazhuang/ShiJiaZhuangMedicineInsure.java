package com.example.reptile.processor.shijiazhuang;

import com.alibaba.fastjson.JSONObject;
import com.example.reptile.util.FileUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.util.ObjectUtils;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 沈阳医保三目录
 */
public class ShiJiaZhuangMedicineInsure {

    //诊疗
    private static String treamentReqUrl = "http://www.sjz12333.gov.cn/si/pages/zcore/zajax.jsp?page=ylfw&n={0}&size=20&name1=&name2=&name3=&value1=&value2=&value3=&_={1}";
    //药品
    private static String medicineReqUrl = "http://www.sjz12333.gov.cn/si/pages/zcore/zajax.jsp?page=ylyp&n={0}&size=20&name1=&name2=&name3=&value1=&value2=&value3=&_={1}";

    //诊疗总数
    private static final Integer treamentTotalPages = 511;

    //药品总数
    private static final Integer medicineTotalPages = 8859;

    private static final BlockingQueue<List<Map>> QUEUE = new LinkedBlockingQueue();

    private Object object = new Object();


    private void parseInsure(String reqUrl,Integer totalPages) throws Exception{
        for (int i = 417;i<=totalPages;i++){
            System.out.println("---------请求第【"+i+"】页数据-----------");
            String url = MessageFormat.format(reqUrl,i+"",new Date().getTime());
            System.out.println("请求地址为："+url);

            String result = execute(url).replace("\n","").replace("\r","").
                    replaceAll("\\\\","").replaceAll("\"T\"","T")
                    .replaceAll("\"阴道分泌物唾液酸苷酶\"","阴道分泌物唾液酸苷酶")
                    .replaceAll("\"白细胞酯酶\"","白细胞酯酶")
                    .replaceAll("\"过氧化氢浓度\"","过氧化氢浓度");
            System.out.println(result);
            Map resultMap = JSONObject.parseObject(result);
            Object resultArray = resultMap.get("resultArray");
            if (ObjectUtils.isEmpty(resultArray)){
                continue;
            }
            List<Map> list = JSONObject.parseObject(resultArray.toString(),List.class);
//            saveMedicineData(list,i);
            saveTreamentData(list,i);
        }

    }

    private String execute(String url) throws Exception{
        return getConnection(url).execute().body();
    }


    private void saveTreamentData(List<Map> list,int index) {
        for (int i=0;i<list.size();i++){
            StringBuffer stringBuffer = new StringBuffer();
            Map map = list.get(i);
            if (1 == index && 0 == i){
                stringBuffer.append("编码").append("\t").append("服务项目名称").append("\t").append("大类名称")
                        .append("\t").append("职工首先自付比例").append("\t")
                        .append("居民首先自付比例").append("\t").append("三级价格").append("\t")
                        .append("二级价格").append("\t").append("一级价格").append("\t")
                        .append("社区价格").append("\r").append("\n");
            }
            stringBuffer.append(map.get("AKA040")).append("\t").append(map.get("AKA041")).append("\t")
                    .append(map.get("AKA042")).append("\t").append(map.get("AKA043")).append("\t")
                    .append(map.get("AKA044")).append("\t").append(map.get("AKA046")).append("\t")
                    .append(map.get("AKA047")).append("\t").append(map.get("AKA048")).append("\t")
                    .append(map.get("AKA049")).append("\r").append("\n");

            FileUtil.writeData(stringBuffer,"D:/shijiazhuangTreament.txt");
        }
    }

    private void saveMedicineData(List<Map> list,int index) {
        for (int i=0;i<list.size();i++){
            StringBuffer stringBuffer = new StringBuffer();
            Map map = list.get(i);
            if (1 == index && 0 == i){
                stringBuffer.append("大类名称").append("\t").append("分类名称").append("\t").append("药品名称")
                        .append("\t").append("剂型名称").append("\t").append("职工首先自付比例").append("\t")
                        .append("居民首先自付比例").append("\r").append("\n");
            }
            stringBuffer.append(map.get("AKA032")).append("\t").append(map.get("AKA030")).append("\t")
                    .append(map.get("AKA031")).append("\t").append(map.get("AKA033")).append("\t")
                    .append(map.get("AKA034")).append("\t").append(map.get("AKA035")).append("\t")
                    .append("\r").append("\n");

            FileUtil.writeData(stringBuffer,"D:/shijiazhuangMedicine.txt");
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
//         new ShengYangMedicineInsure().parseInsure(medicineReqUrl,medicineTotalPages);
//         new ShengYangMedicineInsure().parseInsure(treamentReqUrl,treamentTotalPages);
        new ShiJiaZhuangMedicineInsure().parseInsure(treamentReqUrl,treamentTotalPages);
    }

}

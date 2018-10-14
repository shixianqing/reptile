package com.example.reptile.processor.liaoNin.dalian;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.reptile.util.FileUtil;
import com.example.reptile.web.Request;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.*;

/**
 * 沈阳医保三目录
 */
public class DaLianMedicineInsure {

    //诊疗
    private static String treamentReqUrl = "http://ydjy.syyb.gov.cn/data/AjaxService.aspx?method=selectzlml&pageSize=10";
    //药品
    private static String medicineReqUrl = "http://bsdt.dl12333.gov.cn/getJgData.action";

    //药品总数
    private static final Integer medicineTotalPages = 8865;

    private static final BlockingQueue<JSONArray> QUEUE = new LinkedBlockingQueue();

    private Object object = new Object();


    private void parseInsure(String reqUrl,Integer totalPages) throws Exception{
        for (int i = 8282;i<=totalPages;i++){
            System.out.println("第"+i+"页-----------查询");
           Map params = new HashMap();
           Integer rowStart = (i-1)*20+1;
           Integer rowEnd = ((i-1)*20+1)+20;
           params.put("url","YLBXYPML_List");
           params.put("param","AKA061=&RowStart="+rowStart+"&RowEnd="+rowEnd+"");
           String result = new Request(reqUrl,params).getConnection().execute().body();
           if (ObjectUtils.isEmpty(result)){
               continue;
           }
           int index = result.indexOf("[");
           int lastIndex = result.lastIndexOf("]");
           result = result.substring(index,lastIndex+1);
           result = StringEscapeUtils.unescapeJava(result);

           System.out.println(result);

           JSONArray array = JSONObject.parseArray(result);

            saveMedicineData(array);

//           QUEUE.add(array);

        }

//        parseData();
    }


    private void parseData(){
        ExecutorService executor = new ThreadPoolExecutor(5,10,60, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        while (QUEUE.size() != 0){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    synchronized (object){
                       JSONArray list = QUEUE.poll();
                        if (list != null ){
                            try {
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


    private void saveMedicineData(JSONArray array) {
        Iterator<Object> iterator = array.iterator();
        while (iterator.hasNext()){
            Map map = (Map) iterator.next();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(map.get("cka284")).append("\t").append(map.get("aka061")).append("\t")
                    .append(map.get("cka285")).append("\t").append(map.get("aka070")).append("\t")
                    .append(map.get("aka069")).append("\t")
                    .append("\r").append("\n");

            FileUtil.writeData(stringBuffer,"D:/daLianMedicine.txt");
        }
    }



    public static void main(String[] args) throws Exception{
         new DaLianMedicineInsure().parseInsure(medicineReqUrl,medicineTotalPages);
//         new ShengYangMedicineInsure().parseInsure(treamentReqUrl,treamentTotalPages);



    }

}

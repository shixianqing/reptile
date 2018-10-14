package com.example.reptile.processor.kunming;

import com.example.reptile.util.FileUtil;
import com.example.reptile.web.Request;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.jsoup.Connection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class KunMIngMedicine {

    private static final String URL = "http://www.kmybzc.com/yb/idxqurey.yb";

    private static final Integer TOTAL_PAGES = 863;


    public void process() throws Exception{
        Map params = new HashMap();
        params.put("pagecount","100");
        for (int i=1;i<=TOTAL_PAGES;i++){
            System.out.println("第"+i+"页查询开始");
            params.put("indextype",String.valueOf(i));
            params.put("page",String.valueOf(i));
            Connection connection = new Request(URL,params).getConnection();
            getResult(connection);
        }

       // sendReq();
    }

    private void getResult(Connection connection) throws Exception{
        String result = connection.execute().body();
        System.out.println(result);
        try {
            JSONObject map = JSONObject.fromObject(result);
            JSONArray array = map.getJSONArray("ilist");
            saveMedicineData(array);
//            QUEUE.add(array);
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof JSONException){
                try {
                    result = result.replace("\\","");
                    JSONObject map = JSONObject.fromObject(result);
                    JSONArray array = map.getJSONArray("ilist");
                    System.out.println("重新发起保存");
                saveMedicineData(array);
//                    QUEUE.add(array);
                }catch (Exception r){
                    r.printStackTrace();
                }


            }
        }

    }

    private void saveMedicineData(JSONArray array) {
        Iterator<Object> iterator = array.iterator();
        while (iterator.hasNext()){
            Map map = (Map) iterator.next();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(map.get("i01")).append("\t").append(map.get("i02")).append("\t")
                    .append(map.get("i03")).append("\t").append(map.get("i08")).append("\t")
                    .append(map.get("i10")).append("\t")
                    .append("\r").append("\n");

            FileUtil.writeData(stringBuffer,"D:/kunMingMedicine.txt");
        }
    }




    public static void main(String[] args) throws Exception{
        new KunMIngMedicine().process();
    }
}

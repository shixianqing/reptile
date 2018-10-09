package com.example.reptile.processor.chongqing;

import com.alibaba.fastjson.JSONObject;
import com.example.reptile.mapper.ChongQingTreamentMapper;
import com.example.reptile.web.Request;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TreamentSite {

    private static String reqUrl = "http://ggfw.cqhrss.gov.cn/ggfw/QueryBLH_querySmXz.do";

    @Autowired
    private ChongQingTreamentMapper chongQingTreamentMapper;

    public void process() throws Exception {
        Map params = new HashMap();
        params.put("code","032");
        params.put("bzlxmmc","");
        params.put("azlxmlsh","");
        params.put("afydj","自费");
        params.put("currentPage","1");
        params.put("goPage","");
        Connection connection = new Request(reqUrl,params).getConnection();
        String response = connection.execute().body();
        Map resultMap = JSONObject.parseObject(response,Map.class);
        Map page = JSONObject.parseObject(resultMap.get("page").toString(),Map.class);
        int totalPages = (int) page.get("pageCount");

        for (int i = 1;i<=totalPages;i++){
            params.put("currentPage",String.valueOf(i));
            requestResult(params);
        }
        System.out.println(response);
    }

    private void requestResult(Map params) {
        try {
            Connection connection = new Request(reqUrl,params).getConnection();
            String response = connection.execute().body();
            Map resultMap = JSONObject.parseObject(response,Map.class);
            List<Map> list = JSONObject.parseObject(resultMap.get("result").toString(),List.class);
            chongQingTreamentMapper.insert(list);
        }catch (Exception e){
            e.printStackTrace();
            requestResult(params);
        }

    }

    public static void main(String[] args) throws Exception{
        new TreamentSite().process();
    }
}

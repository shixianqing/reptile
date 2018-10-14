package com.example.reptile.processor.shanghai;

import com.example.reptile.util.FileUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ShangHaiMedicineSite {

    private static final String REQ_URL = "http://202.96.245.182/xxcx/ddyy.jsp";//定点医院
//    private static final String REQ_URL = "http://202.96.245.182/xxcx/yp.jsp?lm=4";//药品
    private static final BlockingQueue<Document> queue = new LinkedBlockingQueue<>();

    private Object object = new Object();

    public void process() throws Exception {
        for (int i=1;i<=161;i++){
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair("pageno",String.valueOf(i)));//医院
//            list.add(new BasicNameValuePair("sPagenum",String.valueOf(i)));//药品
//            list.add(new BasicNameValuePair("fl0",String.valueOf(2)));//药品
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"utf-8");
            getDocument(REQ_URL,entity);
        }

        insertData(queue);

    }

    private void getDocument(String url, HttpEntity entity) throws IOException {
        System.out.println("请求URL："+url);
        HttpClient httpClient = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(120000).setConnectionRequestTimeout(60000).setSocketTimeout(60000).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        httpPost.setEntity(entity);
        HttpResponse response = httpClient.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity(),"UTF-8");
        Document document = Jsoup.parse(result);
        queue.add(document);
    }

    /**
     * 解析药品页面
     * @param document
     * @throws Exception
     */
    private void parseDocument(Document document) throws Exception{
        Elements elements = document.select("#m_content>table:nth-child(1)>tbody>tr:nth-child(2)>td>table:nth-child(6)>tbody>tr").attr("bgcolor","#FFFFFF");

        StringBuffer stringBuffer = new StringBuffer();
        for (int i=2;i<elements.size()-1;i++){
            Element element = elements.get(i);
            Elements childs  = element.getElementsByTag("td");

            String name = childs.get(0).text();
            String form = childs.get(1).text();
            String level = childs.get(2).text();
            String pay = childs.get(3).text();
            stringBuffer.append(name)
                    .append("\t").append(form).append("\t")
                    .append(level).append("\t").append(pay).append("\t")
                    .append("\r").append("\n");

        }
        System.out.println(stringBuffer.toString());
        FileUtil.writeData(stringBuffer,"D:/shanghaiMedicine.txt");

    }

    private void parseHospitalDocument(Document document) throws Exception{
        Elements elements = document.select("#m_content>table:nth-child(3)>tbody>tr>td>table>tbody>tr").attr("bgcolor","#FFFFFF");
//        Elements elements = document.select("#m_content>table:nth-child(1)>tbody>tr:nth-child(2)>td>table:nth-child(6)>tbody>tr").attr("bgcolor","#FFFFFF");

        StringBuffer stringBuffer = new StringBuffer();
        for (int i=1;i<elements.size();i++){
            Element element = elements.get(i);
            if (element.attributes().size()>1){
                continue;
            }
            Elements childs  = element.getElementsByTag("td");
            if (childs.size()<5){
                continue;
            }

            String area = childs.get(0).text();
            String name = childs.get(1).text();
            String addr = childs.get(2).text();
            String level = childs.get(3).text();
            stringBuffer.append(area)
                    .append("\t").append(name).append("\t")
                    .append(addr).append("\t").append(level).append("\t")
                    .append("\r").append("\n");

        }
        System.out.println(stringBuffer.toString());
        FileUtil.writeData(stringBuffer,"D:/hospital.txt");

    }


    private void insertData(BlockingQueue<Document> queue) {

        ExecutorService executor = new ThreadPoolExecutor(5,10,60, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(1000));
        while (queue.size() != 0){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    synchronized (object){
                        Document document = queue.poll();
                        if (document != null ){
                            try {
//                                parseDocument(document);
                                parseHospitalDocument(document);
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

        System.out.println("结束。。。。。。。。");

    }

    public static void main(String[] args) throws Exception{
//        List<NameValuePair> list = new ArrayList<>();
//        list.add(new BasicNameValuePair("pageno","1"));
//        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"utf-8");
//        new ShangHaiMedicineSite().getDocument("http://202.96.245.182/xxcx/ddyy.jsp",entity);

        new ShangHaiMedicineSite().process();
    }
}

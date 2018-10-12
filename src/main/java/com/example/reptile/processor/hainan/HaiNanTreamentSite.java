package com.example.reptile.processor.hainan;

import com.example.reptile.mapper.HaiNanTreamentMapper;
import com.example.reptile.model.HaiNanTreament;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author:shixianqing
 * @Date:2018/10/1117:11
 * @Description: 海南省人力资源和社会保障厅
 **/
@Service
public class HaiNanTreamentSite{

    private Site site = Site.me().setCycleRetryTimes(3).setTimeOut(120000).setSleepTime(1).setUserAgent( (int)(Math.random()*2 ) == 1 ? "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.146 Safari/537.36" : "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134");

    private static final String START_URL = "http://hi.lss.gov.cn/hnsi/sbbmfwybzlxm/";
    private static final String START_URL_REGEX = "http://hi\\.lss\\.gov\\.cn/hnsi/sbbmfwybzlxm/";

    private static String PAGE_COMMON_URL = "http://hi.lss.gov.cn/hnsi/sbbmfwybzlxm/index_{0}.html";
    public static String PAGE_COMMON_URL_REGEX = "http://hi\\.lss\\.gov\\.cn/hnsi/sbbmfwybzlxm/index_[0-9]+.html";

    private static String FIRST_PAGE_URL = "http://hi.lss.gov.cn/hnsi/sbbmfwybzlxm/index.html";
    public static String FIRST_PAGE_URL_REGEX = "http://hi\\.lss\\.gov\\.cn/hnsi/sbbmfwybzlxm/index.html";

    private BlockingQueue<Document> queue = new BlockingArrayQueue<>();

    @Autowired
    private HaiNanTreamentMapper haiNanTreamentMapper;

    public void process() throws Exception {
        Document document = getDocument(START_URL);
        Elements elements = document.select("body>div>div.mattle>div.you>dd>div.page>div>select>option");
        int totalPages = elements.size();
        for (int i=1;i<=5;i++){
            if (i == 1){
                getDocument(FIRST_PAGE_URL);
            }else {
                getDocument(MessageFormat.format(PAGE_COMMON_URL,i+1));
            }
        }

        insertData();

    }

    private void insertData() {

        ExecutorService executor = new ThreadPoolExecutor(5,10,60,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(1000));
        while (queue.size() != 0){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                   synchronized (this){
                       Document document = queue.poll();
                       if (document != null ){
                           try {
                               parseDocument(document);
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


    private void parseDocument(Document document) throws Exception{
        Elements elements = document.select("body>div>div.mattle>div.you>dd>table>tbody>tr.tr02");

        List<HaiNanTreament> haiNanTreaments = new ArrayList<>();
        for (Element element:elements){
            Elements childs  = element.children();
            String treamentNo = childs.get(0).text();
            String hospitalLevel = childs.get(1).text();
            String treamentName = childs.get(2).text();
            String unit = childs.get(5).text();
            BigDecimal standardPrice = new BigDecimal(childs.get(6).text()).setScale(BigDecimal.ROUND_HALF_UP,2);
            BigDecimal selfPayRatio = new BigDecimal(childs.get(7).text()).setScale(BigDecimal.ROUND_HALF_UP,2);
            haiNanTreaments.add(new HaiNanTreament(treamentNo,hospitalLevel,treamentName,unit,standardPrice,selfPayRatio));

        }

        haiNanTreamentMapper.insert(haiNanTreaments);
    }


    private Document getDocument(String url) throws IOException {
        System.out.println("请求URL："+url);
        HttpClient httpClient = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(120000).setConnectionRequestTimeout(60000).setSocketTimeout(60000).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        HttpResponse response = httpClient.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity(),"UTF-8");
        Document document = Jsoup.parse(result);
        queue.add(document);
        return document;
    }

    public static void main(String[] args) throws Exception {
        new HaiNanTreamentSite().process();
    }
}

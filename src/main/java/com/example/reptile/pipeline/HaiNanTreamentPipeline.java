package com.example.reptile.pipeline;

import com.example.reptile.model.HaiNanTreament;
import com.example.reptile.processor.hainan.HaiNanTreamentSit;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:shixianqing
 * @Date:2018/10/1117:32
 * @Description:
 **/
public class HaiNanTreamentPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {

        String url = resultItems.getRequest().getUrl();
        Html html = (Html) resultItems.get("html");
        if (!(url.matches(HaiNanTreamentSit.FIRST_PAGE_URL_REGEX)&&
                url.matches(HaiNanTreamentSit.PAGE_COMMON_URL_REGEX))){
            return;
        }

        Document document = html.getDocument();
        Elements elements = document.select("body>div>div.mattle>div.you>dd>table>tbody>tr");

        List<HaiNanTreament> haiNanTreaments = new ArrayList<>();
        for (Element element:elements){
            Elements childs = element.children();
            String treamentNo = childs.get(0).text();
            String hospitalLevel = childs.get(1).text();
            String treamentName = childs.get(2).text();
            String unit = childs.get(5).text();
            double standardPrice = Double.valueOf(childs.get(6).text());
            double selfPayRatio = Double.valueOf(childs.get(7).text());
            haiNanTreaments.add(new HaiNanTreament(treamentNo,hospitalLevel,treamentName,unit,standardPrice,selfPayRatio));

        }

        System.out.println(haiNanTreaments);
    }
}

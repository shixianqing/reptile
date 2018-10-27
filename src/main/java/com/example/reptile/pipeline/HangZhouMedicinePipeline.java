package com.example.reptile.pipeline;

import com.example.reptile.processor.hangzhou.HangZhouMedicine;
import com.example.reptile.util.FileUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * @Author:shixianqing
 * @Date:2018/10/2614:45
 * @Description:
 **/
public class HangZhouMedicinePipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        String url = resultItems.getRequest().getUrl();
        if (url.matches(HangZhouMedicine.START_URL_REGEX)){
            StringBuffer stringBuilder = new StringBuffer();
            stringBuilder.append("分类").append("\t").append("药品中文名称").append("\t")
                    .append("药品英文名称").append("\t").append("剂型").append("\t")
                    .append("备注").append("\r").append("\n");
            FileUtil.writeData(stringBuilder,"D:/hangzhouMedicine.txt");
            return;
        }

        Html html = resultItems.get("html");
        Document document = html.getDocument();
        Elements elements = document.select("#form1>div.box>div.kk>div.kkrg.rg>div.div_que1>div.div2>table>tbody>tr");
        parseElements(elements);
    }

    private void parseElements(Elements elements) {
        StringBuffer stringBuilder = new StringBuffer();
        for (int i=0;i<elements.size();i++){
            if (i == 0){
                continue;
            }
            Element element = elements.get(i);
            List<String> infos = element.children().eachText();
            System.out.println(infos);
            stringBuilder.append(infos.get(1)).append("\t").append(infos.get(2)).append("\t")
                    .append(infos.get(3)).append("\t").append(infos.get(4)).append("\r").append("\n");
        }
        FileUtil.writeData(stringBuilder,"D:/hangzhouMedicine.txt");
    }


}

package com.example.reptile.pipeline;

import com.example.reptile.processor.zhenjiang.ZheJiangMedicine;
import com.example.reptile.util.FileUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

public class ZheJiangMedicinePipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        Html html = (Html) resultItems.get("html");
        String url = resultItems.getRequest().getUrl();

        if (!url.matches(ZheJiangMedicine.pageCommonUrlRegex)){
            return;
        }

        Elements elements = html.getDocument().select("#form1>div.box>div.kk>div.kkrg.rg>div.div_que1>div.div2>table>tbody>tr");

        for (Element element:elements){
            if ("tr".equals(element.attr("class"))){
                continue;
            }

            List<String> list = element.children().eachText();

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(list.get(0)).append("\t").append(list.get(1)).append("\t")
                    .append(list.get(2)).append("\t").append(list.get(3)).append("\t")
                    .append(list.get(4)).append("\t")
                    .append("\r").append("\n");
            System.out.println("--------------开始保存数据---------"+stringBuffer.toString());

            FileUtil.writeData(stringBuffer,"D:/zhejiangMedicine.txt");
        }
    }

}

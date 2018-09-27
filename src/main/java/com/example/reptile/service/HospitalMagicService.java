package com.example.reptile.service;

import com.example.reptile.mapper.HospitalMapper;
import com.example.reptile.model.Hospital;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:shixianqing
 * @Date:2018/9/2110:36
 * @Description:
 **/
@Service
public class HospitalMagicService{

    @Autowired
    private HospitalMapper hospitalMapper;

    public void addHospital(Hospital hospital){
        hospitalMapper.add(hospital);
    }
}

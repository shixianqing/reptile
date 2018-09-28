package com.example.reptile.controller;

import com.example.reptile.processor.SpiderMedicalBusinessInfo;
import com.example.reptile.processor.SpiderMedicalBusinessSite;
import com.example.reptile.processor.medicine.MedicineSpider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hospital")
public class TestController {


    @Autowired
    SpiderMedicalBusinessSite spiderMedicalBusinessSite;
    @Autowired
    SpiderMedicalBusinessInfo spiderMedicalBusinessInfo;
    @Autowired
    MedicineSpider medicineSpider;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public void test() throws Exception{
        spiderMedicalBusinessInfo.spider();
    }

    @RequestMapping(value = "/medicine",method = RequestMethod.GET)
    public void medicine() throws Exception{
        medicineSpider.process();
    }

}

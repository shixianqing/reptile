package com.example.reptile.controller;

import com.example.reptile.processor.SpiderHospitalBusinessInfo;
import com.example.reptile.processor.SpiderMedicalBusinessSite;
import com.example.reptile.processor.chongqing.TreamentSite;
import com.example.reptile.processor.disease.DiseaseSite;
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
    SpiderHospitalBusinessInfo spiderHospitalBusinessInfo;
    @Autowired
    TreamentSite treamentSite;
    @Autowired
    MedicineSpider medicineSpider;
    @Autowired
    DiseaseSite diseaseSite;
    @Autowired
//    HaiNanTreamentSite haiNanTreamentSite;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public void test() throws Exception{
        spiderHospitalBusinessInfo.spider();
    }
    @RequestMapping(value = "/pai",method = RequestMethod.GET)
    public void pai() throws Exception{
        spiderMedicalBusinessSite.process();
    }

    @RequestMapping(value = "/medicine",method = RequestMethod.GET)
    public void medicine() throws Exception{
        medicineSpider.process();
    }
    @RequestMapping(value = "/chongqingTreament",method = RequestMethod.GET)
    public void chongqingTreament() throws Exception{
        treamentSite.process();
    }
    @RequestMapping(value = "/disease",method = RequestMethod.GET)
    public void disease() throws Exception{
        diseaseSite.run();
    }
    @RequestMapping(value = "/haiNan",method = RequestMethod.GET)
    public String haiNanTreamentSit() throws Exception{
//        haiNanTreamentSite.process();

        return "OK";
    }

}

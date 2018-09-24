package com.example.reptile.controller;

import com.example.reptile.processor.SpiderMedicalBusinessSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hospital")
public class TestController {


    @Autowired
    SpiderMedicalBusinessSite spiderMedicalBusinessSite;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public void test() throws Exception{
        spiderMedicalBusinessSite.process();
    }

}

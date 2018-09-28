package com.example.reptile.processor.medicine;

import org.apache.http.client.methods.HttpPost;


public interface Process {

    void parseHtml(HttpPost httpPost) throws Exception;
}

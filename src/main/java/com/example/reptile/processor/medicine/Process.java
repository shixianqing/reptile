package com.example.reptile.processor.medicine;

import org.jsoup.Connection;


public interface Process {

    void parseHtml(Connection connection) throws Exception;
}

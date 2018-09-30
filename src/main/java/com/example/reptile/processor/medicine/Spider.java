package com.example.reptile.processor.medicine;

import com.example.reptile.web.Request;
import org.apache.http.client.methods.HttpPost;
import org.jsoup.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author:shixianqing
 * @Date:2018/9/2814:39
 * @Description:
 **/
public class Spider implements Runnable {

    private Process process;
    public static final BlockingQueue<Request> blockingQueue = new LinkedBlockingQueue<>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExecutorService  executor = Executors.newCachedThreadPool();
    public static Spider getInstance(Process process){
        return new Spider(process);
    }

    public Spider(Process process){
        this.process = process;
    }
    @Override
    public void run() {
        while (!executor.isShutdown()){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Request request = blockingQueue.poll();
                    if (request == null){
                        executor.shutdown();
                        return;
                    }
                   Connection connection = blockingQueue.poll().getConnection();
                    try {
                        process.parseHtml(connection);
                    } catch (Exception e) {
                        int cycleTime = request.getCycleTime();
                        if (cycleTime>0){
                            try {
                                blockingQueue.put(request);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            request.setCycleTime(--cycleTime);

                        }
                        e.printStackTrace();
                    }
                }
            });
        }

        logger.debug("---------------------------执行完毕---------------------------------");

    }


}

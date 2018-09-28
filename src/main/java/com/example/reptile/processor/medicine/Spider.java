package com.example.reptile.processor.medicine;

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
    public static final BlockingQueue<Map> blockingQueue = new LinkedBlockingQueue<>();
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
        while (!Thread.currentThread().isInterrupted()){
            executor.execute(new Runnable() {

                @Override
                public void run() {
                    Map map = blockingQueue.poll();
                    if (map == null){
                        executor.shutdown();
                        return;
                    }

                   process.parseHtml(map);
                }
            });
        }

        logger.debug("---------------------------执行完毕---------------------------------");

    }


}

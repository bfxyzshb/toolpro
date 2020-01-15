package com.al.study;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class TestThreadPool {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue(8), new ThreadPoolExecutor.DiscardOldestPolicy());
        //Future<String> result = executor.submit(callable);
        List<Callable<String>> callList = new ArrayList<Callable<String>>();
        for (int k = 0; k < 200; k++) {
            callList.add(new MyTask());
        }
        try {
            List<Future<String>> result = executor.invokeAll(callList);
            System.out.println("over------------");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


}

class MyTask implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("执行任务!");
        return "ok";
    }

}
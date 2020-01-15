package com.thread.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(10,10,30, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(1000));
        threadPoolExecutor.shutdown();
        threadPoolExecutor.getActiveCount();
    }
}

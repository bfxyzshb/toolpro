package com.weibo.test;

import java.util.concurrent.ExecutorService;

public class ThreadTest {
    public static void main(String[] args) {
        new Thread(null, new Runnable() {
            int dep = 0;

            @Override
            public void run() {
                try {
                    d();
                } catch (Throwable e) {
                }
                System.out.println(dep);
            }

            void d() {
                dep++;
                d();
            }
        },"thread-test",1024*1024*30).start();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

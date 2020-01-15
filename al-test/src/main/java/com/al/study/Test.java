package com.al.study;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) {
        Apple apple=new Apple();
        ExecutorService threadPool = new ThreadPoolExecutor(2, 2, 30, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(10));
        threadPool.submit(new Monkey(2,apple));
        threadPool.submit(new Monkey(3,apple));
    }


    public static class Apple {
        public int count = 9;

    }

    public static class Monkey implements Runnable {
        private final int num;
        private final Apple apple;

        public Monkey(int num, Apple apple) {
            this.num = num;
            this.apple = apple;
        }

        public void run() {
            while (true) {
                synchronized (apple) {
                    if (apple.count - num < 0) {
                        break;
                    }
                    apple.count = apple.count - num;
                    System.out.println("剩余："+apple.count);
                }
            }

        }
    }


}

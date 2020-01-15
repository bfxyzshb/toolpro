package com.queue.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Queue {
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static Condition condition = reentrantLock.newCondition();

    public static void main(String[] args) {
        reentrantLock.lock();
        try {
            new ThreadA().start();
            while (true) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("test");
            }
        } finally {
            reentrantLock.unlock();
        }



    }


    static class ThreadA extends Thread{
        @Override
        public void run(){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reentrantLock.lock();
            try {
                condition.signal();
            } finally {
                reentrantLock.unlock();
            }
        }

    }

}

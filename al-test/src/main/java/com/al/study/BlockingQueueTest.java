package com.al.study;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author shangjing
 * @date 2018/11/22 4:05 PM
 * @describe
 */
public class BlockingQueueTest {

    private static int count = 0;

    private final BlockingQueue blockingQueue = new LinkedBlockingQueue(10);

    class Producer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    blockingQueue.put(1);
                    count++;
                    System.out.println(Thread.currentThread().getName() + "-生产者生产，数量为:" + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    blockingQueue.take();
                    count--;
                    System.out.println(Thread.currentThread().getName() + "-消费者消费，数量为："+ count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        BlockingQueueTest blockingQueueTest = new BlockingQueueTest();
        new Thread(blockingQueueTest.new Producer()).start();
        new Thread(blockingQueueTest.new Consumer()).start();
        new Thread(blockingQueueTest.new Producer()).start();
        new Thread(blockingQueueTest.new Consumer()).start();
        new Thread(blockingQueueTest.new Producer()).start();
        new Thread(blockingQueueTest.new Consumer()).start();

    }
}
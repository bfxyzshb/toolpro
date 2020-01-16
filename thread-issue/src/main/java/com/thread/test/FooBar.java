package com.thread.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * 顺序打印foobar n次
 */
class FooBar {
    private int n;
    private CountDownLatch countDownLatch;
    private CyclicBarrier barrier;// 使用CyclicBarrier保证任务按组执行

    public FooBar(int n) {
        this.n = n;
        countDownLatch = new CountDownLatch(1);
        barrier = new CyclicBarrier(2);// 保证每组内有两个任务
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        try {
            for (int i = 0; i < n; i++) {
                printFoo.run();
                countDownLatch.countDown();// printFoo方法完成调用countDown
                barrier.await();// 等待printBar方法执行完成
            }
        } catch (Exception e) {
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        try {
            for (int i = 0; i < n; i++) {
                countDownLatch.await();// 等待printFoo方法先执行
                printBar.run();
                countDownLatch = new CountDownLatch(1); // 保证下一次依旧等待printFoo方法先执行
                barrier.await();// 等待printFoo方法执行完成
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        FooBar fooBar=new FooBar(5);
        try {
            Thread thread1=new Thread(()->{
                try {
                    fooBar.foo(()->{
                        System.out.print("foo");
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Thread thread2=new Thread(()->{
                try {
                    fooBar.bar(()->{
                        System.out.println("bar");
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread1.start();
            thread2.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

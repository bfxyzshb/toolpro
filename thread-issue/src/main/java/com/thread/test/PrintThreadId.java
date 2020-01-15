package com.thread.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class PrintThreadId implements Runnable {
    private static final int count = 10;
    private ReentrantLock lock;
    private Condition condition1;
    private Condition condition2;
    private String threadId;
    private int state;
	AtomicInteger flag;


    public PrintThreadId(ReentrantLock lock, Condition condition1, Condition condition2, String threadId, int state,AtomicInteger flag) {
        this.lock = lock;
        this.condition1 = condition1;
        this.condition2 = condition2;
        this.threadId = threadId;
        this.state = state;
        this.flag=flag;
    }

    @Override
    public void run() {
		while (true) {
			if (flag.get() == state) {
				flag.incrementAndGet();
				break;
			}
		}
        lock.lock();
        try {
            for (int i = 0; i < count; i++) {
                System.out.println(threadId);
                condition2.signal();
                //最后一轮不阻塞，正常执行线程后，线程就DEAD不用管了
                if (i < count - 1) {
                    condition1.await();
                }
            }

        } catch (Exception e) {

        } finally {
            //System.out.println("threadId:" + threadId);
            lock.unlock();
        }
    }

    public static void main(String[] arg) throws Exception {
        ReentrantLock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();
        //不要依赖线程启动顺序，还是打印 A B C
		AtomicInteger flag = new AtomicInteger(0);
        Thread thread1 = new Thread(new PrintThreadId(lock, condition1, condition2, "A", 0,flag));
        Thread thread2 = new Thread(new PrintThreadId(lock, condition2, condition3, "B", 1,flag));
        Thread thread3 = new Thread(new PrintThreadId(lock, condition3, condition1, "C", 2,flag));
        thread2.start();
        thread3.start();
        thread1.start();


    }
}
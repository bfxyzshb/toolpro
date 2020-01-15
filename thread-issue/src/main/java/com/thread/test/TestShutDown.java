package com.thread.test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * shutdown：
 *
 * 问：shutdown()有什么功能？
 *
 * 答：阻止新来的任务提交，对已经提交了的任务不会产生任何影响。当已经提交的任务执行完后，它会将那些闲置的线程（idleWorks）进行中断，这个过程是异步的。
 *
 * 问：如何阻止新来的任务提交？
 *
 * 答：通过将线程池的状态改成SHUTDOWN，当再将执行execute提交任务时，如果测试到状态不为RUNNING，则抛出rejectedExecution，从而达到阻止新任务提交的目的。
 *
 * 问：为何对提交的任务不产生任何影响？
 *
 * 答：在调用中断任务的方法时，它会检测workers中的任务，如果worker对应的任务没有中断，并且是空闲线程，它才会去中断。另外的话，workQueue中的值，还是按照一定的逻辑顺序不断的往works中进行输送的，这样一来，就可以保证提交的任务按照线程本身的逻辑执行，不受到影响。
 *
 * shutdownNow：
 *
 * 问：shutdownNow()有什么功能？
 *
 * 答：阻止新来的任务提交，同时会中断当前正在运行的线程，即workers中的线程。另外它还将workQueue中的任务给移除，并将这些任务添加到列表中进行返回。
 *
 * 问：如何阻止新来的任务提交？
 *
 * 答：通过将线程池的状态改成STOP，当再将执行execute提交任务时，如果测试到状态不为RUNNING，则抛出rejectedExecution，从而达到阻止新任务提交的目的.
 *
 * 问：如果我提交的任务代码块中，正在等待某个资源，而这个资源没到，但此时执行shutdownNow()，会出现什么情况？
 *
 * 答：当执行shutdownNow()方法时，如遇已经激活的任务，并且处于阻塞状态时，shutdownNow()会执行1次中断阻塞的操作，此时对应的线程报InterruptedException，如果后续还要等待某个资源，则按正常逻辑等待某个资源的到达。例如，一个线程正在sleep状态中，此时执行shutdownNow()，它向该线程发起interrupt()请求，而sleep()方法遇到有interrupt()请求时，会抛出InterruptedException()，并继续往下执行。在这里要提醒注意的是，在激活的任务中，如果有多个sleep(),该方法只会中断第一个sleep()，而后面的仍然按照正常的执行逻辑进行。
 *
 *  
 *
 * 所以，当线程调用了sleep后，执行shutdownNow()只是打断了该线程的sleep，该线程会退出sleep，并抛出异常；
 *
 * 如果catch中有对异常处理，则继续处理异常并继续往下执行。
 *
 * 但如果在sleep的时候，调用了shutdown()，对本次的sleep没有影响，只是任务队列被清空了，执行完该任务之后，就会退出
 */
public class TestShutDown {
    public static void main(String[] args) {
        try {
            testShutDown(100);
            testShutDowNow(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void testShutDown(int startNo) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            executorService.execute(getTask(i + startNo));
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("shutDown->all thread shutdown");
    }

    public static void testShutDowNow(int startNo) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            executorService.execute(getTask(i + startNo));
        }
        executorService.shutdownNow();
        executorService.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("shutdownNow->all thread shutdown");
    }

    public static Runnable getTask(int threadNo) {
        final Random rand = new Random();
        final int no = threadNo;
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(no + "-->" + rand.nextInt(10));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("thread " + no + " has error" + e);
                }
            }
        };
        return task;
    }
}
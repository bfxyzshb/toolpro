package com.Thread;

/**
 * https://www.cnblogs.com/xll1025/p/6486170.html
 * happen-before
 * 一个简单的展示Happen-Before的例子.
 * 这里有两个共享变量:a和flag,初始值分别为0和false.在ThreadA中先给a=1,然后flag=true.
 * 如果按照有序的话,那么在ThreadB中如果if(flag)成功的话,则应该a=1,而a=a*1之后a仍然为1,下方的if(a==0)应该永远不会为真,永远不会打印.
 * 但实际情况是:在试验100次的情况下会出现0次或几次的打印结果,而试验1000次结果更明显,有十几次打印.
 * <p>
 * 以上结论表明 指令发生了重排
 * <p>
 * 防止指令重排 用volatile修饰变量
 */

public class SimpleHappenBefore {

    /**
     * 这是一个验证结果的变量
     */
    private static int a = 100000;
    /**
     * 这是一个标志位
     */
    private static boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        //由于多线程情况下未必会试出重排序的结论,所以多试一些次
        for (int i = 0; i < 10000; i++) {
            ThreadA threadA = new ThreadA();
            ThreadB threadB = new ThreadB();
            threadB.start();
            threadA.start();
        }
    }

    static class ThreadA extends Thread {
        /**
         * 指令重排后flag=true先执行；
         * 为什么？
         * a=1
         * 假如不是a=1的操作，而是a=new byte[1024*1024](分配1M空间)，
         * 那么它会运行地很慢，此时CPU是等待其执行结束呢，还是先执行下面那句flag=true呢？
         * 显然，先执行flag=true可以提前使用CPU，加快整体效率
         */
        public void run() {
            flag = true;
            a = 1;

        }
    }

    static class ThreadB extends Thread {
        public void run() {
            if (flag) {
                System.out.println("a=" + a);
            }

        }
    }
}
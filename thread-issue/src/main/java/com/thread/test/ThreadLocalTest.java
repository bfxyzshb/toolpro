package com.thread.test;

public class ThreadLocalTest {
    public static void main(String[] args) {
        ThreadLocal threadLocal=new ThreadLocal();
        threadLocal.set(1);
        threadLocal.set("A");
        System.out.println(threadLocal.get());
    }


}

package com.test;

import org.junit.Test;

public class Test1 {
    @Test
    public void run(){
        System.out.println("runrunrunrunrunrunrunrunrunrunrunrunrun");
    }

    public static void main(String[] args) {
        int a=(int) ((Math.random() * 9 + 1) * 100000);
        System.out.println(String.valueOf(a));
        int vcode = (int)((Math.random() * 9 + 1) * 100000);
        System.out.println("验证码："+vcode);
    }
}
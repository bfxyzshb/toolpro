package com.weibo.test;

public class EnumTest {

    public static void main(String[] args) {
        System.out.println(MsgType.like.name());
    }



    enum MsgType{
        like,comment;
    }

}

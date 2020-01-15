package com.weibo.test;

import com.google.common.base.Joiner;
import  org.apache.commons.collections.CollectionUtils;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Array;
import java.util.*;

public class EndUids {

    public static void main(String[] args) {
        String str="6568073865";
        char[] uid=str.toCharArray();
        System.out.println(uid[str.length()-2]);
        System.out.println(uid[str.length()-3]);
        Map map=new HashMap();
        System.out.println(CollectionUtils.isEmpty(new ArrayList()));


    }
}

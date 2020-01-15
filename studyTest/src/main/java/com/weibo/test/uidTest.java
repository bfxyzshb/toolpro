package com.weibo.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class uidTest {
    public static void main(String[] args) {
        String uids="6568073865;";
        String[] uidArr=uids.split(";");
        Set<Long> set =new HashSet<>();
        for(int i=0;i<uidArr.length;i++){
            set.add(Long.parseLong(uidArr[i]));
        }
        long uid=6568073865L;
        System.out.println(set.contains(uid));
    }
}

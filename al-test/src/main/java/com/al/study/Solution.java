package com.al.study;

import java.util.HashMap;
import java.util.Map;

/**
 * 写段代码，定义一个字符串常量，字符串中只有大小写字母和整数，输出字符串中的出现最多的数字的和
 */
public class Solution {

    public static void main(String[] args) {
        String str = "9fil3dj11P0jAsf11j";
        System.out.println(findMost(str));
    }

    private static Integer findMost(String str) {
        if (str == null || str.length() == 0) {
            return -1;
        }
        String temp = str.replaceAll("[a-zA-Z]+", "@");
        System.out.println(temp);
        String[] result = temp.split("@");
        Map<String,Integer> map=new HashMap();
        int max=0;
        String maxStr="";
        for (String s : result) {
            Integer num=map.get(s)==null?0:map.get(s);
            num++;
            map.put(s,num);
            if(max<=num){
                max=num;
                maxStr=s;
            }
        }
        System.out.println(maxStr);
        return Integer.valueOf(maxStr)*max;
    }

}

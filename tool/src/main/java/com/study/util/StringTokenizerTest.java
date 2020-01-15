package com.study.util;

import java.util.StringTokenizer;

public class StringTokenizerTest {
    public static void main(String[] args) {
        StringTokenizer st = new StringTokenizer("www.baidu.com", ".");
        while(st.hasMoreElements()){
            System.out.println("Token:" + st.nextToken());
        }
    }

}

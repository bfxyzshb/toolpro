package com;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {
    public static void main(String[] args) {
        ThreadLocal threadLocal=new ThreadLocal();
        threadLocal.set(123);
        Pattern pattern = Pattern.compile("(insert\\s+)(into\\s+)([A-Za-z_]*)");
        Matcher m1 = pattern.matcher("insert into file_platform_36.filemeta_1912");
        //输出结果
        while(m1.find()) {
            String result = m1.group();
            System.out.println(m1.group(3));
        }

        // 按指定模式在字符串查找
        String line = "This order was placed for QT3000! OK?";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile("(\\D*)(\\d+)(.*)");
        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if (m.find( )) {
            System.out.println("Found value: " + m.group(0) );
            System.out.println("Found value: " + m.group(1) );
            System.out.println("Found value: " + m.group(2) );
            System.out.println("Found value: " + m.group(3) );
        } else {
            System.out.println("NO MATCH");
        }

    }
}

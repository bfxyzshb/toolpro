package com.j8.stream;

import java.util.Arrays;
import java.util.stream.Collectors;

public class parallel {

    public static void main(String[] args) {

       /* Arrays.asList(1, 2, 3, 4, 5, 6, 7, 9, 8, 0, 1)
                .stream()
                .parallel()
                .collect(Collectors.groupingBy(x -> x % 10))
                .forEach((x, y) -> System.out.println(x + ":" + y));*/
       String uri="abc/bc/123.jog";
        String fid = uri.substring(uri.lastIndexOf("/") , uri.lastIndexOf("."));
        System.out.println(fid);
    }
}

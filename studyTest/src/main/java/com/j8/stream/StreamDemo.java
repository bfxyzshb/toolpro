package com.j8.stream;


/*import com.sun.tools.javac.util.List;

import java.util.OptionalInt;

public class StreamDemo {
    public static void main(String[] args) {
        List<String> strings = List.of("Apple", "bug", "ABC", "Dog");
        OptionalInt max = strings.stream()
                //无状态中间操作
                .filter(s -> s.startsWith("A"))
                //无状态中间操作
                .mapToInt(String::length)
                //有状态中间操作
                .sorted()
                //非短路终端操作
                .max();
        System.out.println(max.getAsInt());
        strings.parallelStream();
    }
}*/



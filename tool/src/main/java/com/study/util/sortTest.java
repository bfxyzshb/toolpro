package com.study.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class sortTest {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(3,1,5,2,9,8,4,10,6,7,12345567,234566789);
        nums.sort(Comparator.reverseOrder()); //reverseOrder倒序
        System.out.println("倒序:"+nums);//倒序:[10, 9, 8, 7, 6, 5, 4, 3, 2, 1]

        nums.sort(Comparator.naturalOrder());//naturalOrder自然排序即：正序
        System.err.println("正序:"+nums);//正序:[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

        long[] uids={1,2};
        Arrays.asList(uids).forEach(u->{
            System.out.println(u.length);
        });
        List<Long> list1=Arrays.stream(uids).boxed().collect(Collectors.toList());
        System.out.println(list1);
    }

}

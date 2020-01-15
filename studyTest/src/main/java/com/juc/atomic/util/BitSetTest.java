package com.juc.atomic.util;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;


/**
 *
 */
public class BitSetTest {
    public static void main(String[] args) {
        List<Integer> list=new ArrayList(){{add(100);add(2);add(2);}};

        BitSet bitSet = new BitSet(Integer.MAX_VALUE);
        for(int index = 0;index < list.size(); index++){
            int value = list.get(index);
            boolean exists = bitSet.get(value);
            if(!exists){
                bitSet.set(value);
            }else{
                System.out.println("重复的整数：" + value);
            }
        }
        bitSet.stream().forEach(i->{
            System.out.println(i);
        });
        System.out.println(1<<6);
    }
}

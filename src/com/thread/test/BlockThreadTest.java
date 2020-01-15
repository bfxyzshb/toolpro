package com.thread.test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;

public class BlockThreadTest {

    private static ConcurrentHashMap holder = new ConcurrentHashMap();

    public static <K,V> Object getHolerObject(K key, Function<? super K, ? extends V> mappingFunction) {
        Object value = holder.computeIfAbsent(key, mappingFunction);
        return value == null ? null : value;
    }

    public static void main(String[] args) {
        while(true){
            BlockThreadTest.getHolerObject("test",r->{
                System.out.println("runing");
                return "a";});
            System.out.println(BlockThreadTest.holder.get("test"));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }



}

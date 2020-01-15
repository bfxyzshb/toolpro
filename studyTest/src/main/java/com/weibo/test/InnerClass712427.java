package com.weibo.test;

import com.sun.btrace.annotations.*;
import com.sun.btrace.BTraceUtils.Strings; 
import com.sun.btrace.BTraceUtils.Time; 
import com.sun.btrace.BTraceUtils.Threads; 
import com.sun.btrace.BTraceUtils; 
import com.sun.btrace.aggregation.*; 
@BTrace 
public class InnerClass712427{ 
@OnMethod(clazz = "com.weibo.test.NumberUtil", method = "sum") 
public static void m104441(){
BTraceUtils.println("-------------------------------stack info--------------------------");
 BTraceUtils.jstack();
 BTraceUtils.println("-------------------------------stack End--------------------------");} 
}
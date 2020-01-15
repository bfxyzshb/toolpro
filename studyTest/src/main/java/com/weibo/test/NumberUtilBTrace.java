package com.weibo.test;

import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.Strings.strcat;

import static com.sun.btrace.BTraceUtils.jstack;

import static com.sun.btrace.BTraceUtils.println;

import static com.sun.btrace.BTraceUtils.str;

 

/**

 * NumberUtilBTrace

 *

 * @author fengzheng

 * @date 2017/6/20

 */

@BTrace

public class NumberUtilBTrace {

 

    @OnMethod(

            clazz="com.weibo.test.NumberUtil",

            method="sum",

            location=@Location(Kind.RETURN)

    )

    public static void func(@Return int result) {

        println("trace: =======================");

        println(strcat("result:", str(result)));

        jstack();

    }

}
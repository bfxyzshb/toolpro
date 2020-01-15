package com.weibo.test;

import java.lang.reflect.Field;

/**

 * NumberUtil

 *

 * @author fengzheng

 * @date 2017/2/15

 */

public class NumberUtil {

 

    public int sum(int a){

        int result = 0;
        try {

            Thread.sleep(20);

        }catch (InterruptedException e){
        }

        for(int i = 0; i< 100; i++){

            result += i * i;

        }
        add();
        Field field=null;
        //field.set();

        return result;

    }

    private void add(){

    }


 

    public static void main(String[] args){

        while (true) {

            Thread.currentThread().setName("计算");

            NumberUtil util = new NumberUtil();

            int result = util.sum(1);

            System.out.println(result);

            try {

                Thread.sleep(50);

            }catch (InterruptedException e){

 

            }

        }

    }

}
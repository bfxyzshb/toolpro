package com.wrapper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class wrapperTest {
    private static SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
    private final static long dayTime = 24 * 3600 * 1000;

    private static volatile String GRAYENDUID = "{\"begin\":\"3\",\"end\":\"2\",\"nums\":\"00\"}";

    public static void main(String[] args) {
        //sdf.format(null);
        /*String str="test"+"\t"+"123";
        System.out.println(str);*/
        long uid=6568070L;
        long suffix=uid%10;
        System.out.println(suffix);
        System.out.println(GRAYENDUID);

        String uidStr=String.valueOf(12345678);
        System.out.println(uidStr.substring(uidStr.length()-2,uidStr.length()));
        /*ComputerInterface computerInterface=new Computrer();
        Keyboard keyboard=new Keyboard(computerInterface);
        //keyboard.echo();
        Mouse mouse=new Mouse(keyboard);
        mouse.echo();
        System.out.println(getStartTime(3));*/


    }



    private static String getStartTime(int delay) {
        Date date = new Date(System.currentTimeMillis() - delay * dayTime);
        throw new RuntimeException("123");
        //return sdf.format(date);
    }
}

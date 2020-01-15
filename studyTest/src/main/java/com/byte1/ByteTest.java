package com.byte1;

public class ByteTest {
    public static void main1(String[] args) {
        System.out.println(10);
        int arr[] = {10, 3, 9, 5, 10, 3, 5};
        findNumAppearOnce(arr);
    }

    public static void findNumAppearOnce(int[] arr) {
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            result ^= arr[i];
            System.out.println(result);
        }
        System.out.println("result:" + result);
    }


    public static void main(String[] args){

    }

}

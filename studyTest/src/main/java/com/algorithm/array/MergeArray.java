package com.algorithm.array;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MergeArray {
    public static void main(String[] args) {
        int[] a = {1, 3, 5, 6, 10, 34, 35, 80, 99};
        int[] b = {2, 12, 15, 20, 66};
        int[] c = mergeArray(a, b);
        List newC=Stream.of(c).collect(Collectors.toList());
        System.out.println(newC.toString());
        int i=0;
        int j=0;
        System.out.println(i++);
        System.out.println(++j);
        System.out.println(i++);
        System.out.println(i++);
    }

    private static int[] mergeArray(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) {
                c[k++] = a[i++];
            } else {
                c[k++] = b[j++];
            }
        }
        while (i < a.length) {
            c[k++] = a[i++];
        }
        while (j < b.length) {
            c[k++] = b[i++];
        }
        return c;
    }
}

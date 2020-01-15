package com.juc.atomic.util;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class HugeBitset {
    //分段式bitset存储在list中
    private List<BitSet> bitsetList = new ArrayList(){};
//    下标最大值
    private long max;
//    每段值大小
    private int seg;
//  根据下标最大值，段大小初始化bitset
    HugeBitset(long max,int seg){
        this.max = max;
        this.seg = seg;

        int segs = (int)(max/seg) +1;

        for(int i = 0;i<segs;i++){
            BitSet temp = new BitSet(seg);
            bitsetList.add(temp);
        }
        System.out.println("HugeBitset#seg counts is:"+bitsetList.size()+", max is :"+max+", seg is :"+seg);
    }
//    获取段 bitset
    public BitSet getSegBitset(long index){
        int segNo = (int)(index/seg);
        System.out.println("getSegBitset#segNo is:"+segNo+",index is :"+index);
        return bitsetList.get(segNo);
    }
//    获取段 bitset的偏移量
    public int getBsOffset(long index){
        int offset = (int)(index%seg);
        System.out.println("getBsOffset#offset is:"+offset+",index is :"+index);
        return offset;
    }

    public static void main(String[] args){
        long testmax = 6748347838l;
        int testseg=500000000;
        long testindex1 = 38475643l;
        long testindex2 = 838888843l;
        long testindex3 = 4838888843l;
        long testindex4 = 6738888843l;

        HugeBitset testhb = new HugeBitset(testmax,testseg);
        testhb.getSegBitset(testindex1).set(testhb.getBsOffset(testindex1));
        testhb.getSegBitset(testindex2).set(testhb.getBsOffset(testindex2));
        testhb.getSegBitset(testindex3).set(testhb.getBsOffset(testindex3));
        testhb.getSegBitset(testindex4).set(testhb.getBsOffset(testindex4));

        System.out.println("GetResults:testindex1:"+testhb.getSegBitset(testindex1).get(testhb.getBsOffset(testindex1))
        +" , testindex2:"+testhb.getSegBitset(testindex2).get(testhb.getBsOffset(testindex2))
                        +" , testindex3:"+testhb.getSegBitset(testindex3).get(testhb.getBsOffset(testindex3))
                        +" , testindex4:"+testhb.getSegBitset(testindex4).get(testhb.getBsOffset(testindex4))
 );

//    System.out.println((int)(testmax%testseg));

//        System.out.println((int)5l/2);

    }
}
package com.algorithm.limit;

public class LeakyLimiter {

    //桶的容量
    private int capacity;

    //漏水速度
    private int ratePerMillSecond;

    //水量
    private double water;

    //上次漏水时间
    private long lastLeakTime;

    public LeakyLimiter(int capacity, int ratePerMillSecond) {

        this.capacity = capacity;
        this.ratePerMillSecond = ratePerMillSecond;
        this.water = 0;
    }


    //获取限流
    public boolean tryAcquire() {

        //执行漏水，更新剩余水量
        refresh();

        //尝试加水，水满则拒绝
        if (water + 1 > capacity) {
            return false;
        }

        water = water + 1;
        return true;

    }

    private void refresh() {
        //当前时间
        long currentTime = System.currentTimeMillis();

        if (currentTime > lastLeakTime) {

            //距上次漏水的时间间隔
            long millisSinceLastLeak = currentTime - lastLeakTime;
            long leaks = millisSinceLastLeak * ratePerMillSecond;

            //允许漏水
            if (leaks > 0) {
                //已经漏光
                if (water <= leaks) {
                    water = 0;
                } else {
                    water = water - leaks;
                }
                this.lastLeakTime = currentTime;
            }
        }
    }
}

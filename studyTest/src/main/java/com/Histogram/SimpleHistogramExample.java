package com.Histogram; /**
 * HistogramPerfTest.java
 * Written by Gil Tene of Azul Systems, and released to the public domain,
 * as explained at http://creativecommons.org/publicdomain/zero/1.0/
 *
 * @author Gil Tene
 */

import org.HdrHistogram.*;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 直方图统计
 * A simple example of using HdrHistogram: run for 20 seconds collecting the
 * time it takes to perform a simple Datagram Socket create/close operation,
 * and report a histogram of the times at the end.
 * doc:http://javadox.com/org.hdrhistogram/HdrHistogram/1.2.1/org/HdrHistogram/Histogram.html
 */

public class SimpleHistogramExample {
    // A Histogram covering the range from 1 nsec to 1 hour with 3 decimal point resolution:
    /**
     * highestTrackableValue-直方图要跟踪的最大值。必须是大于等于2的正整数。
     * numberOfSignificantValueDigits-直方图将保持值解析和分隔的有效小数位数。必须是0到5之间的非负整数
     */
    static Histogram histogram = new Histogram(3600000000000L, 3);

    static public volatile DatagramSocket socket;

    static long WARMUP_TIME_MSEC = 5000;
    static long RUN_TIME_MSEC = 10000;
    static AtomicLong count = new AtomicLong(0);

    static void recordTimeToCreateAndCloseDatagramSocket() {
        long startTime = System.nanoTime();
        try {
            socket = new DatagramSocket();
            /*int sleep = new Random().nextInt(10);
            Thread.sleep(sleep);*/
        } catch (Exception ex) {
        } finally {
            socket.close();
        }
        long endTime = System.nanoTime();
        System.out.println(endTime - startTime);
        histogram.recordValue(endTime - startTime);
    }

    public static void main(final String[] args) {
        long startTime = System.currentTimeMillis();
        long now;
        //预热
       /* do {
            recordTimeToCreateAndCloseDatagramSocket();
            now = System.currentTimeMillis();
        } while (now - startTime < WARMUP_TIME_MSEC);

        histogram.reset();*/

        do {
            recordTimeToCreateAndCloseDatagramSocket();
            now = System.currentTimeMillis();
        } while (now - startTime < RUN_TIME_MSEC);

        System.out.println("Recorded latencies [in usec] for Create+Close of a DatagramSocket:");

        histogram.outputPercentileDistribution(System.out, 1000.0);
        System.out.println(histogram.getTotalCount());
        System.out.println("50% " + histogram.getValueAtPercentile(20));
        System.out.println("50% " + histogram.getValueAtPercentile(50));
        System.out.println("90% " + histogram.getValueAtPercentile(90));
        System.out.println("99% " + histogram.getValueAtPercentile(99));
        System.out.println("99% " + histogram.getValueAtPercentile(99.9));
        System.out.println("99% " + histogram.getValueAtPercentile(99.99));
        System.out.println("99% " + histogram.getValueAtPercentile(99.999));
    }
}

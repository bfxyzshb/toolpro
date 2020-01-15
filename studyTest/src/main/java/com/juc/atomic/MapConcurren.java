package com.juc.atomic;

import com.juc.atomic.util.LongAdderV8;

import java.util.concurrent.ConcurrentHashMap;

public class MapConcurren {
    final ConcurrentHashMap<MetricKey, LongAdderV8> metrics = new ConcurrentHashMap<>();

    public static void main(String[] args) {

    }
    private void increment(MetricKey key, int quantity) {
        if (quantity == 0) return;

        LongAdderV8 metric = metrics.get(key);
        if (metric == null) {
            metric = new LongAdderV8();
            metric.add(quantity);
            //并发放
            metric = metrics.putIfAbsent(key, metric);
            if (metric == null) return;
        }
        metric.add(quantity);
    }
    enum MetricKey {
        messages,
        messagesDropped
    }
}

package com.weibo.test;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
 
import java.util.concurrent.TimeUnit;

/**
 * Throughput 统计qps
 * AverageTime 统计平均耗时
 */
 
@BenchmarkMode({Mode.Throughput,Mode.AverageTime})
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class JMHFirstBenchmark {
    @Benchmark//对要被测试性能的代码添加注解，说明该方法是要被测试性能的
    public int sleepAWhile() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            // ignore
        }
        return 0;
    }
 
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHFirstBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(3)
                .measurementIterations(3)//执行几次
                .build();
 
        new Runner(opt).run();
    }
 
}
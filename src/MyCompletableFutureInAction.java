import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * Created by mengxiaopeng on 2018/3/9. 
 * com.company.LambdaExpressions.Futures.CompletableFuture 
 * 1.supplyAsync（执行Callable）  thenApply|thenApplyAsync   whenComplete|whenCompleteAsync  组合用法 
 * 2.对步骤1中的链式用法 每一步是被哪个线程池中哪个线程所执行 
 * 3.runAsync(执行Runnable)  whenComplete 组合用法 
 * 4.关于类型推导比较重要的  类名::method （本类调用 只要是静态方法即可）(其他类调用 静态+public) 
 *   调用类中的那些非静态方法  写法  实例::method  (本类 调用（不能使用方法推导）需要实例+实例.method) (其他类调用 实例+public) 
 * 5.多核编程中parallelStream 正确的使用方法 
 */  
public class MyCompletableFutureInAction {
    private static Random random=new Random();
  
    public static void main(String[] args) {  
        //##方式一 对线程池里面的线程进行初始化设置  
        ExecutorService executorService = Executors.newFixedThreadPool(2, runnable -> {
            Thread t = new Thread(runnable);  
            t.setDaemon(false);  
            return t;  
        });  
  
        //默认不是守护线程 默认false setDaemon(false);  
        ExecutorService executorPool = Executors.newFixedThreadPool(100);  
  
  
        //### 上面三步都是 [pool-1-thread-1]执行 即三个步骤是同一个线程执行
        CompletableFuture.supplyAsync(MyCompletableFutureInAction::generateRandomDouble,executorPool)
                .thenApplyAsync(t->{
                    System.out.println("thenApply up get value:"+t);
                    System.out.println("##1.0## [thenApplyThreadName]=["+Thread.currentThread().getName()+"]");
                     return multiPly(t);  
                })
                //获取上游的值和异常（汇总）
                .whenCompleteAsync((t,throwable)->{
                    System.out.println("whenComplete up get throwable value:"+throwable);
                    System.out.println("whenComplete up get t value:"+t);
                    System.out.println("##1.0## [whenCompleteThreadName]=["+Thread.currentThread().getName()+"]");

                    System.out.println(t);
                    throwable.printStackTrace();

                });
  
        /*//###2.0####CompletableFuture.supplyAsync(executorPool)  thenApplyAsync  whenCompleteAsync
        CompletableFuture.supplyAsync(MyCompletableFutureInAction::generateRandomDouble,executorPool)
                .thenApplyAsync(t->{  
                    System.out.println("##2.0## [thenApplyThreadName]=["+Thread.currentThread().getName()+"]");  
                    return multiPly(t);  
                }).whenCompleteAsync((v,throwable)->{
                    System.out.println("##2.0## [whenCompleteThreadName]=["+Thread.currentThread().getName()+"]");  
                    System.out.println(v);
                    throwable.printStackTrace();});
        //### 上面三步supplyAsync=[pool-1-thread-2]   后面两个都是[ForkJoinPool.commonPool-worker-1]  
        //### 原因由于调用这个时thenApplyAsync  没有指定Executor executor 然后又是因为异步  默认采用ForkJoin的连接池  
  
        //###3.0####CompletableFuture.supplyAsync(executorPool)  thenApplyAsync  whenCompleteAsync(executorpool)
        CompletableFuture.supplyAsync(MyCompletableFutureInAction::generateRandomDouble,executorPool)
                .thenApply(t->{  
                    System.out.println("##3.0## [thenApplyThreadName]=["+Thread.currentThread().getName()+"]");  
                    return multiPly(t);  
                }).whenCompleteAsync((v,t)->{  
            System.out.println("##3.0## [whenCompleteThreadName]=["+Thread.currentThread().getName()+"]");  
            System.out.println(v);  
            t.printStackTrace();},executorPool);  
        //### 上面三步supplyAsync和thenApply 都是[pool-1-thread-3]   whenCompleteAsync[pool-1-thread-4]  
        //### 原因由于调用这个时thenApply和前者同一线程  whenCompleteAsync指定了线程  
  
  
        //###4.0 测试runAsync 执行的是Runnable  
        CompletableFuture.runAsync(
                MyCompletableFutureInAction::printRandomDouble)
                .whenComplete(  
                        (t,v)->{  
                            System.out.println("xxxxxxxxxxxxxxxx"+t);  
                            v.printStackTrace();  
                        }  
                );  
  
        //###5.0 测试runAsync 执行的是Runnable 方法推导 调用类的非静态私有方法  
        //### 非静态私有方法 （在自己本类中也只能采用lamdba表达式 实例.method()）在其他类无法采用方法推导 也不可以采用lamdba表达式实例.method()  
        MyCompletableFutureInAction myCompletableFuture = new MyCompletableFutureInAction();
        CompletableFuture.runAsync(()->{  
            //myCompletableFuture.testMethodRefence();  
        });  
  
  
        //####6.0 组合使用  
  
        List<Integer> productionIDs = Arrays.asList(1, 2, 3, 4, 5);
        Instant start=Instant.now();
        //方式一  
        List<Double> result = productionIDs.stream()
                .map(i -> CompletableFuture.supplyAsync(MyCompletableFuture::generateRandomDouble, executorPool)) 
                .map(f -> f.thenApply(MyCompletableFutureInAction2::multiPly)) 
                .map(CompletableFuture::join).collect(toList()); 

        //方式二 这种写法速度比上面快  
        List<Double> result = productionIDs.parallelStream()
                .map(i -> CompletableFuture.supplyAsync(MyCompletableFuture::generateRandomDouble, executorPool)) 
                .map(f -> f.thenApply(MyCompletableFutureInAction2::multiPly)) 
                .map(CompletableFuture::join).collect(toList());
  
        //方式三 最快的一种发送 比第二种性能又好 原因是先收集Future 进行CompletableFuture::join 可以并行计算  
        //而方式一 和方式二 多层Map连着写 需要对每一个流分片执行完Map 所以性能不是最好的  
        List<CompletableFuture<Double>> collect = productionIDs.parallelStream()
                .map(i -> CompletableFuture.supplyAsync(MyCompletableFutureInAction::generateRandomDouble, executorPool))
                .collect(toList());  
  
        List<Double> result = collect.stream().map(CompletableFuture::join).collect(toList());  
        System.out.println(result+"=Way3 最快的方式=SpendTime=["+(Duration.between(start,Instant.now()))+"]");*/
  
    }  
  
  
    /** 
     * thenApply调用方法 
     * @param value 
     * @return 
     */  
    private static Double  multiPly(Double value) {
        try {
            System.out.println("multiPly sleep...");
            //TimeUnit.SECONDS.sleep(3L);
            System.out.println("multiPly sleep...end");
        } catch (Exception e) {
            e.printStackTrace();  
        }  
        return value*10;  
    }  
  
  
    public static Double generateRandomDouble(){  
        try {  
            System.out.println("[##3.0## Double_Thread_Name]=["+Thread.currentThread().getName()+"]");  
            Thread.sleep(1000);  
            double value = random.nextDouble();  
            System.out.println(value);  
            return value;  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * 这里类似于Runnable 
     */  
    public static void printRandomDouble(){  
        try {  
            Thread.sleep(1000);  
            //IntStream.rangeClosed(0,10).map(Random::nextInt)  
            //上面会出错原因是当采用方法推导时 需要采用类的实例来引用 不然会报 no-static method be use  
            IntStream.rangeClosed(5,10).map(random::nextInt)
                    .forEach(System.out::println);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }  
} 
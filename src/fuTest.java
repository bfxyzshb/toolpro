import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class fuTest {

    public static void main(String[] args) throws Exception{
        /*CompletableFuture cf = CompletableFuture.completedFuture("message");
        System.out.println(cf.isDone());
        //CompletableFuture执行完返回结果  否则返回hull
        System.out.println(cf.getNow(null));*/

       /*CompletableFuture cf=CompletableFuture.runAsync(()->{
            System.out.println("hello world");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(cf.join());
        Thread.sleep(2000);
        System.out.println(cf);*/
        /*ExecutorService es=Executors.newFixedThreadPool(4,
                new ThreadFactory() {
                    public Thread newThread(Runnable r) {
                        Thread t = Executors.defaultThreadFactory().newThread(r);
                        t.setDaemon(false);
                        return t;
                    }
                });
       CompletableFuture cf=CompletableFuture.completedFuture("shb").thenApplyAsync(str->{
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           return str.toUpperCase();
       });
        System.out.println(cf.get());*/
        //Thread.currentThread().join();

        /*StringBuilder result = new StringBuilder("test:");
        CompletableFuture.completedFuture("thenAccept message")
                .thenAccept(s -> result.append(s));
        System.out.println(result.toString());*/

        /*CompletableFuture cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s.toUpperCase();
        });
        System.out.println(cf.getNow(null));
        System.out.println(cf.join());*/

        /*String original = "Message";
        CompletableFuture cf = CompletableFuture.completedFuture(original).thenApply(s ->s.toUpperCase())
        .thenCompose(upper -> CompletableFuture.completedFuture(original).thenApplyAsync(s -> s.toLowerCase())
                        .thenApplyAsync(s -> upper + s));
        System.out.println(cf.join());*/

        /*CompletableFuture cf=CompletableFuture.completedFuture("shb").thenApply(str->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return str.toUpperCase().substring(0,1);
        });
        System.out.println(cf.getNow(null));
        List messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture> futures= (List<CompletableFuture>) messages.stream();*/
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 2, 60, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<>(10),
                new RejectedExecutionHandler(){

                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println("have block");
                    }
                });
        for(;;){
            CompletableFuture.supplyAsync(() -> {
                throw new RuntimeException("1");
            },pool).whenCompleteAsync((sucess, t) -> {
                if (t != null) {
                    System.out.println("error");
                }
            });
        }





    }
}

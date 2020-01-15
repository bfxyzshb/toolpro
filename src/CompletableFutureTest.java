import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutureTest {
    private static Random random=new Random();
    private static ExecutorService executorPool = Executors.newFixedThreadPool(100);

    public static void main(String[] args) {
        //调用callable
        CompletableFuture.supplyAsync(CompletableFutureTest::generateRandomDouble,executorPool).thenApply(value->{
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thenApplyAsync value="+value);
            return multiPly(value);
        }
        ).whenCompleteAsync((value,throwable)->{
            System.out.println("whenCompleteAsync value="+value);
        });
        System.out.println("end");
        /*try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }

    /**
     * thenApply调用方法
     * @param value
     * @return
     */
    private static Double  multiPly(Double value) {
        try {
            TimeUnit.SECONDS.sleep(3L);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return value*10;
    }


    public static Double generateRandomDouble(){
        try {
            //Thread.sleep(1000);
            double value = random.nextDouble();
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

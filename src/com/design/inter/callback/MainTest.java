package com.design.inter.callback;

public class MainTest {

    public void zadd(){
        this.exportFunction(new CallBack() {
            @Override
            public void call() {
                //正常业务
                System.out.println("zadd");
            }
        });
    }

    public void del(){



    }


    public void callTime(CallBack callBack){
        long startTime=System.currentTimeMillis();
        callBack.call();
        System.out.println("正常业务");
        long endTime=System.currentTimeMillis();
    }



    private void exportFunction(CallBack callBack){
        long startTime=System.currentTimeMillis();
        SafeSleep.sleep(100l);
        callBack.call();
        long endTime=System.currentTimeMillis();
        long costTime=endTime-startTime;
        System.out.println("耗时："+costTime);

    }


    interface CallBack{
        public void call();
    }


    static class SafeSleep{

        public static void sleep(Long time){
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

package interview.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @auther reliance
 * @create 2019-07-04
 * 第四种获得java多线程的方式，线程池
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {











    }

    private static void jdkThreadPool() {
        System.out.println(Runtime.getRuntime().availableProcessors());

        ExecutorService threadPool2 = Executors.newFixedThreadPool(5);
        ExecutorService threadPool1 = Executors.newSingleThreadExecutor();
        ExecutorService threadPool = Executors.newCachedThreadPool();


        try {
            for (int i = 0; i < 10; i++) {
                threadPool.submit(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 窗口办理业务");

                });
                try{
                    TimeUnit.MILLISECONDS.sleep(200);}catch (Exception e){e.printStackTrace();}
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            threadPool.shutdown();
        }
    }
}

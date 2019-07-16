package interview.thread;

import java.util.concurrent.*;

/**
 * @auther reliance
 * @create 2019-07-04
 * 第四种获得java多线程的方式，线程池
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {

        System.out.println(Runtime.getRuntime().availableProcessors());

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        double blockage_coefficient = 0.9;

        ExecutorService CPUThreadPool = new ThreadPoolExecutor(
                availableProcessors,
                availableProcessors,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        );

        ExecutorService IOThreadPool = new ThreadPoolExecutor(
                (int)(availableProcessors / (1 - blockage_coefficient)),
                (int)(availableProcessors / (1 - blockage_coefficient)),
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        );

        try {
            for (int i = 0; i < 10; i++) {
                CPUThreadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t CPU运算");
                    try{TimeUnit.MILLISECONDS.sleep(200);}catch (Exception e){e.printStackTrace();}
                });
            }

            for (int i = 0; i < 10; i++) {
                IOThreadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t ###IO运算");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            CPUThreadPool.shutdown();
            IOThreadPool.shutdown();
        }



    }

    private static void jdkThreadPool() {
        System.out.println(Runtime.getRuntime().availableProcessors());

        ExecutorService threadPool2 = Executors.newFixedThreadPool(5);
        ExecutorService threadPool1 = Executors.newSingleThreadExecutor();
        ExecutorService threadPool = Executors.newCachedThreadPool();


        try {
            for (int i = 0; i < 10; i++) {
                threadPool.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 窗口办理业务");

                });
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}

package interview.review;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ReviewSemaphore {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 9; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"\t抢到了车位");
                    try{
                        TimeUnit.SECONDS.sleep(3);}catch (Exception e){e.printStackTrace();}
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName()+"\t#######离开了车位");
                }
            },String.valueOf(i)).start();

        }
    }
}

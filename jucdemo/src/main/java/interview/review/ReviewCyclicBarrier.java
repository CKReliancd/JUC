package interview.review;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class ReviewCyclicBarrier {

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()-> {
            System.out.println("\t*******召唤神龙");
        });
        for (int i = 1; i <= 7; i++) {
            try{ TimeUnit.MILLISECONDS.sleep(500);}catch (Exception e){e.printStackTrace();}
            final int temp = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t找到第"+temp+"颗龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }

    }
}

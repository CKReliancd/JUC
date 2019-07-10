package interview.review;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ReviewCountDown {

    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t被灭");
                countDownLatch.countDown();
            },ReviewEnum.foreach_ReviewEnum(i).getRetMassage()).start();
            try{ TimeUnit.MILLISECONDS.sleep(300);}catch (Exception e){e.printStackTrace();}
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "\t**********秦帝国统一天下");



    }

}

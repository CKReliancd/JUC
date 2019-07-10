package interview.review;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ReviewBlockQueue {
    public static void main(String[] args) {
        BlockingQueue<Thread> blockingQueue = new ArrayBlockingQueue(3);

        try {
            System.out.println(blockingQueue.offer(Thread.currentThread(), 2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

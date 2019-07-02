package interview.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {

        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(arrayBlockingQueue.offer("a", 2, TimeUnit.SECONDS));
        System.out.println(arrayBlockingQueue.offer("a", 2, TimeUnit.SECONDS));
        System.out.println(arrayBlockingQueue.offer("a", 2, TimeUnit.SECONDS));
        System.out.println(arrayBlockingQueue.offer("a", 2, TimeUnit.SECONDS));

        System.out.println(arrayBlockingQueue.take());
        System.out.println(arrayBlockingQueue.take());
        System.out.println(arrayBlockingQueue.take());

    }
}

package interview.thread;

import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueueDemo {
    public static void main(String[] args) {

        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(arrayBlockingQueue.add("a"));
        System.out.println(arrayBlockingQueue.add("b"));
        System.out.println(arrayBlockingQueue.add("c"));

        System.out.println(arrayBlockingQueue.remove());
        System.out.println(arrayBlockingQueue.remove());

        System.out.println(arrayBlockingQueue.element());
        System.out.println(arrayBlockingQueue.remove());
    }
}

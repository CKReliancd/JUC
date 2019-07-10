package interview.review;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 传统的生产者消费者模式
 */

class ShareData {

    AtomicInteger atomicInteger = new AtomicInteger();

    Lock lock = new ReentrantLock();

    Condition condition = lock.newCondition();

    public void add() {
        System.out.println(Thread.currentThread().getName()+"\t########come to add");
        lock.lock();
        try {
            while (atomicInteger.get() != 0) {
                condition.await();
            }
            atomicInteger.incrementAndGet();
            System.out.println(Thread.currentThread().getName()+"\t"+atomicInteger.get());
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
             lock.unlock();
        }

        System.out.println(Thread.currentThread().getName()+"\t##out after add");

    }

    public void decreace() {
        System.out.println(Thread.currentThread().getName()+"\t########come to decrease");
        lock.lock();
        try {
            while (atomicInteger.get() != 1) {
                condition.await();
            }
            atomicInteger.decrementAndGet();
            System.out.println(Thread.currentThread().getName()+"\t"+atomicInteger.get());
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        System.out.println(Thread.currentThread().getName()+"\t##out after decrease");
    }

}

public class ReviewTraditional {

    public static void main(String[] args) {

        ShareData shareData = new ShareData();

        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                shareData.add();
            }, "AA").start();
        }

        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                shareData.decreace();
            },  "BB").start();
        }
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                shareData.add();
            },  "CC").start();
        }

        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                shareData.decreace();
//                try{TimeUnit.MILLISECONDS.sleep(100);}catch (Exception e){e.printStackTrace();}
            }, "DD").start();
        }
    }
}

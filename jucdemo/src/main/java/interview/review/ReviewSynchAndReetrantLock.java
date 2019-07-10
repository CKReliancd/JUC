package interview.review;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：多线程之间按顺序调用，实现A->B->C三个线程启动，要求如下：
 * AA打印5次，BB打印10次，CC打印15次
 * 紧接着
 * AA打印5次，BB打印10次，CC打印15次
 * 。。。。。。
 * 来10轮
 */

class MySource {

    private int number = 1;
    Lock lock = new ReentrantLock();

    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            while (number != 1) {
                try {
                    c1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName());
            }
            number = 2;
            c2.signal();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try {
            while (number != 2) {
                try {
                    c2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName());
            }
            number = 3;
            c3.signal();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try {
            while (number != 3) {
                try {
                    c3.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName());
            }
            number = 1;
            c1.signal();
        } finally {
            lock.unlock();
        }
    }
}


public class ReviewSynchAndReetrantLock {
    public static void main(String[] args) {
        MySource mySource = new MySource();
        for (int i = 1; i <= 10; i++) {
            new Thread(()->{
                mySource.print5();
            },"AA").start();
            try{ TimeUnit.MILLISECONDS.sleep(300);}catch (Exception e){e.printStackTrace();}
        }
        for (int i = 1; i <= 10; i++) {
            new Thread(()->{
                mySource.print10();
            },"BB").start();
            try{ TimeUnit.MILLISECONDS.sleep(300);}catch (Exception e){e.printStackTrace();}
        }
        for (int i = 1; i <= 10; i++) {
            new Thread(()->{
                mySource.print15();
            },"CC").start();
            try{ TimeUnit.MILLISECONDS.sleep(300);}catch (Exception e){e.printStackTrace();}
        }
    }
}

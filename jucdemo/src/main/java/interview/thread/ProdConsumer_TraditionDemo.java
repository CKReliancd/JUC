package interview.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 传统的生产者消费者模式
 */

class ShareData{

    private Integer number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws Exception {
        lock.lock();
        try {
            //1、判断
            while (number != 0) {
                condition.await();
            }
            //2、干活
            number++;
            System.out.println(Thread.currentThread().getName()+"\t"+ number);
            //3、通知
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }

    public void decrement() throws Exception {

        lock.lock();
        try {
            //1、判断
            while (number == 0) {
                condition.await();
            }
            //2、干活
            number--;
            System.out.println(Thread.currentThread().getName()+"\t"+ number);
            //3、通知
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


}

public class ProdConsumer_TraditionDemo {
    public static void main(String[] args) {
        ShareData data = new ShareData();

        for (int i = 1; i <= 5; i++) {
            new Thread(()->{
                try {
                    data.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"AA").start();
        }
        for (int i = 1; i <= 5; i++) {
            new Thread(()->{
                try {
                    data.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"BB").start();
        }
        for (int i = 1; i <= 5; i++) {
            new Thread(()->{
                try {
                    data.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"CC").start();
        }
        for (int i = 1; i <= 5; i++) {
            new Thread(()->{
                try {
                    data.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"DD").start();
        }
    }
}

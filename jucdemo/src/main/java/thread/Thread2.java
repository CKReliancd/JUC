package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Thread2 {


    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(() -> {
            for (int i = 1; i < 40; i++) {
                try {
                    shareData.increase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "A同学").start();

        new Thread(() -> {
            for (int i = 1; i < 40; i++) {
                try {
                    shareData.decrease();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "B同学").start();
        new Thread(() -> {
            for (int i = 1; i < 40; i++) {
                try {
                    shareData.increase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "C同学").start();

        new Thread(() -> {
            for (int i = 1; i < 40; i++) {
                try {
                    shareData.decrease();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "D同学").start();

    }
}

class ShareData {

    private int number = 0;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void increase() throws Exception {

        lock.lock();
        try {
            while (number != 0) {
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            condition.signalAll();
        }


    }

    public void decrease() throws Exception {

        lock.lock();
        try {
            while (number == 0) {
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            condition.signalAll();
        }
    }

}


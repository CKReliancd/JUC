package Templates;

import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyQueue {


    private Object obj;
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void readObj() {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public void writeObj(Object obj) {
        rwLock.writeLock().lock();
        try {
            this.obj = obj;
            System.out.println(Thread.currentThread().getName() + "writeThread:\t" + obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwLock.writeLock().unlock();
        }
    }

}

/**
 * @author xialei
 * @Description: 一个线程写入, 100个线程读取
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) throws InterruptedException {
        MyQueue q = new MyQueue();

        new Thread(() -> {
            q.writeObj("ClassName1221");
        }, "AAAAA ").start();


        for (int i = 1; i <= 100; i++) {
            new Thread(() -> {
                q.readObj();
            }, String.valueOf(i)).start();
        }


    }
}


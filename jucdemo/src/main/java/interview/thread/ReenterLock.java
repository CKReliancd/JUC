package interview.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁：是指多个线程按照申请锁的顺序来获取锁，类似排队打饭，先来后到
 *
 * 非公平锁：是指多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后申请的线程比先申请的线程优先获取锁，
 *                 在高并发的情况下，有可能会造成优先级反转或者饥饿现象
 *
 *  并发包中ReentranLock的创建可以指定构造函数的Boolean类型来得到公平锁或非公平锁，默认是非公平锁
 *
 *  关于两者区别：
 *          公平锁，在并发环境中，每个线程在获取锁时会先查看此锁维护的等待队列，如果为空，
 *      或者当前线程是等待队列的第一个，就占有锁，否则，就会加入到等待队列中，以后会按照fifo的规则从队列中取到自己
 *          非公平锁，比较粗鲁，上来就直接尝试占有锁，如果尝试失败，就再采用类似公平锁那种方式。
 *
 * 题外话：
 *      Java ReentrantLock而言：
 *      通过构造函数指定该锁是否是公平锁，默认是非公平锁。非公平锁的优点是在于吞吐量比公平锁大。
 *      对于Synchronized而言，也是一种非公平锁
 *
 * 可重入锁（也叫递归锁）
 *      指的是同一线程外层函数获得锁之后，内层递归函数仍然能获取该锁的代码，
 *      在同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁
 *
 *      也即是说，线程可以进入任何一个它已经拥有的锁，所同步着的代码块
 *
 *
 * case one Synchronized就是一个典型的可重入锁
 * t1	    invoked sendSMS                     t1线程在外层方法获取锁的时候
 * t1	    ######invoked sendEmail       t2在进入内层方法会自动获取锁
 *
 * t2	    invoked sendSMS
 * t2	    ######invoked sendEmail
 *
 * case two ReentranLock就是一个典型的可重入锁
 *
 *
 *
 *
 *
 *
 */

class Phone implements Runnable{
    public synchronized void sendSMS(){
        System.out.println(Thread.currentThread().getName()+"\t invoked sendSMS");
        sendEmail();
    }
    public synchronized void sendEmail(){
        System.out.println(Thread.currentThread().getName()+"\t######invoked sendEmail");
    }

    //===============================================
    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    public void get(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t invoked get()");
            set();
        }finally {
            lock.unlock();
        }
    }
    public void set(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t ############invoked set()");
        }finally {
            lock.unlock();
        }
    }
}

public class ReenterLock {
    public static void main(String[] args) {
        Phone phone = new Phone();


        new Thread(()->{
            phone.sendSMS();
        },"t1").start();

        new Thread(()->{
            phone.sendSMS();
        },"t2").start();


        Thread t3 = new Thread(phone,"t3");

        Thread t4 = new Thread(phone,"t4");

        t3.start();
        t4.start();

    }
}

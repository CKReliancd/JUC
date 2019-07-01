package interview.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 题目：实现一个自旋锁
 * 自旋锁的好处：循环获取直到成功为止，没有类似wait的阻塞
 * <p>
 * 通过CAS操作完成自旋锁，A线程先进来调用myLock方法自己持有锁5秒，B随后进来后发现当前线程持有锁
 * 不是null，所以只能通过自旋等待，直到A释放锁后B随后抢到
 */
public class SpinLockDemo {

    static AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public static void myLock() {
        boolean b;
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t come in =_=");
        do {
            b = atomicReference.compareAndSet(null, thread);
            if (b){
                System.out.println(Thread.currentThread().getName() +"\t 获得了锁");
            }
        } while (!b);
    }

    public static void myUnLock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t invoke UnLock");
    }

    public static void main(String[] args) {

        new Thread(() -> {
            SpinLockDemo.myLock();
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (Exception e){e.printStackTrace();}
            SpinLockDemo.myUnLock();
        }, "AA").start();

        new Thread(()->{
            SpinLockDemo.myLock();
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e){e.printStackTrace();}
            SpinLockDemo.myUnLock();
        },"BB").start();

    }

}

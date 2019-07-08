package interview.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1、验证volatile的可见性
 * 1.1 假如int number = 0；numnber变量值钱根本没有添加volatile关键字修饰
 * 1.2 添加volatile，可以解决可见性问题
 * 2、验证volatile不保证原子性
 * 2.1 原子性指什么意思？
 * 不可分割，完整性，也即某个线程正在做某个具体业务，中间不可以被阻塞或者分割。
 * 需要整体完整，要么同时成功， 要么同时失败
 * 2.2是否可以保证原子性
 *
 * 2.3 why
 *
 * 2.4 如何解决原子性？？
 *      * 加上sysn
 *      * 使用aotomic
 */
class MyData {
    volatile int number = 0;

    public int addT060() {
        this.number = 60;
        return number;
    }

    /**
     * 请注意，此时number前面是加了volatile关键字修饰的
     */
    public synchronized void addPlusPlus() {
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();

    public void addMyAtomic(){
        atomicInteger.getAndIncrement();
    }
}
public class VolatilDemo {
    public static void main(String[] args) {

        MyData myData = new MyData();

        for (int i = 1; i < 20; i++) {

            new Thread(() -> {
                for (int j = 0; j <= 1000; j++) {
                    myData.addPlusPlus();
                    myData.addMyAtomic();
                }
            },String.valueOf(i)).start();
        }

        //后台一直都有两个线程，一个是main，一个是GC，可以这么写，保证后台20个线程完成
        while (Thread.activeCount() > 2){
            Thread.yield();
        }

        //上面的线程算完，剩下的就是main线程

        System.out.println(Thread.currentThread().getName()+"\t int type, finally number value : "+myData.number);
        System.out.println(Thread.currentThread().getName()+"\t AtomicInteger type, finally number value : "+myData.atomicInteger);

//        seeOkByVolatile();

    }

    /**
     * Volatile可以保证可见性，即使通知其它线程，主物理内存的值已经被修改
     */
    private static void seeOkByVolatile() {
        MyData myData = new MyData();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            //暂停一会线程
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t update number value" + myData.addT060());
        }, "AAA").start();

        //第二个线程就是main线程
        while (myData.number == 0) {
        }

        System.out.println(Thread.currentThread().getName() + "\t mission is over");
    }

}

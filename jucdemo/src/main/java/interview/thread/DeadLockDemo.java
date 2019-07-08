package interview.thread;

import java.util.concurrent.TimeUnit;

/**
 * 死锁是指两个或两个以上的进程在执行过程中，
 * 因争夺资源而造成的一种互相等待的现象，
 * 若无外力干涉，那他们都无法推进下去，
 */
class HoldLockThread implements Runnable {
    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }


    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有：" + lockA + "\t试图获取：" + lockB);
            try {TimeUnit.SECONDS.sleep(2);} catch (Exception e) {e.printStackTrace();}
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 在内自己持有：" + lockB + "\t 试图获取：" + lockA);
            }
        }
    }
}

public class DeadLockDemo {
    public static void main(String[] args) {

        String lockA = "a";
        String lockB = "b";

        new Thread(new HoldLockThread(lockA,lockB),"ThreadAAA").start();
        new Thread(new HoldLockThread(lockB,lockA),"ThreadBBB").start();



    }
}

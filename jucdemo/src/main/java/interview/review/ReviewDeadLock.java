package interview.review;

import java.util.concurrent.TimeUnit;

class SimulateLock implements Runnable{

    private String LockA;
    private String LockB;

    public SimulateLock(String lockA, String lockB) {
        this.LockA = lockA;
        this.LockB = lockB;
    }

    @Override
    public void run() {
        synchronized (LockA){
            System.out.println(Thread.currentThread().getName()+"\t自己持有LockA："+LockA+"\t试图获取LockB："+LockB);
            try {TimeUnit.SECONDS.sleep(2);} catch (Exception e) {e.printStackTrace();}
            synchronized (LockB){
                System.out.println(Thread.currentThread().getName()+"\t自己持有LockA："+LockB+"\t获取了LockB："+LockA);
            }
        }
    }
}
public class ReviewDeadLock {
    public static void main(String[] args) {

        String LockA = "a";
        String LockB = "b";


        new Thread(new SimulateLock(LockA, LockB),"ThreadAAA").start();

        new Thread(new SimulateLock(LockB, LockA),"ThreadBBB").start();


    }
}

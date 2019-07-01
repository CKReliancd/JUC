package interview.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 *  ABA问题的解决    AtomicStampReference
 */
public class ABADemo {

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);

    static AtomicStampedReference atomicStampedReference =  new AtomicStampedReference<>(100,1);


    public static void main(String[] args) {

        System.out.println("============以下是ABA问题的产生===============");

        new Thread(()->{
            System.out.println(atomicReference.compareAndSet(100, 101)+"\tt1\t"+atomicReference.get());
            System.out.println(atomicReference.compareAndSet(101, 100)+"\tt1\t"+atomicReference.get());
        },"t1").start();

        new Thread(()->{
            try{ TimeUnit.SECONDS.sleep(1); }catch (Exception e){e.printStackTrace();}
            System.out.println(atomicReference.compareAndSet(100, 2019)+"\tt2\t"+atomicReference.get());
        },"t2").start();
        
        try{ TimeUnit.SECONDS.sleep(2); }catch (Exception e){e.printStackTrace();}

        System.out.println("============以下是ABA问题的解决===============");

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t当前第一次版本号："+atomicStampedReference.getStamp());
            try{TimeUnit.SECONDS.sleep(1);}catch (Exception e){e.printStackTrace();}
            atomicStampedReference.compareAndSet(100,101,1,atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t第二次版本号："+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100,2,atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t第三次版本号："+atomicStampedReference.getStamp());

        },"t3").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t当前第一次版本号："+atomicStampedReference.getStamp());
            try{TimeUnit.SECONDS.sleep(3);}catch (Exception e){e.printStackTrace();}
            boolean result = atomicStampedReference.compareAndSet(100, 2019, 1, atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName()+"\t修改成功否："+result+"\t当前最新实际版本号："+atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName()+"\t当前实际最新值："+atomicStampedReference.getReference());

        },"t4").start();



    }

}

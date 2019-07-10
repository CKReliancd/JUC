package interview.review;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

class MyLock{

    AtomicReference<Thread> atomicReference = new AtomicReference();

    public void myLock(){
        Thread thread = Thread.currentThread();

        System.out.println(Thread.currentThread().getName() + "\t come in =_=");

        while (!atomicReference.compareAndSet(null,thread)){}

        System.out.println(Thread.currentThread().getName()+"\tget Lock");
    }

    public void myUnLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"\tUnLock");

    }



}

public class ReviewSpinLock {
    public static void main(String[] args) {
        MyLock myLock = new MyLock();

        new Thread(()->{
            myLock.myLock();
            try{TimeUnit.SECONDS.sleep(5);}catch (Exception e){e.printStackTrace();}
            myLock.myUnLock();
        },"AA").start();


        new Thread(()->{
            myLock.myLock();
            try{TimeUnit.SECONDS.sleep(1);}catch (Exception e){e.printStackTrace();}
            myLock.myUnLock();
        },"BB").start();
    }

}

package interview.review;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone implements Runnable{



    public void sendMessage(){
        Lock lock = new ReentrantLock();
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t sendMassage");
            sendEmail();
        }finally {
             lock.unlock();
        }

    }

    public void sendEmail(){
        Lock lock = new ReentrantLock();
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t ######sendEmail");
        }finally {
             lock.unlock();
        }
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"\tcome in");
        sendMessage();

    }

}

public class ReviewReentranLock {

    public static void main(String[] args) {
        Phone phone = new Phone();

        Thread AAA = new Thread(phone, "AAA");

        Thread BBB = new Thread(phone, "BBB");

        AAA.start();
        BBB.start();

    }

}

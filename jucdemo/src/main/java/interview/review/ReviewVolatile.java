package interview.review;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Resource {

    static AtomicInteger atomicInteger = new AtomicInteger();

    static volatile Integer number = 0;

    public static void addNumber() {
        Resource.number = 60;
    }

    public static void numberPlusPlus() {
        Resource.number++;
    }
}

public class ReviewVolatile {
    public static void main(String[] args) {

        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    Resource.atomicInteger.incrementAndGet();
                }
            }, String.valueOf(i)).start();
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(Resource.atomicInteger);
    }

    private static void notAtomic() {
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    Resource.numberPlusPlus();
                }
                System.out.println(Thread.currentThread().getName() + "\t:" + Resource.number);
            }, String.valueOf(i)).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println(Resource.number);
    }

    private static void seeOkByVolatile() {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Resource.addNumber();
            System.out.println(Thread.currentThread().getName() + "\t update number to 60");
        }, "AAA").start();

        while (Resource.number == 0) {

        }

        System.out.println(Thread.currentThread().getName() + "\tmission is over,main get number value:" + Resource.number);
    }
}

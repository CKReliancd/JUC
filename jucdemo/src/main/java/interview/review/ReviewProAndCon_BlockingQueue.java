package interview.review;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class ProCon {
    AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue = null;
    volatile boolean FLAG = true;

    public ProCon(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void product() {
        boolean offer;
        String data = null;
        while (FLAG) {
            data = atomicInteger.incrementAndGet()+"";
            try {
                offer = blockingQueue.offer(data, 2, TimeUnit.SECONDS);
                if (offer) {
                    System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "成功");
                } else {
                    System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "失败");
                }
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t大领导叫停，意味着FLAG = false");
    }

    public void consumer() {
        String result = null;
        while (FLAG) {
            try {
                result = blockingQueue.poll(2, TimeUnit.SECONDS);
                if (result == null || result.equalsIgnoreCase("")) {
                    System.out.println(Thread.currentThread().getName() + "\t超过2秒没有取到元素，退出");
                    return;
                }
                System.out.println(Thread.currentThread().getName() + "\t获取元素成功：" + result);
//                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        this.FLAG = false;
        System.out.println(Thread.currentThread().getName() + "\t大领导叫停");
    }
}

public class ReviewProAndCon_BlockingQueue {

    public static void main(String[] args) {

        ProCon proCon = new ProCon(new ArrayBlockingQueue(10));

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t生产线程启动");
            proCon.product();
        }, "Product").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t消费线程启动");
            System.out.println();
            proCon.consumer();
        }, "Consumer").start();

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println();
        proCon.close();


    }

}

package interview.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Resource{
    //默认开启
    private volatile boolean FLAG = true;
    private AtomicInteger number = new AtomicInteger();


    BlockingQueue<String> blockingQueue = null;

    public Resource( BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void Prod() throws Exception{
        String data = null;
        boolean retValue;
        while (FLAG){
            data = number.incrementAndGet() + "";
            retValue = blockingQueue.offer(data, 2, TimeUnit.SECONDS);
            if (retValue){
                System.out.println(Thread.currentThread().getName()+"\t插入队列"+data+"成功");
            }else {
                System.out.println(Thread.currentThread().getName()+"\t插入队列"+data+"失败");
            }
            try{TimeUnit.SECONDS.sleep(1);}catch (Exception e){e.printStackTrace();}
        }
        System.out.println(Thread.currentThread().getName()+"\t 大老板叫停,表示flag = false");
    }

    public void cunsumer()throws Exception{
        String result = null;
        while (FLAG){
            result = blockingQueue.poll(2,TimeUnit.SECONDS);
            if (null == result || result.equalsIgnoreCase("")){
                System.out.println(Thread.currentThread().getName()+"\t4秒都没有取到蛋糕，退出");
                return;
            }
            System.out.println(Thread.currentThread().getName()+"\t 消费队列蛋糕"+result+"成功");
        }
    }

    public void stop() throws Exception{
        this.FLAG = false;
    }

}
public class ProductConsumer_BlockQueueDemo {
    public static void main(String[] args) {
        Resource resource = new Resource(new ArrayBlockingQueue<>(10));
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t生产线程启动");
            try {
                resource.Prod();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Prod").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t消费线程启动");
            System.out.println();
            try {
                resource.cunsumer();
                System.out.println();
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Consumer").start();

        try{TimeUnit.SECONDS.sleep(5);}catch (Exception e){e.printStackTrace();}

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("5秒钟后，大老板main线程叫停");
        try {
            resource.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

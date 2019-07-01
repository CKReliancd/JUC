package interview.thread;

/**
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行。
 * 但是
 * 如果有一个线程想去写共享资源类，就不应该再有其它线程可以对该资源进行读或写
 * 小总结：
 *      读-读能共存
 *      读-写不能共存
 *      写-写不能共存
 *
 *      写操作：原子+独占
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 资源类
 */
class MyCache{

    private static volatile Map<String, Object> map = new HashMap<>();

    private static ReentrantReadWriteLock rwLock= new ReentrantReadWriteLock();

    public  static void put(String key, Object value){

        rwLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 正在写入："+ key);
            try{TimeUnit.MILLISECONDS.sleep(300);}catch (Exception e){e.printStackTrace();}
            map.put(key, value);
            System.out.println(Thread.currentThread().getName()+"\t 写入完成");
        }finally {
            rwLock.writeLock().unlock();
        }
    }

    public static void get(String key){

        rwLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 正在读取,key= "+key);
            try{TimeUnit.MILLISECONDS.sleep(300);}catch (Exception e){e.printStackTrace();}
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成,result= "+result);
        }finally {
            rwLock.readLock().unlock();
        }

    }

}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(()->{
                MyCache.put(temp+"",temp+"");
            },String.valueOf(i)).start();
        }
        try{
            TimeUnit.MILLISECONDS.sleep(500);
        }catch (Exception e){e.printStackTrace();}

        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(()->{
                MyCache.get(temp+"");
            },String.valueOf(i)).start();
        }

    }
}

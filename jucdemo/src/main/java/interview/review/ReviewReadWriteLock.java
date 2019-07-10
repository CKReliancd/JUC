package interview.review;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCache{

    Map<String,Object> cache = new HashMap<>();
    ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String key,String value){

        rwLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t正在写入："+key);
            try{TimeUnit.MILLISECONDS.sleep(200);}catch (Exception e){e.printStackTrace();}
            cache.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t写入完成");
            try{TimeUnit.MILLISECONDS.sleep(200);}catch (Exception e){e.printStackTrace();}
        }finally {
             rwLock.writeLock().unlock();
        }
    }

    public void get(String key){

        rwLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t######正在读取："+key);
            try{TimeUnit.MILLISECONDS.sleep(200);}catch (Exception e){e.printStackTrace();}
            Object result = cache.get(key);
            System.out.println(Thread.currentThread().getName()+"\t######读取完成："+result);
            try{TimeUnit.MILLISECONDS.sleep(200);}catch (Exception e){e.printStackTrace();}
        }finally {
             rwLock.readLock().unlock();
        }

    }

}

public class ReviewReadWriteLock {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 1; i <= 5; i++) {
            final String temp =i+"";
            new Thread(()->{

                myCache.put(temp+"",  "Object");
            },String.valueOf(i)).start();
        }

        try{TimeUnit.MILLISECONDS.sleep(200);}catch (Exception e){e.printStackTrace();}
        for (int i = 1; i <= 5; i++) {
            final String temp =i+"";
            new Thread(()->{
                myCache.get(temp+"");
            },String.valueOf(i)).start();
        }


    }
}

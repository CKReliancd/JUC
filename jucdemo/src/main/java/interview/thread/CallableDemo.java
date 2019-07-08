package interview.thread;

import java.util.concurrent.*;

/**
 * java
 * 多线程中，第三种获得多线程的方式
 */

class MyCallable implements Callable {
    @Override
    public Object call() throws Exception {

        System.out.println(Thread.currentThread().getName()+"\t********Come in callable");
        try{ TimeUnit.SECONDS.sleep(5);}catch (Exception e){e.printStackTrace();}
        return 1024;
    }
}

public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        FutureTask<Integer> futureTask = new FutureTask<>(new MyCallable());

        Thread thread = new Thread(futureTask,"AA");
        thread.start();

        System.out.println(Thread.currentThread().getName()+"\t*********");

        while (futureTask.isDone()){

        }

        System.out.println(futureTask.get()+100);

    }
}

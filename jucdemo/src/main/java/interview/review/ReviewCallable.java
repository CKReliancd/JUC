package interview.review;

import java.util.concurrent.*;

class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\tcome in Callable");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1024;
    }
}

public class ReviewCallable {

    public static void main(String[] args) {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyCallable());
        Integer integerA = 0;

        Thread threadA = new Thread(futureTask, "AA");
        threadA.start();

        try {
            integerA = futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(integerA);


    }
}

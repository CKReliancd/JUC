package thread;

import org.springframework.ui.ModelMap;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomicDemo {

    public static void main(String[] args) {

        AtomicDemo ad = new AtomicDemo();

       /* for (int i = 0; i < 10; i++) {
            new Thread(ad).start();
        }*/
            new Thread(ad).start();
            new Thread(ad).start();
            new Thread(ad).start();
            new Thread(ad).start();
            new Thread(ad).start();
            new Thread(ad).start();
            new Thread(ad).start();
            new Thread(ad).start();
            new Thread(ad).start();
            new Thread(ad).start();

            new ModelMap();

    }
}
class AtomicDemo implements Runnable{

    private AtomicInteger serialNumber = new AtomicInteger();

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        System.out.println(Thread.currentThread().getName() +" : " +serialNumber.getAndIncrement());
    }

}

package thread;

public class ThreadTest {

    public static int x = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            MyThread t = new MyThread();
            t.start();
        }
        System.out.println(x);
    }

}
class MyThread extends Thread{



}
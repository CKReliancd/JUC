package interview.jvm.oom;

public class UnablCreateNewNativeThreadDemo {
    public static void main(String[] args) {

        for (int i = 1; ; i++) {
            System.out.println("********* i = "+i);
            new Thread(()->{
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}

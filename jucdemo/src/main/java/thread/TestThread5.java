package thread;

public class TestThread5 {

    public static void main(String[] args) {
        HelloThread2 ht2 = new HelloThread2();
        Thread t2 = new Thread(ht2);
        t2.start();

        for (int i = 100; i < 100000000; i++){
            if (i == 800000){
                ht2.setFlag();
                System.out.println("------------------------");
            }
        }

//        while (t2.isAlive()){
//            t2.interrupt();
//        }
//
//        try {
//            t2.join();
//        } catch (Exception e) {}
//
//        try {
//            Thread.sleep(1);
//        } catch (InterruptedException e) {}
//




    }

}

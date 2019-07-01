package thread;

public class HelloThread2 implements Runnable{

    int i =0;

    boolean flag = true;

    @Override
    public void run() {
        while (flag){

            System.out.println(Thread.currentThread().getName() + ":"+ i++);
        }
    }
    public void setFlag(){
        flag = false;
    }
}

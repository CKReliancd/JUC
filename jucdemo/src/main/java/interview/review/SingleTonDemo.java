package interview.review;

public class SingleTonDemo {

    private static SingleTonDemo singleTonDemo =null;

    private SingleTonDemo(){
        System.out.println(Thread.currentThread().getName()+"\t我是SingleTonDemo构造方法");
    }

    public static SingleTonDemo getSingleTonDemo(){
        if (singleTonDemo == null) {
            synchronized (SingleTonDemo.class){
                if (singleTonDemo == null){
                    singleTonDemo = new SingleTonDemo();
                }
            }
        }
        return singleTonDemo;
    }

    public static void main(String[] args) {

      for (int i = 1; i <= 5; i++) {
          new Thread(()->{
              SingleTonDemo.getSingleTonDemo();
          },String.valueOf(i)).start();
      }
    }

}

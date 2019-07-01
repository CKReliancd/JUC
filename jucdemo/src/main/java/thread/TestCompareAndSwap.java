package thread;

public class TestCompareAndSwap {

    public static void main(String[] args) {
        final CompareAndSwap cas = new CompareAndSwap();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //在进行比较之前手动get一次内存值value
                    int expectedValue = cas.get();
                    boolean b = cas.compareAndSet(expectedValue, (int) (Math.random() * 101));

                    System.out.println(b);
                }
            }).start();
        }

    }

}

class CompareAndSwap{

    private int value;

    //获取内存值
    public synchronized int get(){
        return value;
    }

    //比较
    public synchronized int compareAndSwap(int expectedValue, int newValue){

        //获取内存值
        int oldVlaue= this.value;
        if (oldVlaue == expectedValue){
            this.value = newValue;
        }
        return oldVlaue;
    }
    //设置
    public synchronized boolean compareAndSet(int expectedValue,int newValue){
        return expectedValue == compareAndSwap(expectedValue,newValue);
    }

}

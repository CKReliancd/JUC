package thread;

public class Test2 {

    public int i = 1;

    Test2(){
        this.i = 2;
    }

    public void set3(int j){
        i = j;
    }
    public void set4(int j){
        i = j;
    }

    public static void main(String[] args) {
        Test2 t1 = new Test2();
        Test2 t2 = new Test2();


        t1.set3(3);
        System.out.println(t1.i);


        t2.set4(4);
        System.out.println(t2.i);


    }

}
class Singleton{
    private Singleton(){}
    private static Singleton singleton = new Singleton();


}

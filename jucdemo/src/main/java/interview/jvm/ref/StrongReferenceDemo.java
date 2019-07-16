package interview.jvm.ref;

public class StrongReferenceDemo {
    public static void main(String[] args) {

        //这样定义的默认就是强引用
        Object obj1 = new Object();

        //obj2引用赋值
        Object obj2 = obj1;

        obj1 = null;

        System.gc();
        System.out.println("obj1\t"+obj1);
        System.out.println("obj2\t"+obj2);


    }
}

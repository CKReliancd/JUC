package thread;

public class A {
    static {
        System.out.println("代码块A");
    }

    public A() {
        System.out.println("构造器A");
    }
}

class B extends A {
    static {
        System.out.println("代码块B");
    }

    public B() {
        System.out.println("构造器B");
    }
}
class Test{
    public static void main(String[] args) {
        A ab = new B();
        ab = new B();
    }
}
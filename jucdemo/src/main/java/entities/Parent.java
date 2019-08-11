package entities;

/**
 * 加载顺序练习
 */
public class Parent {
    public static int t = parentStaticMethod2();

    {
        System.out.println("父类非静态初始化块");
    }
    static {
        System.out.println("父类静态初始化块");
    }

    public Parent(){
        System.out.println("父类构造方法");
    }

    public static int parentStaticMethod(){
        System.out.println("父类的静态方法");
        return 0;
    }

    public static int parentStaticMethod2(){
        System.out.println("父类的静态方法2");
        return 9;
    }
}

class Child extends Parent{

    public static int t = childStaticMethod2();

    {
        System.out.println("子类非静态初始化块");
    }

    static {
        System.out.println("子类静态初始化块");
    }

    public Child(){
        System.out.println("子类的构造方法");
    }

    public static int childStaticMethod(){
        System.out.println("子类的静态方法");
        return 1000;
    }

    public static int childStaticMethod2(){
        System.out.println("子类的静态方法2");
        return 1000;
    }

    public static void main(String[] args) {
        Parent child = new Child();
    }
}

























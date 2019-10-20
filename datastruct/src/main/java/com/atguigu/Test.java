package com.atguigu;

public final class Test {

    public static StringBuffer doSomething(StringBuffer buff){

        buff = new StringBuffer();
        buff.append("Hello World");
        return buff;
    }

    public static void main(String[] args) {
        StringBuffer buff = new StringBuffer();

        buff.append("Hello");

        doSomething(buff);

        System.out.println(buff);

        double a = 3/2 ;

        int b = 3/2;
        System.out.println(a+"  "+b);


        int x = 012;
        System.out.println("x= "+x);

    }


}

package interview.jvm.ref;

public class StackOverflowErrorDemo {
    public static void main(String[] args) {

        new Throwable();
        //Exception in thread "main" java.lang.StackOverflowError
        stackOverflowError();
    }

    private static void stackOverflowError() {
        stackOverflowError();
    }

}

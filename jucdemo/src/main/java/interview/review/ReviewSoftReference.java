package interview.review;

import java.lang.ref.SoftReference;

/**
 * -Xms5m -Xmx5m -XX:+PrintGCDetails
 */
public class ReviewSoftReference {

    public static void softRef_Memory_NotEnough() {
        Object o = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o);

        System.out.println(o);
        System.out.println(softReference.get());

        System.out.println("o===================null");
        o = null;
        System.out.println(o);
        System.out.println(softReference.get());




        try {
            byte[] bytes = new byte[30*1024*1024];
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            System.out.println("bytes======================");
            System.out.println(o);
            System.out.println(softReference.get());

        }


    }

    public static void main(String[] args) {
        softRef_Memory_NotEnough();
    }
}

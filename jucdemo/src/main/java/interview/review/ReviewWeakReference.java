package interview.review;

import java.lang.ref.WeakReference;

public class ReviewWeakReference {
    public static void main(String[] args) {
        Object o = new Object();
        WeakReference weakReference = new WeakReference<>(o);

        System.out.println(o);
        System.out.println(weakReference.get());

        System.out.println("===============");

        /**
         * 这里的意思是切断栈里的Object应用变量和堆里的new Object对象的联系，
         * 让softReference引用单独存在,这样new Object()产生的对象免除了强引用Object的干扰
         */
        o = null;
        System.gc();
        System.out.println(o);
        System.out.println(weakReference.get());


    }
}

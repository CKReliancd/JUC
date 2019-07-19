package interview.jvm.gc;

import java.util.Random;

/**
 * 1、-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC        (DefNew+Tenured)
 * <p>
 * 2、-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC         (ParNew+Tenured)
 * <p>
 * 备注情况：Java HotSpot(TM) 64-Bit Server VM warning:
 * Using the ParNew Young Collector with the Serial old collector is deprecated
 * and will likely be removed in a future release
 * <p>
 * 3、-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC     (PSYoungGen+ParOldGen)

 * 4
 * 4.1 -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParalleOldGC   (PSYoungGen+ParOldGen)
 * 4.2 不加就是默认UseParallelGC
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParalleOldG        (PSYoungGen+ParOldGen)
 *
 * 5 -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC  (par new generation + concurrent Mark+Sweep)
 *
 * 6 -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC
 *
 * 实际中被优化掉了
 * 7 -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialOldGC
 *
 *
 */

public class GCDemo {

    public static void main(String[] args) {

        System.out.println("*******GCDemo hello");
        try {
            String str = "atguigu";
            while (true) {
                str += str + new Random().nextInt(7777777) + new Random().nextInt(88888);
                str.intern();
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }


    }
}

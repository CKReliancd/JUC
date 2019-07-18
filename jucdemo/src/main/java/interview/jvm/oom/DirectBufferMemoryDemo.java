package interview.jvm.oom;

import java.nio.ByteBuffer;

/**
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 */
public class DirectBufferMemoryDemo {
    public static void main(String[] args) {
        System.out.println("配置的maxDirectMemory:" + (sun.misc.VM.maxDirectMemory() / (double) 1024 / 1024) + "MB");
        try{ Thread.sleep(3000);}catch (InterruptedException e){e.printStackTrace();}

        //配置的直接内存5m，故意搞一个6m

        ByteBuffer bb = ByteBuffer.allocateDirect(6*1024*1024);

    }
}

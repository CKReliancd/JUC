package interview.jvm.gc;

public class HelloGC {
    public static void main(String[] args) throws InterruptedException {

//        System.out.println("Java虚拟机中的内存总量:\t TOTAL_MEMORY(-Xms) = "+Runtime.getRuntime().totalMemory()+"(字节)、"
//                        + (Runtime.getRuntime().totalMemory()/(double)1024/1024)+"MB");
//
//        System.out.println("Java虚拟机试图使用的最大内存量\t MAX_MEMORY(-Xmx) = "+Runtime.getRuntime().maxMemory()+"(字节)、"
//                        + (Runtime.getRuntime().maxMemory()/(double)1024/1024)+"MB");
//        byte[] byteArray = new byte[50*1024*1024];

        System.out.println("*******HelloGC");
        Thread.sleep(Integer.MAX_VALUE);


    }

}

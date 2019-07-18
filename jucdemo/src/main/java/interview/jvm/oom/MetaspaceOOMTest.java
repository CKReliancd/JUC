package interview.jvm.oom;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * JVM参数
 * -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=8m
 * <p>
 * 模拟Metaspace空间溢出，我们不断的往元空间灌，类占据的空间总是会超过metaspace指定的空间大小
 */
public class MetaspaceOOMTest {
    static class OOMTest {}

    public static void main(String[] args) {
        //模拟计数多少次以后发生异常
        int i = 0;
        try {
            while (true) {
                i++;
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(OOMTest.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invokeSuper(o, args);
                    }
                });

                enhancer.create();

            }
        } catch (Exception e) {
            System.out.println("**********多少次后发生了异常：" + i);
            e.printStackTrace();
        }
    }
}

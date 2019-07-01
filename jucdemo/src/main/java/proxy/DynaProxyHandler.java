package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynaProxyHandler implements InvocationHandler {

    //目标对象
    private Object target;

    //生成代理对象
    public Object newProxyInstance(Object target){
        this.target = target;

        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }



    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理开始了================");



        Object obj = method.invoke(target,args);


        System.out.println( "代理结束了==================");
        return obj;


    }

}

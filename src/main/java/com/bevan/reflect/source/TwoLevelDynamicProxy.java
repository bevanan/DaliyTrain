package main.java.com.bevan.reflect.source;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zbf
 * @since 2024/6/9 上午10:31
 * 1. 我先寻问了创建代理时的两个参数是否是固定的（类加载器、接口数组），待问为啥要指定的类加载器：用于加载传入的接口数组中的所有方法，
 *      并确保代理对象与目标对象在相同的类加载器上，避免类型转换异常或其他兼容性问题。
 * 2. 在问了两个位子的invoke分别代表什么，知道了 InvocationHandler 接口的 invoke 方法和 Method 类的 invoke 的区别
 *      外层的invoke是JVM调用的，内部的invoke看自己要不要调用，下方有例子
 * 3. 接着问了 Method里面的invoke参数target的作用，简而言之就是：目的是告诉method，现在调用的方法要作用在哪一个具体对象上；
 *      args为传的参数，可以为null
 *
 */
public class TwoLevelDynamicProxy {

    /**
     * 动态代理
     * 创建一个接口及其实现类。使用反射机制动态生成一个代理类，代理类在方法调用前后添加日志信息。
     */
    public static void main(String[] args) {
        Hello hello = new HelloImpl();
        Hello proxy = (Hello) Proxy.newProxyInstance(
                hello.getClass().getClassLoader(),
                hello.getClass().getInterfaces(),
                new HelloProxy(hello)
        );
        proxy.sayHello();
        // 没办法调用到
        // proxy.sayGoodBye();
        // hello.sayGoodBye();
        // 没代理
        hello.sayHello();

        // -------另一种创建代理的方式--------
        // 当前线程的类加载器
        ClassLoader customClassLoader = Thread.currentThread().getContextClassLoader();
        // new Class<?>[]{Hello.class}
        Class<?>[] interfaces = {Hello.class};
        Hello proxy2 = (Hello) Proxy.newProxyInstance(
                customClassLoader,
                interfaces,
                new HelloProxy(hello));
        proxy2.sayHello();
    }
}

interface Hello {
    void sayHello();
}

class HelloImpl implements Hello {
    public void sayHello() {
        System.out.println("Hello, World!");
    }

    public void sayGoodBye() {
        System.out.println("Bye, Bye!!!!!!");
    }
}

class HelloProxy implements InvocationHandler {
    private Object target;

    public HelloProxy(Object target) {
        this.target = target;
    }

    // 每当代理对象调用一个方法时，invoke 方法都会被执行。
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before method call");
        // 将方法调用委托给实际目标对象的标准方式，但你可以根据需要进行自定义。
        Object result = method.invoke(target, args);
        System.out.println("After method call");
        return result;

        // 自定义sayHello方法的返回内容（sayHello内的sout就不输出了，因为没有method.invoke(target, args)调用了）
        // if ("sayHello".equals(method.getName())) {
        //     System.out.println("Custom behavior before sayHello");
        //     // 自定义处理，不调用实际对象方法
        //     System.out.println("Hello from custom proxy!");
        //     System.out.println("Custom behavior after sayHello");
        //     return null; // sayHello 方法没有返回值
        // } else {
        //     // 对于其他方法，调用实际对象的方法
        //     return method.invoke(target, args);
        // }
    }
}


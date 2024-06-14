package com.bevan.reflect.simpleSpringBoot;

import com.bevan.reflect.simpleSpringBoot.utils.BeanFactory;
import com.bevan.reflect.simpleSpringBoot.utils.EmbeddedServer;

/**
 * @author zbf
 * @since 2024/6/13 下午8:11
 */
public class HelloApplication {
    public static void main(String[] args) {
        MySpringBootApplication.run(HelloApplication.class, args);
    }
}

class MySpringBootApplication {
    public static void run(Class<?> primarySource, String[] args) {
        BeanFactory beanFactory = new BeanFactory(primarySource.getPackageName());
        EmbeddedServer server = new EmbeddedServer(8080, beanFactory);
        server.start();
    }
}

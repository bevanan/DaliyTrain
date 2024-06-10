package main.java.com.bevan.reflect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author zbf
 * @since 2024/6/9 下午4:29
 * 定义一个自定义注解，应用于类或方法上。使用反射机制读取注解信息并执行相应的逻辑。
 *
 * 相应的疑问，注解到底有什么作用？拿这个例子上说，我们新创建的注解无非就是存了个字符串，能有什么作用？
 *      答：也是例子上的演示，会判断被调用的这个方法是否有这个注解从而看要不要做额外的处理
 *
 * 理解后，可以尝试编写自己简易版依赖注入 MyAutowired
 */
public class TwoLevelAnnotation {
    public static void main(String[] args) {
        try {
            Class<?> clazz = AnnotatedClass.class;
            Method method = clazz.getMethod("annotatedMethod");
            if (method.isAnnotationPresent(MyAnnotation.class)) {
                MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
                System.out.println("Annotation value: " + annotation.value());
                method.invoke(clazz.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
    String value();
}

class AnnotatedClass {
    @MyAnnotation(value = "Hello Annotation")
    public void annotatedMethod() {
        System.out.println("This is an annotated method.");
    }
}




// -------------自定义依赖注入

@Retention(RetentionPolicy.RUNTIME)
@interface MyAutowired {
}

class MyContainer {
    public static void injectDependencies(Object obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(MyAutowired.class)) {
                Class<?> fieldType = field.getType();
                Object dependency = createInstance(fieldType); // 创建依赖对象
                field.setAccessible(true);
                field.set(obj, dependency);
            }
        }
    }

    private static Object createInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + clazz, e);
        }
    }
}


// 示例类和使用
class MyRepository {
    public void save() {
        System.out.println("Saving data...");
    }
}

class MyService {
    @MyAutowired
    private MyRepository myRepository;

    public void doSomething() {
        myRepository.save();
    }
}

class CustomDIExample {
    public static void main(String[] args) throws IllegalAccessException {
        MyService myService = new MyService();
        MyContainer.injectDependencies(myService);
        myService.doSomething();
    }
}
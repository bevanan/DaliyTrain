package com.bevan.reflect.daliy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author zbf
 * @since 2024/6/11 上午9:04
 */
public class Day01 {
    public static void main(String[] args) throws IllegalAccessException {
        MyService myService = new MyService();
        MyContainer.injectDependencies(myService);
        myService.saveFunction();
    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface Autowired {
}

class MyMapper {
    public void save() {
        System.out.println("saving data..");
    }
}

class MyService {
    @Autowired
    private MyMapper myMapper;

    public void saveFunction() {
        myMapper.save();
    }
}

class MyContainer {
    public static void injectDependencies(Object obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Class<?> type = field.getType();
                Object instance = createInstance(type);
                field.setAccessible(true);
                field.set(obj, instance);
            }
        }
    }

    private static Object createInstance(Class<?> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}

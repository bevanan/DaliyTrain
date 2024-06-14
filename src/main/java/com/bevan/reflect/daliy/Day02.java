package com.bevan.reflect.daliy;

import com.bevan.stream.Person;
import com.bevan.stream.Student;

import java.io.File;
import java.lang.reflect.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zbf
 * @since 2024/6/9 上午10:08
 */
public class Day02 {
    // 用Student当例子吧
    public static void main(String[] args) {
        getClassInfo1();
    }

    /**
     * 获取类信息
     * 编写一个程序，使用反射机制获取并打印任意给定类的所有字段、方法、类名、包名的信息。
     */
    private static void getClassInfo1() {

    }

    /**
     * 实例化对象
     * 使用反射创建一个指定类的对象，并调用其无参构造方法。
     */
    private static void getInitClass1() {

    }

    /**
     * 实例化对象
     * 使用反射创建一个指定类的对象，并调用其有参构造方法。
     */
    private static void getInitByConstructorClass1() {

    }


    /**
     * 访问私有字段、方法
     * 定义一个带有私有字段的类，使用反射机制修改并访问该私有字段。
     */
    // class Person {
    //     private String name = "John";
    //
    //     private void secretMethod() {
    //         System.out.println("This is a secret method!");
    //     }
    // }
    private static void getPrivatePram1() {

    }

    /**
     * 泛型与反射
     * 编写代码使用反射机制获取一个泛型类的实际类型参数。
     */
    public static void GenericReflection1() {

    }





    // ------------answer-------------

    /**
     * 获取类信息
     * 编写一个程序，使用反射机制获取并打印任意给定类的类名、实现了什么接口、包名、所有字段和方法的信息。
     */
    private static void getClassInfo() {
        try {
            Class<?> clazz = Class.forName("java.util.ArrayList");
            Class<Student> studentClass = Student.class;
            System.out.println("Class Name: " + clazz.getName());
            System.out.println("Package Name: " + clazz.getPackage());

            System.out.println("Fields:");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                System.out.println(field.getName());
            }

            System.out.println("Methods:");
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println(method.getName());
            }

            System.out.println("Interfaces:");
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                System.out.println(anInterface);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 实例化对象
     * 使用反射创建一个指定类的对象，并调用其无参构造方法。
     */
    private static void getInitByDeclaredConstructorClass() {
        try {
            Class<?> clazz = Class.forName("java.util.ArrayList");
            Object instance = clazz.getDeclaredConstructor().newInstance();
            ArrayList<Integer> arr = (ArrayList<Integer>) instance;
            System.out.println("Instance created: " + instance.getClass().getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 实例化对象
     * 使用反射创建一个指定类的对象，并调用其有参构造方法。
     */
    private static void getInitByConstructorClass() {
        try {
            Class<?> clazz = Person.class;
            Constructor<?> constructor = clazz.getConstructor(String.class, int.class);
            Object person = constructor.newInstance("John", 30);
            Method method = clazz.getMethod("display");
            method.invoke(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 访问私有字段、方法
     * 定义一个带有私有字段的类，使用反射机制修改并访问该私有字段。
     */
    // class Person {
    //     private String name = "John";
    //
    //     private void secretMethod() {
    //         System.out.println("This is a secret method!");
    //     }
    // }
    private static void getPrivatePram() {
        try {
            Person person = new Person();
            Field field = Person.class.getDeclaredField("name");
            field.setAccessible(true);
            System.out.println("Original value: " + field.get(person));
            field.set(person, "Jane");
            System.out.println("Modified value: " + field.get(person));

            Method method = Person.class.getDeclaredMethod("secretMethod");
            method.setAccessible(true);
            method.invoke(person);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 泛型与反射
     * 编写代码使用反射机制获取一个泛型类的实际类型参数。
     */
    // public static void GenericReflection() {
    //     GenericClass<List<String>> genericClass = new GenericClass<List<String>>() {
    //     };
    //     Type type = ((ParameterizedType) genericClass.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    //     System.out.println("Generic type: " + type);
    // }
}


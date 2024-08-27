package com.bevan.reflect.source;

import com.bevan.stream.Student;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author zbf
 * @since 2024/6/13 下午8:41
 *
 * 通过反射获取到指定类所在的 包下的所有class
 */
public class TwoLevelGetPackageClass {
    public static void main(String[] args) {
        Class<Student> clazz = Student.class;
        String packageName = clazz.getPackageName();
        String path = packageName.replace('.', '/');
        System.out.println(path);

        URL resource = Thread.currentThread().getContextClassLoader().getResource(path);
        System.out.println(resource);
        try {
            File directory = new File(resource.toURI());
            System.out.println(directory);
            for (File file : directory.listFiles()) {
                System.out.println(file.getName());
                String className = packageName + "." + file.getName().replace(".class", "");
                System.out.println(className);
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}

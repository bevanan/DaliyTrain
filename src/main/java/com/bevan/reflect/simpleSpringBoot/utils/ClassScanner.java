package com.bevan.reflect.simpleSpringBoot.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Baven
 * @date 2024/6/12 23:47
 * 类扫描器
 * 扫描指定包下的所有类
 */
public class ClassScanner {
    public static Set<Class<?>> scan(String basePackage) throws URISyntaxException, ClassNotFoundException {
        HashSet<Class<?>> classes = new HashSet<>();
        String path = basePackage.replace('.', '/');
        URL resource = Thread.currentThread().getContextClassLoader().getResource(path);
        if (resource != null) {
            File directory = new File(resource.toURI());
            for (File file : directory.listFiles()) {
                if (file.getName().endsWith(".class")) {
                    String className = basePackage + "." + file.getName().replace(".class", "");
                    System.out.println(className);
                    classes.add(Class.forName(className));
                }
            }
        }
        return classes;
    }
}

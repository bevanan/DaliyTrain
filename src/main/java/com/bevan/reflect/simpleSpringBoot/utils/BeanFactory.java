package main.java.com.bevan.reflect.simpleSpringBoot.utils;

import main.java.com.bevan.reflect.simpleSpringBoot.annotation.RequestMapping;
import main.java.com.bevan.reflect.simpleSpringBoot.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Baven
 * @date 2024/6/12 23:20
 */
public class BeanFactory {
     private Map<Class<?>, Object> beanMap = new HashMap<>(16);
     private Map<String, Method> handlerMapping = new HashMap<>(16);

     public BeanFactory(String basePackage) {
          try {
               Set<Class<?>> classes = ClassScanner.scan(basePackage);
               for (Class<?> clazz : classes) {
                    if (clazz.isAnnotationPresent(RestController.class)) {
                         Object instance = clazz.getDeclaredConstructor().newInstance();
                         beanMap.put(clazz, instance);

                         for (Method method : clazz.getDeclaredMethods()) {
                              if (method.isAnnotationPresent(RequestMapping.class)) {
                                   RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                                   handlerMapping.put(requestMapping.value(), method);
                              }
                         }
                    }
               }
          } catch (Exception e) {
               e.printStackTrace();
          }
     }

     public Object getBean(Class<?> clazz) {
          return beanMap.get(clazz);
     }

     public Method getHandler(String url) {
          return handlerMapping.get(url);
     }
}

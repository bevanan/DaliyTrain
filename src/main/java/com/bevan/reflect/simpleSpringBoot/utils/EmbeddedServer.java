package com.bevan.reflect.simpleSpringBoot.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author zbf
 * @since 2024/6/13 下午7:48
 */
public class EmbeddedServer {
    private int port;
    private BeanFactory beanFactory;

    public EmbeddedServer(int port, BeanFactory beanFactory) {
        this.port = port;
        this.beanFactory = beanFactory;
    }

    public void start() {
        // 创建一个Jetty服务器实例，并指定服务器监听的端口号。
        Server server = new Server(port);

        // 设置一个处理器，用于处理所有传入的HTTP请求。
        // AbstractHandler：一个Jetty的抽象类，实现了Handler接口，我们通过继承这个类并重写handle方法来定义自己的请求处理逻辑。
        server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
                    throws IOException, ServletException {
                Method handlerMethod = beanFactory.getHandler(target);
                if (handlerMethod != null) {
                    Object controller = beanFactory.getBean(handlerMethod.getDeclaringClass());
                    try {
                        Object result = handlerMethod.invoke(controller);
                        httpServletResponse.setContentType("text/plain");
                        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                        httpServletResponse.getWriter().write(result.toString());
                    } catch (Exception e) {
                        httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        throw new RuntimeException(e);
                    }
                } else {
                    httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
                request.setHandled(true);
            }
        });

        try {
            // 启动Jetty服务器
            server.start();
            // 等待服务器关闭
            server.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

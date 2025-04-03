package com.bevan.design.chain;

public class ForTest {
    public static void main(String[] args) {
        Handler h1 = new HandlerA();
        Handler h2 = new HandlerB();
        Handler h3 = new HandlerC();
        h1.setNext(h2);
        h2.setNext(h3);
        RequestData requestData = new RequestData("请求数据ABCDE");
        h1.handleRequest(requestData);
        System.out.println(requestData.getData());
    }
}

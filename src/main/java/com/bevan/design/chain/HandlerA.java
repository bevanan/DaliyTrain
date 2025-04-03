package com.bevan.design.chain;

public class HandlerA extends Handler {
    @Override
    public void handleRequest(RequestData requestData) {
        System.out.println("HandlerA 执行代码逻辑! 处理: " + requestData.getData());
        requestData.setData(requestData.getData().replace("A",""));
        if(next != null){
            next.handleRequest(requestData);
        }else{
            System.out.println("执行中止!");
        }
    }
}

package com.bevan.design.chain.base;

public class HandlerC extends Handler {

    @Override
    public void handleRequest(RequestData requestData) {
        System.out.println("HandlerC 执行代码逻辑! 处理: " + requestData.getData());
        requestData.setData(requestData.getData().replace("C",""));
        if(next != null){
            next.handleRequest(requestData);
        }else{
            System.out.println("执行中止!");
        }
    }
}

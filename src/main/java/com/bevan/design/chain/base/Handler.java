package com.bevan.design.chain.base;

public abstract class Handler {
    protected Handler next;

    public void setNext(Handler next) {
        this.next = next;
    }

    public abstract void handleRequest(RequestData requestData);
}

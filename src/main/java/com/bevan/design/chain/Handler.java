package com.bevan.design.chain;

public abstract class Handler {
    protected Handler next;

    public void setNext(Handler next) {
        this.next = next;
    }

    public abstract void handleRequest(RequestData requestData);
}

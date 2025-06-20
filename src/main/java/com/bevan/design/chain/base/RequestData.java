package com.bevan.design.chain.base;

import lombok.Data;

@Data
public class RequestData {
    private String data;

    public RequestData(String data) {
        this.data = data;
    }
}

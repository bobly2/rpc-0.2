package com.rpc.server.rpcrequest;

import lombok.Data;

import java.io.Serializable;

@Data
public class CalculateRpcRequest implements Serializable {

    private Long timeId = System.currentTimeMillis();

    private String method;
    private int a;
    private int b;

    @Override
    public String toString() {
        return "CalculateRpcRequest{" +
                "method='" + method + '\'' +
                ", a=" + a +
                ", b=" + b +
                '}';
    }
}

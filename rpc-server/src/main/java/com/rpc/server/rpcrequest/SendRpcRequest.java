package com.rpc.server.rpcrequest;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: SC19002999
 * @time: 2020/4/23 18:08
 */
@Data
public class SendRpcRequest implements Serializable {
    private static final long serialVersionUID = 7503710091945320739L;

    private String method;
    private String str;

    @Override
    public String toString() {
        return "CalculateRpcRequest{" +
                "method='" + method + '\'' +
                ", str=" + str +
                '}';
    }
}
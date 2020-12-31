package com.rpc.server.rpcrequest;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: SC19002999
 * @Description:
 * @Date: 2020/12/31 16:12
 * @Version: 1.0
 */
@Data
public class AddRequest implements Serializable {
    private Integer result;
    private Long timeId;
}

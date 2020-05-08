package com.rpc.provider.Dto;

import lombok.Data;

/**
 * @description:
 * @author: SC19002999
 * @time: 2020/5/6 17:53
 */
@Data
public class BaseMsg {
    private Integer msgType;
    private String msgContent;
}
package com.rpc.server.comsumer.app;


import com.rpc.server.comsumer.service.CalculatorRemoteImpl;
import com.rpc.server.provider.service.Calculator;

/**
 * @description:消费 调用方
 * @author:SC19002999
 * @create: 2020/4/23
 **/
public class ComsumerApp {

    public static void main(String[] args) {

        Calculator calculator = new CalculatorRemoteImpl();
        calculator.add(1, 2);
//        String str = calculator.send("hello rpc");
//        System.out.print(str);
    }
}

package com.rpc.server.provider.service;


import org.springframework.stereotype.Service;

@Service
public class CalculatorImpl implements Calculator {
    @Override
    public int add(int a, int b) {
        int c = a + b;
        System.out.print("result=" + c);
        return c;
    }

    @Override
    public String send(String str) {
        return "接收到的消息" + str;
    }

}

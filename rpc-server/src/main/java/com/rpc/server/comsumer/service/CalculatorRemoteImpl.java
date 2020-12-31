package com.rpc.server.comsumer.service;


import com.rpc.server.comsumer.nettyclient.NettyClient;
import com.rpc.server.provider.service.Calculator;
import com.rpc.server.rpcrequest.CalculateRpcRequest;
import com.rpc.server.rpcrequest.SendRpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CalculatorRemoteImpl implements Calculator {
    public static final int PORT = 9090;


    @Override
    public int add(int a, int b) {
        this.getMethodAdd(a, b);
        return 0;
//        List<String> addressList = lookupProviders("Calculator.add");
//        String address = chooseTarget(addressList);
//        try {
//            //创建Socket对象
//            Socket socket = new Socket(address, PORT);
//            OutputStream outputStream = socket.getOutputStream();//获取一个输出流，向服务端发送信息
//            // 将请求序列化
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
//            CalculateRpcRequest calculateRpcRequest = generateRequest(a, b);
//            // 将请求发给服务提供方
//            objectOutputStream.writeObject(calculateRpcRequest);
//
//            // 将响应体反序列化
//            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
//            Object response = objectInputStream.readObject();
//            System.out.println("response=" + response);
//
//            if (response instanceof Integer) {
//                return (Integer) response;
//            } else {
//                throw new InternalError();
//            }
//
//        } catch (Exception e) {
//            log.error("fail", e);
//            throw new InternalError();
//        }
    }

    private CalculateRpcRequest generateRequest(int a, int b) {
        CalculateRpcRequest calculateRpcRequest = new CalculateRpcRequest();
        calculateRpcRequest.setA(a);
        calculateRpcRequest.setB(b);
        calculateRpcRequest.setMethod("add");
        return calculateRpcRequest;
    }

    private String chooseTarget(List<String> providers) {
        if (null == providers || providers.size() == 0) {
            throw new IllegalArgumentException();
        }
        return providers.get(0);
    }

    public static List<String> lookupProviders(String name) {
        List<String> strings = new ArrayList();
        strings.add("127.0.0.1");
        return strings;
    }

    @Override
    public String send(String str) {
        List<String> addressList = lookupProviders("Calculator.add");
        String address = chooseTarget(addressList);
        try {
            //创建Socket对象
            Socket socket = new Socket(address, PORT);
            OutputStream outputStream = socket.getOutputStream();//获取一个输出流，向服务端发送信息
            // 将请求序列化
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            SendRpcRequest sendRpcRequest = generateRequest(str);
            // 将请求发给服务提供方
            objectOutputStream.writeObject(sendRpcRequest);

            // 将响应体反序列化
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object response = objectInputStream.readObject();
            System.out.print("response=" + response);

            if (response instanceof String) {
                return (String) response;
            } else {
                throw new InternalError();
            }

        } catch (Exception e) {
            log.error("fail", e);
            throw new InternalError();
        }
    }


    private SendRpcRequest generateRequest(String str) {
        SendRpcRequest calculateRpcRequest = new SendRpcRequest();
        calculateRpcRequest.setStr(str);
        calculateRpcRequest.setMethod("send");
        return calculateRpcRequest;
    }

    //启动netty客户端
    public void getMethodAdd(int a, int b) {
        NettyClient nettyClient = new NettyClient();
        CalculateRpcRequest calculateRpcRequest = generateRequest(a, b);
        nettyClient.startAdd(calculateRpcRequest);
    }
}

package com.rpc.server.provider.app;


import com.rpc.server.provider.nettyserver.NettyServer;
import com.rpc.server.provider.service.Calculator;
import com.rpc.server.provider.service.CalculatorImpl;
import com.rpc.server.rpcrequest.CalculateRpcRequest;
import com.rpc.server.rpcrequest.SendRpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description: 提供服务
 * @author:SC19002999
 * @create: 2020/4/23
 **/
@Slf4j
public class ProviderApp {


    private Calculator calculator = new CalculatorImpl();

    public static void main(String[] args) throws IOException {
//        new ProviderApp().run();
        new ProviderApp().runNetty();
    }

    private void run() throws IOException {
        //创建ServerSocket对象，绑定并监听端口
        ServerSocket listener = new ServerSocket(9090);
        System.out.println("服务端已启动，等待客户端连接..");
        try {
            while (true) {
                //侦听并接受到此套接字的连接,返回一个Socket对象
                Socket socket = listener.accept();
                try {
                    //得到一个输入流，接收客户端传递的信息
                    InputStream inputStream = socket.getInputStream();
                    // 将请求反序列化
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    Object object = objectInputStream.readObject();
                    System.out.println("object=" + object);

                    ObjectOutputStream objectOutputStream = null;
                    // 调用服务
                    int result = 0;
//                    　　instanceof 严格来说是Java中的一个双目运算符，用来测试一个对象是否为一个类的实例，用法为： boolean result = obj instanceof Class
                    if (object instanceof CalculateRpcRequest) {
                        CalculateRpcRequest calculateRpcRequest = (CalculateRpcRequest) object;
                        if ("add".equals(calculateRpcRequest.getMethod())) {
                            // 返回结果
                            result = calculator.add(calculateRpcRequest.getA(), calculateRpcRequest.getB());
                            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                            objectOutputStream.writeObject(new Integer(result));
                        } else {
                            throw new UnsupportedOperationException();
                        }
                    }

                    String result2 = null;
                    if (object instanceof SendRpcRequest) {
                        SendRpcRequest sendRpcRequest = (SendRpcRequest) object;
                        if ("send".equals(sendRpcRequest.getMethod())) {
                            // 返回结果
                            result2 = calculator.send(sendRpcRequest.getStr());
                            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                            objectOutputStream.writeObject(new String(result2));
                        } else {
                            throw new UnsupportedOperationException();
                        }
                    }

                } catch (Exception e) {
                    log.error("fail", e);
                } finally {
                    socket.close();
                }
            }
        } finally {
            listener.close();
        }
    }

    //通过netty通信
    private void runNetty() throws IOException {
        //启动服务端
        NettyServer nettyServer = new NettyServer();
        //监听9090端口
        nettyServer.start(new InetSocketAddress("127.0.0.1", 8090));
    }
}

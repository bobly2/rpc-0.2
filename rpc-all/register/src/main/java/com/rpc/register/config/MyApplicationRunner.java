package com.rpc.register.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @description:
 * @author: SC19002999
 * @time: 2020/4/30 16:33
 */
@Component
@Order(value = 1)
public class MyApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("注册器启动：监听8085端口的请求");
        initServer();
    }

    private void initServer() throws InterruptedException {
        // 用来接收进来的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 用来处理已经被接收的连接，一旦bossGroup接收到连接，就会把连接信息注册到workerGroup上
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // nio服务的启动类
            ServerBootstrap sbs = new ServerBootstrap();
            // 配置nio服务参数
            sbs.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // 说明一个新的Channel如何接收进来的连接
                    .option(ChannelOption.SO_BACKLOG, 128) // tcp最大缓存链接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //保持连接
                    .handler(new LoggingHandler(LogLevel.INFO)) // 打印日志级别

                    //服务器启动后 绑定监听端口,同步等待成功主要用于异步操作的通知回调,回调处理用的ChildChannelHandler
                    .childHandler(
                            //new ChannelHandler()
                            new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    System.out.println("有一客户端链接到本服务端");
                                    System.out.println("IP:" + socketChannel.localAddress().getHostName());
                                    System.out.println("Port:" + socketChannel.localAddress().getPort());
                                    // marshalling 序列化对象的解码
//                      socketChannel.pipeline().addLast(MarshallingCodefactory.buildDecoder());
                                    // marshalling 序列化对象的编码
//                      socketChannel.pipeline().addLast(MarshallingCodefactory.buildEncoder());
                                    // 网络超时时间
//                      socketChannel.pipeline().addLast(new ReadTimeoutHandler(5));
                                    // 处理接收到的请求
//                                    socketChannel.pipeline().addLast(new HeartBeatIdleHandler());
                                    socketChannel.pipeline().addLast(new ServerHandler()); // 这里相当于过滤器，可以配置多个
                                }
                            });

            // 绑定端口，开始接受链接  监听端口的socket请求
            ChannelFuture cf = sbs.bind(8085).sync();
            // 开多个端口
//          ChannelFuture cf2 = sbs.bind(3333).sync();
//          cf2.channel().closeFuture().sync();

            // 等待服务端口的关闭；在这个例子中不会发生，但你可以优雅实现；关闭你的服务
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
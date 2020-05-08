package com.rpc.provider.config;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;


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
        System.out.println("服务启动,服务连接端口：8085");
        initServer();
    }

    private void initServer() throws InterruptedException, UnsupportedEncodingException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 客户端辅助启动类 对客户端配置
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new AppClientHandler());
                        }
                    });
            // 异步链接服务器 同步等待链接成功
            ChannelFuture cf = b.connect("127.0.0.1", 8085).sync();

            //发起客户端请求
            String reqStr = "ServerA";
            cf.channel().writeAndFlush(Unpooled.copiedBuffer(reqStr.getBytes("UTF-8")));
            // 等待链接关闭
            cf.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
            System.out.println("释放线程资源...");
        }

    }
}
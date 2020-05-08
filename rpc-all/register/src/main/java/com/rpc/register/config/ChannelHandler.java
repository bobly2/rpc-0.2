package com.rpc.register.config;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: SC19002999
 * @time: 2020/5/6 17:35
 */
public class ChannelHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel e) throws Exception {
        System.out.println("有一客户端链接到本服务端");
        System.out.println("IP:" + e.localAddress().getHostName());
        System.out.println("Port:" + e.localAddress().getPort());

        /**
         * 心跳包 1、readerIdleTimeSeconds 读超时时间 2、writerIdleTimeSeconds 写超时时间
         * 3、allIdleTimeSeconds 读写超时时间 4、TimeUnit.SECONDS 秒[默认为秒，可以指定]
         */
        e.pipeline().addLast(new IdleStateHandler(2, 2, 2, TimeUnit.SECONDS));
//		// 基于换行符号解码器
//		e.pipeline().addLast(new LineBasedFrameDecoder(1024));
//		// 解码转String
//		e.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
//		// 编码器 String
//		e.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));

        // 处理心跳 【放在编码解码的下面，因为这个是通道有处理顺序】
//        e.pipeline().addLast(new HeartBeatIdleHandler());

        // 这里相当于过滤器，可以配置多个
        // 在管道中添加我们自己的接收数据实现方法
        e.pipeline().addLast(new ServerHandler());
    }

}

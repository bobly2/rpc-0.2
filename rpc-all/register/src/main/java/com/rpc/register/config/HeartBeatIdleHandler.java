package com.rpc.register.config;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @description:
 * @author: SC19002999
 * @time: 2020/5/6 17:41
 */
public class HeartBeatIdleHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                System.out.println("--- Reader Idle ---");
                ctx.writeAndFlush("读取等待：客户端你在吗... ...\r\n");
                // ctx.close();
            } else if (e.state() == IdleState.WRITER_IDLE) {
                System.out.println("--- Write Idle ---");
                ctx.writeAndFlush("写入等待：客户端你在吗... ...\r\n");
                // ctx.close();
            } else if (e.state() == IdleState.ALL_IDLE) {
                System.out.println("--- All_IDLE ---");
                ctx.writeAndFlush("全部时间：客户端你在吗... ...\r\n");
            }
        }
    }

}
package com.rpc.server.comsumer.nettyclient;

import com.rpc.server.comsumer.ResultFactory.ResultFactory;
import com.rpc.server.rpcrequest.AddRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: SC19002999
 * @Description:
 * @Date: 2020/12/31 10:49
 * @Version: 1.0
 */
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Autowired
    ResultFactory resultFactory;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端Active .....");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        log.info("客户端收到消息: {}", msg.toString());
        if (msg instanceof AddRequest) {
            AddRequest addRequest = (AddRequest) msg;
            ResultFactory.put(addRequest.getTimeId().toString(), addRequest.getResult());
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

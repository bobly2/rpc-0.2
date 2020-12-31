package com.rpc.server.provider.nettyserver;

import com.rpc.server.provider.service.CalculatorImpl;
import com.rpc.server.rpcrequest.AddRequest;
import com.rpc.server.rpcrequest.CalculateRpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: SC19002999
 * @Description:
 * @Date: 2020/12/31 10:47
 * @Version: 1.0
 */

@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端连接会触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel active......");
    }

    /**
     * 客户端发消息会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务端收到消息:" + msg.toString());
        AddRequest addRequest = this.getMethodAdd(msg);

        ctx.write(addRequest);
        ctx.flush();
    }

    /**
     * 发生异常触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    //用netty接收的数据来实现add
    public AddRequest getMethodAdd(Object object) {
        // 调用服务
        int result = 0;
        if (object instanceof CalculateRpcRequest) {
            CalculateRpcRequest calculateRpcRequest = (CalculateRpcRequest) object;
            if ("add".equals(calculateRpcRequest.getMethod())) {
                // 返回结果
                result = new CalculatorImpl().add(calculateRpcRequest.getA(), calculateRpcRequest.getB());
                AddRequest addRequest = new AddRequest();
                addRequest.setResult(result);
                addRequest.setTimeId(calculateRpcRequest.getTimeId());
                return addRequest;
            } else {
                throw new UnsupportedOperationException();
            }
        }
        return null;
    }
}

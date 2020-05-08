package com.rpc.provider.config;

import com.rpc.provider.Dto.BaseMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @description:
 * @author: SC19002999
 * @time: 2020/5/6 17:52
 */
public class AppClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
//    private static final Logger logger = Logger.getLogger(AppClientHandler.class.getName());

    /**
     * 客户端连接
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端active");
        BaseMsg bm = new BaseMsg();
        bm.setMsgType(100);
        bm.setMsgContent("测试的内容");
        byte[] reqs = null;
        ctx.write("hello world");
//        ctx.writeAndFlush(Unpooled.buffer(reqs.length).writeBytes(reqs));
    }

    /**
     * 客户端读取服务端返回数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("客户端收到服务器响应数据");
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("======>" + body);
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        System.out.println("客户端收到服务器响应数据处理完成:");
        //读取服务端返回后，再发送，再返回这样一直循环，长连接
        Thread.sleep(2000);
        BaseMsg bm = new BaseMsg();
        byte[] reqs = "hell world".getBytes("UTF-8");
        ctx.writeAndFlush(Unpooled.buffer(reqs.length).writeBytes(reqs));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        logger.warning("Unexpected exception from downstream:" + cause.getMessage());
        System.out.println("客户端异常退出");
        ctx.close();

    }


}
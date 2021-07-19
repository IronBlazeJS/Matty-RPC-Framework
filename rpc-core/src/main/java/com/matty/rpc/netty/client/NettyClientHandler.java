package com.matty.rpc.netty.client;

import com.matty.rpc.entity.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: NettyClientHandler
 * author: Matty Roslak
 * date: 2021/7/19  14:48
 * Netty客户端处理器
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    private static final Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        try {
            logger.info(String.format("客户端接收到消息：%s", msg));
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
            //将RpcResponse对象以 key 为 rpcResponse 放入 ChannelHandlerContext 中
            ctx.channel().attr(key).set(msg);
            //关闭客户端通道
            ctx.channel().close();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("过程调用中有错误发生：");
        cause.printStackTrace();
        ctx.close();
    }
}

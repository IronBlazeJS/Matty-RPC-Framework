package com.matty.rpc.netty.server;

import com.matty.rpc.RequestHandler;
import com.matty.rpc.entity.RpcRequest;
import com.matty.rpc.entity.RpcResponse;
import com.matty.rpc.registry.DefaultServiceRegistry;
import com.matty.rpc.registry.ServiceRegistry;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: NettyServerHandler
 * author: Matty Roslak
 * date: 2021/7/19  14:19
 * Netty服务端处理从客户端传来的RpcRequest
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private static RequestHandler requestHandler;
    private static ServiceRegistry serviceRegistry;

    static{
        requestHandler = new RequestHandler();
        serviceRegistry = new DefaultServiceRegistry();
    }

    /**
     * 通道读取就绪事件--接受客户端请求
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        try{
            logger.info("服务端接收到请求：{}", msg);
            // 从RpcRequest中获取方法的各个参数
            String interfaceName = msg.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName);
            // 执行方法，获得返回值
            Object result = requestHandler.handle(msg, service);
            // 把返回值发送回去
            ChannelFuture future = ctx.writeAndFlush(RpcResponse.success(result));
            //添加一个监听器到channelfuture来检测是否所有的数据包都发出，然后关闭通道
            future.addListener(ChannelFutureListener.CLOSE);
        }finally {
            // 减少一个对象引用就调用release，计数值为0时进行回收
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("处理过程调用时有错误发生：");
        cause.printStackTrace();
        ctx.close();
    }
}

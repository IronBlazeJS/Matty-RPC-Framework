package com.matty.rpc.netty.client;

import com.matty.rpc.RpcClient;
import com.matty.rpc.codec.CommonDecoder;
import com.matty.rpc.codec.CommonEncoder;
import com.matty.rpc.entity.RpcRequest;
import com.matty.rpc.entity.RpcResponse;
import com.matty.rpc.serializer.JsonSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: NettyClient
 * author: Matty Roslak
 * date: 2021/7/19  14:14
 * Netty方式客户端
 */
public class NettyClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private String host;
    private int port;
    private static final Bootstrap bootstrap;

    public NettyClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new CommonDecoder())
                                .addLast(new CommonEncoder(new JsonSerializer()))
                                .addLast(new NettyClientHandler());
                    }
                });
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            logger.info("客户端连接到服务端{}：{}", host, port);
            Channel channel = future.channel();
            if(channel != null){
                //向服务端发请求，并设置监听，关于writeAndFlush()的具体实现可以参考：https://blog.csdn.net/qq_34436819/article/details/103937188
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if(future1.isSuccess()){
                        logger.info(String.format("客户端发送消息：%s", rpcRequest.toString()));
                    }else {
                        logger.error("发送消息时有错误发生:", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                //AttributeMap<AttributeKey, AttributeValue>是绑定在Channel上的，可以设置用来获取通道对象
                // 设置key
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                //get()阻塞获取value
                RpcResponse rpcResponse = channel.attr(key).get();
                return rpcResponse.getData();
            }
        }catch (InterruptedException e){
            logger.error("发送消息时有错误发生:", e);
        }
        return null;
    }
}
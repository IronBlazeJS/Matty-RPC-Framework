package com.matty.rpc.transport.netty.server;

import com.matty.rpc.codec.CommonDecoder;
import com.matty.rpc.codec.CommonEncoder;
import com.matty.rpc.enumeration.RpcError;
import com.matty.rpc.exception.RpcException;
import com.matty.rpc.hook.ShutdownHook;
import com.matty.rpc.provider.ServiceProvider;
import com.matty.rpc.provider.ServiceProviderImpl;
import com.matty.rpc.register.NacosServiceRegistry;
import com.matty.rpc.register.ServiceRegistry;
import com.matty.rpc.serializer.CommonSerializer;
import com.matty.rpc.transport.RpcServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * ClassName: NettyServer
 * author: Matty Roslak
 * date: 2021/7/19  9:26
 * netty方式服务端
 */
public class NettyServer implements RpcServer {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private final String host;
    private final int port;

    private final ServiceRegistry serviceRegistry;
    private final ServiceProvider serviceProvider;

    private final CommonSerializer serializer;

    public NettyServer(String host, int port) {
        this(host, port, DEFAULT_SERIALIZER);
    }

    public NettyServer(String host, int port, Integer serializerCode) {
        this.host = host;
        this.port = port;
        serviceRegistry = new NacosServiceRegistry();
        serviceProvider = new ServiceProviderImpl();
        serializer = CommonSerializer.getByCode(serializerCode);
    }

    /**
     * @param service, serviceClass
     * @return [void]
     * @description 将服务保存在本地的注册表，同时注册到Nacos
     */
    @Override
    public <T> void publishService(T service, Class<T> serviceClass) {
        if (serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        // 本地服务注册
        serviceProvider.addServiceProvider(service, serviceClass);
        // 注册中心服务注册
        serviceRegistry.register(serviceClass.getCanonicalName(), new InetSocketAddress(host, port));
        start();
    }

    @Override
    public void start() {
        //添加注销服务的钩子，服务端关闭时才会执行
        ShutdownHook.getShutdownHook().addClearAllHook();

        //用于处理客户端新连接的主”线程池“
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //用于连接后处理IO事件的从”线程池“
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //初始化Netty服务端启动器，作为服务端入口
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //将主从“线程池”初始化到启动器中
            serverBootstrap.group(bossGroup, workerGroup)
                    //设置服务端通道类型
                    .channel(NioServerSocketChannel.class)
                    //日志打印方式
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //配置ServerChannel参数，服务端接受连接的最大队列长度，如果队列已满，客户端连接将被拒绝。理解可参考：https://blog.csdn.net/fd2025/article/details/79740226
                    .option(ChannelOption.SO_BACKLOG, 256)
                    //启用该功能时，TCP会主动探测空闲连接的有效性。可以将此功能视为TCP的心跳机制，默认的心跳间隔是7200s即2小时。
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    //配置Channel参数，nodelay没有延迟，true就代表禁用Nagle算法，减小传输延迟。理解可参考：https://blog.csdn.net/lclwjl/article/details/80154565
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    //初始化Handler,设置Handler操作
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //初始化管道
                            ChannelPipeline pipeline = ch.pipeline();
                            //往管道中添加Handler，注意入站Handler与出站Handler都必须按实际执行顺序添加，比如先解码再Server处理，那Decoder()就要放在前面。
                            //但入站和出站Handler之间则互不影响，这里我就是先添加的出站Handler再添加的入站
                            pipeline.addLast(new CommonEncoder(serializer))
                                    .addLast(new CommonDecoder())
                                    .addLast(new NettyServerHandler());
                        }
                    });
            //绑定端口，启动Netty，sync()代表阻塞主Server线程，以执行Netty线程，如果不阻塞Netty就直接被下面shutdown了
            ChannelFuture future = serverBootstrap.bind(host, port).sync();
            //等确定通道关闭了，关闭future回到主Server线程
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("启动服务器时有错误发生", e);
        } finally {
            //优雅关闭Netty服务端且清理掉内存，shutdownGracefully()执行逻辑参考：https://www.icode9.com/content-4-797057.html
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}

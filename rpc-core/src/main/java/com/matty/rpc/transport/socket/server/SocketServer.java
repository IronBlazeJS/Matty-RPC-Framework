package com.matty.rpc.transport.socket.server;

import com.matty.rpc.handler.RequestHandler;
import com.matty.rpc.provider.ServiceProvider;
import com.matty.rpc.provider.ServiceProviderImpl;
import com.matty.rpc.register.NacosServiceRegistry;
import com.matty.rpc.register.ServiceRegistry;
import com.matty.rpc.transport.RpcServer;
import com.matty.rpc.enumeration.RpcError;
import com.matty.rpc.exception.RpcException;

import com.matty.rpc.serializer.CommonSerializer;
import com.matty.rpc.util.ThreadPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * ClassName: RpcServer
 * author: Matty Roslak
 * date: 2021/7/16  21:32
 * Socket方式进行远程调用连接的服务端
 */
public class SocketServer implements RpcServer {

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private final ExecutorService threadPool;
    private RequestHandler requestHandler = new RequestHandler();
    private final String host;
    private final int port;
    private CommonSerializer serializer;

    // 构造方法中创建线程池
    private final ServiceRegistry serviceRegistry;
    private final ServiceProvider serviceProvider;

    public SocketServer(String host, int port){
        this.host = host;
        this.port = port;
        serviceRegistry = new NacosServiceRegistry();
        serviceProvider = new ServiceProviderImpl();

        //创建线程池
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");

    }

    /**
     * 服务保存到本地注册表，同时注册到Nacos
     * @param service
     * @param serviceClass
     * @param <T>
     */
    @Override
    public <T> void publishService(T service, Class<T> serviceClass) {
        if (serializer == null){
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        serviceProvider.addServiceProvider(service, serviceClass);
        serviceRegistry.register(serviceClass.getCanonicalName(), new InetSocketAddress(host, port));
        start();
    }

    /**
     * 服务端启动
     */
    @Override
    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            logger.info("服务器启动……");
            Socket socket;
            //当未接收到连接请求时，accept()会一直阻塞
            while ((socket = serverSocket.accept()) != null){
                logger.info("客户端连接！{}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serializer));
            }
            threadPool.shutdown();
        }catch (IOException e){
            logger.info("服务器启动时有错误发生：" + e);
        }
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}

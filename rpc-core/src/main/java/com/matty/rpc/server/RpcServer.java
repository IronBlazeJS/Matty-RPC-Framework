package com.matty.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * ClassName: RpcServer
 * author: Matty Roslak
 * date: 2021/7/16  21:32
 * RPC服务端
 */
public class RpcServer {

    private final ExecutorService threadPool;
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    // 构造方法中创建线程池
    public RpcServer() {
        int corePoolSize = 5;
        int maximumPoolSize = 50;
        long keepAliveTime = 60;

        // 设置上限为100线程的阻塞队列
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        // 创建线程池实例  想想七大参数 最后的拒绝策略默认，所以这里写了6个
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workingQueue);

    }
    // 利用ServerSocket监听与客户端发出请求的一致端口，连接到客户端Socket，循环接收请求。
    public void register(Object service, int port) {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("服务器正在启动……");
            Socket socket;
            //当未接收到连接请求时，accept()会一直阻塞，也就是说不会执行判断
            while ((socket = serverSocket.accept()) != null) {
                logger.info("客户端连接！IP：" + socket.getInetAddress());
                threadPool.execute(new WorkerThread(socket, service));
            }
        }catch (IOException e) {
            logger.info("连接时有错误发生：" + e);
        }
    }
}

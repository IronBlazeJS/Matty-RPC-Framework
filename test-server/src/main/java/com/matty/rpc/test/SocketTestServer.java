package com.matty.rpc.test;

import com.matty.rpc.api.HelloService;
import com.matty.rpc.registry.DefaultServiceRegistry;
import com.matty.rpc.registry.ServiceRegistry;
import com.matty.rpc.socket.server.SocketServer;

/**
 * ClassName: TestServer
 * author: Matty Roslak
 * date: 2021/7/16  22:07
 * 测试用服务端
 */
public class SocketTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        // 注册服务
        serviceRegistry.register(helloService);
        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.start(9000);
    }
}

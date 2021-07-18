package com.matty.rpc.test;

import com.matty.rpc.api.HelloService;
import com.matty.rpc.registry.DefaultServiceRegistry;
import com.matty.rpc.registry.ServiceRegistry;
import com.matty.rpc.server.RpcServer;

/**
 * ClassName: TestServer
 * author: Matty Roslak
 * date: 2021/7/16  22:07
 * 测试用服务端
 */
public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        // 注册服务
        serviceRegistry.register(helloService);
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000);
    }
}

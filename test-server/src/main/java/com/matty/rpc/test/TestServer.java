package com.matty.rpc.test;

import com.matty.rpc.api.HelloService;
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
        RpcServer rpcServer = new RpcServer();
        //注册HelloServiceImpl服务
        rpcServer.register(helloService, 9000);
    }
}

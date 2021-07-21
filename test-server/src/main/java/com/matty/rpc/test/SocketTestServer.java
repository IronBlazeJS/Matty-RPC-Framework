package com.matty.rpc.test;

import com.matty.rpc.api.HelloService;
import com.matty.rpc.serializer.CommonSerializer;
import com.matty.rpc.transport.socket.server.SocketServer;

/**
 * ClassName: TestServer
 * author: Matty Roslak
 * date: 2021/7/16  22:07
 * 测试用Socket服务端
 */
public class SocketTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl2();
        SocketServer socketServer = new SocketServer("127.0.0.1", 9998, CommonSerializer.HESSIAN_SERIALIZER);
        socketServer.publishService(helloService, HelloService.class);
    }
}

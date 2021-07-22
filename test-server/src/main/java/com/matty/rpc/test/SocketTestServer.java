package com.matty.rpc.test;

import com.matty.rpc.annotation.ServiceScan;
import com.matty.rpc.serializer.CommonSerializer;
import com.matty.rpc.transport.RpcServer;
import com.matty.rpc.transport.socket.server.SocketServer;

/**
 * ClassName: TestServer
 * author: Matty Roslak
 * date: 2021/7/16  22:07
 * 测试用Socket服务端
 */
@ServiceScan
public class SocketTestServer {
    public static void main(String[] args) {
        RpcServer server = new SocketServer("127.0.0.1", 9998, CommonSerializer.HESSIAN_SERIALIZER);
        server.start();
    }
}

package com.matty.rpc.test;

import com.matty.rpc.api.HelloService;
import com.matty.rpc.transport.netty.server.NettyServer;
import com.matty.rpc.serializer.KryoSerializer;

/**
 * ClassName: NettyTestServer
 * author: Matty Roslak
 * date: 2021/7/19  16:53
 * 测试用Netty服务端
 */
public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        NettyServer server = new NettyServer("127.0.0.1", 9999);
        server.setSerializer(new KryoSerializer());
        server.publishService(helloService, HelloService.class);
    }
}

package com.matty.rpc.test;

import com.matty.rpc.api.HelloService;
import com.matty.rpc.netty.server.NettyServer;
import com.matty.rpc.registry.DefaultServiceRegistry;
import com.matty.rpc.registry.ServiceRegistry;
import com.matty.rpc.serializer.KryoSerializer;

/**
 * ClassName: NettyTestServer
 * author: Matty Roslak
 * date: 2021/7/19  16:53
 */
public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.setSerializer(new KryoSerializer());
        server.start(9999);
    }
}

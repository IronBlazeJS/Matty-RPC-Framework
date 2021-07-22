package com.matty.rpc.test;

import com.matty.rpc.annotation.ServiceScan;
import com.matty.rpc.serializer.CommonSerializer;
import com.matty.rpc.transport.RpcServer;
import com.matty.rpc.transport.netty.server.NettyServer;

/**
 * ClassName: NettyTestServer
 * author: Matty Roslak
 * date: 2021/7/19  16:53
 * 测试用Netty服务端
 */
@ServiceScan
public class NettyTestServer {
    public static void main(String[] args) {
        RpcServer server = new NettyServer("127.0.0.1", 9999, CommonSerializer.PROTOBUF_SERIALIZER);
        server.start();
    }
}

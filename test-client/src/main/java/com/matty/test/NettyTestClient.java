package com.matty.test;

import com.matty.rpc.RpcClient;
import com.matty.rpc.RpcClientProxy;
import com.matty.rpc.api.HelloObject;
import com.matty.rpc.api.HelloService;
import com.matty.rpc.netty.client.NettyClient;

/**
 * ClassName: NettyTestClient
 * author: Matty Roslak
 * date: 2021/7/19  16:52
 * 测试用Netty客户端
 */
public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "this is netty style");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}

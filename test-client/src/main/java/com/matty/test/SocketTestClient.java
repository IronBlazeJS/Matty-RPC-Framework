package com.matty.test;

import com.matty.rpc.api.HelloObject;
import com.matty.rpc.api.HelloService;
import com.matty.rpc.transport.RpcClientProxy;
import com.matty.rpc.serializer.KryoSerializer;
import com.matty.rpc.transport.socket.client.SocketClient;

/**
 * ClassName: TestClient
 * author: Matty Roslak
 * date: 2021/7/16  22:11
 * 测试用Socket客户端
 */
public class SocketTestClient {
    public static void main(String[] args) {
        SocketClient client = new SocketClient();
        client.setSerializer(new KryoSerializer());
        //接口与代理对象之间的中介对象
        RpcClientProxy proxy = new RpcClientProxy(client);
        //创建代理对象
        HelloService helloService = proxy.getProxy(HelloService.class);
        //接口方法的参数对象
        HelloObject object = new HelloObject(12, "This is a test message MATTY");
        //由动态代理可知，代理对象调用hello()实际会执行invoke()
        String res = helloService.hello(object);
        System.out.println(res);
    }
}

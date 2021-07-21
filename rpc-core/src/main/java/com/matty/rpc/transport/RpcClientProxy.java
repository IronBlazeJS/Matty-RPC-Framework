package com.matty.rpc.transport;

import com.matty.rpc.entity.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * ClassName: RpcClientProxy
 * author: Matty Roslak
 * date: 2021/7/15  23:27
 */
public class RpcClientProxy implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(RpcClientProxy.class);

    private final RpcClient client;

    public RpcClientProxy(RpcClient client) {
        this.client = client;
    }

    // 创建proxy对象实例
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    // 生成RpcRequest对象，然后调用客户端的send方法把它发出去，最后拿到结果
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        logger.info("调用方法：{}#{}", method.getDeclaringClass().getName(), method.getName());

        RpcRequest rpcRequest = new RpcRequest(UUID.randomUUID().toString(), method.getDeclaringClass().getName(), method.getName(), args, method.getParameterTypes(), false);
        return client.sendRequest(rpcRequest);
    }
}

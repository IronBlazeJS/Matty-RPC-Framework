package com.matty.rpc.transport;

import com.matty.rpc.serializer.CommonSerializer;

/**
 * ClassName: RpcServer
 * author: Matty Roslak
 * date: 2021/7/19  9:08
 * 服务端类通用接口
 */
public interface RpcServer {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    void start();

    /**
     * 向Nacos注册服务
     *
     * @param service
     * @param serviceClass
     * @param <T>
     */
    <T> void publishService(T service, Class<T> serviceClass);

}

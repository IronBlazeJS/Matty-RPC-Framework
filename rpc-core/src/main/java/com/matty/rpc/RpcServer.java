package com.matty.rpc;

import com.matty.rpc.serializer.CommonSerializer;

/**
 * ClassName: RpcServer
 * author: Matty Roslak
 * date: 2021/7/19  9:08
 * 服务端类通用接口
 */
public interface RpcServer {

    void start(int port);

    void setSerializer(CommonSerializer serializer);

}

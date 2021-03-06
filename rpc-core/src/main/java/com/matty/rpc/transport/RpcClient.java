package com.matty.rpc.transport;

import com.matty.rpc.entity.RpcRequest;
import com.matty.rpc.serializer.CommonSerializer;

/**
 * ClassName: RpcClient
 * author: Matty Roslak
 * date: 2021/7/19  9:03
 * 客户端类通用接口
 */
public interface RpcClient {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    Object sendRequest(RpcRequest rpcRequest);


}

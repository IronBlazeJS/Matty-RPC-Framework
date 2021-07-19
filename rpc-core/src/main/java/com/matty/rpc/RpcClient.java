package com.matty.rpc;

import com.matty.rpc.entity.RpcRequest;
import com.matty.rpc.serializer.CommonSerializer;

/**
 * ClassName: RpcClient
 * author: Matty Roslak
 * date: 2021/7/19  9:03
 * 客户端类通用接口
 */
public interface RpcClient {

    Object sendRequest(RpcRequest rpcRequest);

    void setSerializer(CommonSerializer serializer);

}

package com.matty.rpc.util;

import com.matty.rpc.entity.RpcRequest;
import com.matty.rpc.entity.RpcResponse;
import com.matty.rpc.enumeration.ResponseCode;
import com.matty.rpc.enumeration.RpcError;
import com.matty.rpc.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: RpcMessageChecker
 * author: Matty Roslak
 * date: 2021/7/20  9:13
 * 检查响应和请求的id
 */
public class RpcMessageChecker {

    private static final String INTERFACE_NAME = "interfaceName";
    private static final Logger logger = LoggerFactory.getLogger(RpcMessageChecker.class);

    private RpcMessageChecker() {
    }

    public static void check(RpcRequest rpcRequest, RpcResponse rpcResponse) {
        if (rpcResponse == null) {
            logger.error("调用服务失败，serviceName:{}", rpcRequest.getInterfaceName());
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }
        //响应与请求的请求号不同
        if (!rpcRequest.getRequestId().equals(rpcResponse.getRequestId())) {
            throw new RpcException(RpcError.RESPONSE_NOT_MATCH, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }
        //调用失败
        if (rpcResponse.getStatusCode() == null || !rpcResponse.getStatusCode().equals(ResponseCode.SUCCESS.getCode())) {
            logger.error("调用服务失败，serviceName:{}，RpcResponse:{}", rpcRequest.getInterfaceName(), rpcResponse);
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }
    }
}

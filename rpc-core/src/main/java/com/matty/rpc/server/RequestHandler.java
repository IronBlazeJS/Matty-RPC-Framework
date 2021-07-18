package com.matty.rpc.server;

import com.matty.rpc.entity.RpcRequest;
import com.matty.rpc.entity.RpcResponse;
import com.matty.rpc.enumeration.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * ClassName: WorkerThread
 * author: Matty Roslak
 * date: 2021/7/16  21:51
 * 具体的方法调用处理器
 */
public class RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public Object handle(RpcRequest rpcRequest, Object service) {
        Object result = null;
        try {
            result = invokeTargetMethod(rpcRequest, service);
            logger.info("服务：{}成功调用方法：{}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.info("调用或发送时有错误发生：" + e);
        }
        return result;
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) throws InvocationTargetException, IllegalAccessException {
        Method method;
        try {
            //getClass()获取的是实例对象的类型
            // 先获取方法method对象
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND);
        }
        // 再具体调用method对象对应的方法，并返回
        return method.invoke(service, rpcRequest.getParameters());
    }

}

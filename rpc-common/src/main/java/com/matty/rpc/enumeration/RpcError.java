package com.matty.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName: RpcError
 * author: Matty Roslak
 * date: 2021/7/18  19:03
 * Rpc调用过程中出现的错误
 */
@AllArgsConstructor
@Getter
public enum RpcError {

    SERVICE_INVOCATION_FAILURE("服务调用出现失败"),
    SERVICE_CAN_NOT_BE_NULL("注册的服务不能为空"),
    SERVICE_NOT_FOUND("找不到对应的服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务未实现接口"),
    UNKNOWN_PROTOCOL("不识别的协议包"),
    UNKNOWN_SERIALIZER("不识别的(反)序列化器"),
    UNKNOWN_PACKAGE_TYPE("不识别的数据包类型"),
    SERIALIZER_NOT_FOUND("找不到序列化器");

    private final String message;
}

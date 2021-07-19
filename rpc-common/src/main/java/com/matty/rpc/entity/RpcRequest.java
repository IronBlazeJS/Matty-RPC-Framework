package com.matty.rpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: RpcRequest
 * author: Matty Roslak
 * date: 2021/7/12  0:10
 * 传输格式（传输协议）：客户端向服务端传输的对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcRequest implements Serializable {
    /**
     * 待调用接口名称
     */
    private String interfaceName;
    /**
     * 待调用方法名称
     */
    private String methodName;
    /**
     * 待调用方法的参数
     */
    private Object[] parameters;
    /**
     * 待调用方法的参数类型
     */
    private Class<?>[] paramTypes;
}

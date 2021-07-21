package com.matty.rpc.exception;

import com.matty.rpc.enumeration.RpcError;

/**
 * ClassName: RpcException
 * author: Matty Roslak
 * date: 2021/7/18  19:04
 * Rpc调用异常
 */
public class RpcException extends RuntimeException {

    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ":" + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());
    }
}

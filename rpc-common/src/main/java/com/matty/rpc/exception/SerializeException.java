package com.matty.rpc.exception;

/**
 * ClassName: SerializeException
 * author: Matty Roslak
 * date: 2021/7/19  19:52
 * 序列化异常
 */
public class SerializeException extends RuntimeException {
    public SerializeException(String msg) {
        super(msg);
    }
}

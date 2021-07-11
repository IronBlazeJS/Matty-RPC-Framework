package com.matty.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName: ResponseCode
 * author: Matty Roslak
 * date: 2021/7/12  0:16
 * 响应状态码对象
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {
    SUCCESS(200,"调用方法成功"),
    FAIL(500,"调用方法失败"),
    NOT_FOUND_METHOD(501,"未找到指定方法"),
    NOT_FOUND_CLASS(502,"未找到指定类");

    private final int code;
    private final String message;
}

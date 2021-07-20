package com.matty.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName: SerializerCode
 * author: Matty Roslak
 * date: 2021/7/19  15:55
 */
@AllArgsConstructor
@Getter
public enum SerializerCode {

    KRYO(0),
    JSON(1),
    HESSIAN(2),
    PROTOBUF(3);

    private final int code;
}

package com.matty.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName: PackageType
 * author: Matty Roslak
 * date: 2021/7/19  16:26
 * 标识包的类型
 */
@Getter
@AllArgsConstructor
public enum PackageType {

    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;
}

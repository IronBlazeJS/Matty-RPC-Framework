package com.matty.rpc.entity;

import com.matty.rpc.enumeration.ResponseCode;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: RpcResponse
 * author: Matty Roslak
 * date: 2021/7/12  0:13
 * 服务端处理完后，向客户端返回的对象
 */
@Data
public class RpcResponse<T> implements Serializable {
    /**
     *响应状态码
     */
    private Integer statusCode;
    /**
     *响应状态码对应的信息
     */
    private String message;
    /**
     *成功时的响应数据
     */
    private T data;

    /**
     * 成功时服务端返回的对象
     * @param data
     * @param <T>
     * @return
     */
    public static <T> RpcResponse<T> success(T data){
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    /**
     * 失败时服务端返回的对象
     * @param code
     * @param <T>
     * @return
     */
    public static <T> RpcResponse<T> fail(ResponseCode code){
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }
}

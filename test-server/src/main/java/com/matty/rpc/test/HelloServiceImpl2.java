package com.matty.rpc.test;

import com.matty.rpc.api.HelloObject;
import com.matty.rpc.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: HelloServiceImpl2
 * author: Matty Roslak
 * date: 2021/7/21  15:41
 * 服务端api接口实现  socket
 */
public class HelloServiceImpl2 implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl2.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("接收到消息：{}", object.getMessage());
        return "这是调用的返回值：id= " + object.getId() + " 本次处理来自Socket服务端";

    }
}

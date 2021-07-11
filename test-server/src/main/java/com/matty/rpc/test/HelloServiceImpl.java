package com.matty.rpc.test;

import com.matty.rpc.api.HelloObject;
import com.matty.rpc.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: HelloServiceImpl
 * author: Matty Roslak
 * date: 2021/7/11  23:59
 * 服务端api接口实现
 */
public class HelloServiceImpl implements HelloService {

    /**
     * 使用HelloServiceImpl初始化日志对象，方便在日志输出的时候，可以打印出日志信息所属的类。
     */
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        return null;
    }
}

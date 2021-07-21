package com.matty.rpc.register;

import java.net.InetSocketAddress;

/**
 * ClassName: ServiceRegistry
 * author: Matty Roslak
 * date: 2021/7/20  16:23
 * 注册中心注册接口
 */
public interface ServiceRegistry {

    /**
     * @param serviceName, inetSocketAddress 服务名称，提供服务的地址
     * @return [void]
     * @description 将一个服务注册到注册表
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);

}

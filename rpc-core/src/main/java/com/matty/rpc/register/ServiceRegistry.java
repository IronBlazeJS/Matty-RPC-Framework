package com.matty.rpc.register;

import java.net.InetSocketAddress;

/**
 * ClassName: ServiceRegistry
 * author: Matty Roslak
 * date: 2021/7/20  16:23
 * 服务注册中心通用接口
 */
public interface ServiceRegistry {

    /**
     * @description 将一个服务注册到注册表
     * @param serviceName, inetSocketAddress 服务名称，提供服务的地址
     * @return [void]
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);

    /**
     * @description 根据服务名查找服务实体
     * @param serviceName
     * @return [java.net.InetSocketAddress] 服务实体
     */
    InetSocketAddress lookupService(String serviceName);

}

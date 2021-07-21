package com.matty.rpc.register;

import java.net.InetSocketAddress;

/**
 * ClassName: ServiceDiscovery
 * author: Matty Roslak
 * date: 2021/7/21  15:21
 * 查找服务接口
 */
public interface ServiceDiscovery {

    /**
     * @param serviceName
     * @return [java.net.InetSocketAddress]
     * @description 根据服务名称查找服务端地址
     */
    InetSocketAddress lookupService(String serviceName);
}

package com.matty.rpc.registry;

/**
 * ClassName: ServiceRegistry
 * author: Matty Roslak
 * date: 2021/7/18  21:14
 * 服务注册的通用接口（容器）
 */
public interface ServiceRegistry {

    /**
     * 将一个服务注册进注册表
     * @param service 待注册的服务实体对象
     * @param <T> 服务实体的类型
     */
    <T> void register(T service);

    /**
     * 根据服务名获取服务对象
     * @param serviceName
     * @return 服务对象
     */
    Object getService(String serviceName);

}

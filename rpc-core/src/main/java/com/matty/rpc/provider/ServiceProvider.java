package com.matty.rpc.provider;

/**
 * ClassName: ServiceProvider
 * author: Matty Roslak
 * date: 2021/7/20  16:43
 * 保存和提供服务实例对象
 */
public interface ServiceProvider {

    /**
     * 将一个服务注册进注册表
     * @param service 待注册的服务实体对象
     * @param <T> 服务实体的类型
     */
    <T> void addServiceProvider(T service, Class<T> serviceClass);

    /**
     * 根据服务名获取服务对象
     * @param serviceName
     * @return 服务对象
     */
    Object getServiceProvider(String serviceName);
}

package com.matty.rpc.provider;

/**
 * ClassName: ServiceProvider
 * author: Matty Roslak
 * date: 2021/7/20  16:43
 * 保存和提供服务实例对象
 */
public interface ServiceProvider {

    /**
     * 将一个服务注册进本地注册表
     *
     * @param service
     * @param serviceName
     * @param <T>
     */
    <T> void addServiceProvider(T service, String serviceName);

    /**
     * 根据服务名获取服务对象
     *
     * @param serviceName
     * @return 服务对象
     */
    Object getServiceProvider(String serviceName);
}

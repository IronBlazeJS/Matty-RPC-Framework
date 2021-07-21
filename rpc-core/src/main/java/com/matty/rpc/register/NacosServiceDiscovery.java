package com.matty.rpc.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.matty.rpc.util.NacosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * ClassName: NacosServiceDiscovery
 * author: Matty Roslak
 * date: 2021/7/21  15:22
 * 查找服务
 */
public class NacosServiceDiscovery implements ServiceDiscovery{

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceDiscovery.class);
    private final NamingService namingService;

    public NacosServiceDiscovery(){
        namingService = NacosUtil.getNacosNamingService();
    }

    /**
     * @description 根据服务名称从注册中心获取到一个服务提供者的地址
     * @param serviceName
     * @return [java.net.InetSocketAddress]
     */
    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            //利用列表获取某个服务的所有提供者
            List<Instance> instances = NacosUtil.getAllInstance(namingService, serviceName);
            Instance instance = instances.get(0);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        }catch (NacosException e) {
            logger.error("获取服务时有错误发生", e);
        }
        return null;
    }
}

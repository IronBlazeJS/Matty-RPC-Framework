package com.matty.rpc.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.matty.rpc.enumeration.RpcError;
import com.matty.rpc.exception.RpcException;
import com.matty.rpc.loadbalancer.LoadBalancer;
import com.matty.rpc.loadbalancer.RandomLoadBalancer;
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
public class NacosServiceDiscovery implements ServiceDiscovery {

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceDiscovery.class);

    private final LoadBalancer loadBalancer;

    // 负载均衡方式选择
    public NacosServiceDiscovery(LoadBalancer loadBalancer) {
        if (loadBalancer == null) {
            this.loadBalancer = new RandomLoadBalancer();
        } else {
            this.loadBalancer = loadBalancer;
        }
    }

    /**
     * @param serviceName
     * @return [java.net.InetSocketAddress]
     * @description 根据服务名称从注册中心获取到一个服务提供者的地址
     */
    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            //利用列表获取某个服务的所有提供者
            List<Instance> instances = NacosUtil.getAllInstance(serviceName);
            if (instances.size() == 0) {
                logger.error("找不到对应服务：" + serviceName);
                throw new RpcException(RpcError.SERVICE_NOT_FOUND);
            }
            //负载均衡获取一个服务实体
            Instance instance = loadBalancer.select(instances);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            logger.error("获取服务时有错误发生", e);
        }
        return null;
    }
}

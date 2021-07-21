package com.matty.rpc.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.matty.rpc.enumeration.RpcError;
import com.matty.rpc.exception.RpcException;
import com.matty.rpc.util.NacosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * ClassName: NacosServiceRegistry
 * author: Matty Roslak
 * date: 2021/7/20  16:29
 * Nacos服务注册中心
 */
public class NacosServiceRegistry implements ServiceRegistry {

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);

    /**
     * @param serviceName, inetSocketAddress
     * @return [void]
     * @description 将服务的名称和地址注册进服务注册中心
     */
    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            //向Nacos注册服务
            NacosUtil.registerService(serviceName, inetSocketAddress);
        } catch (NacosException e) {
            logger.error("注册服务时有错误发生" + e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }
}

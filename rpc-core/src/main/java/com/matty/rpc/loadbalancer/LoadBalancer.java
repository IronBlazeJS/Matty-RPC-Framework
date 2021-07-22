package com.matty.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * ClassName: LoadBalancer
 * author: Matty Roslak
 * date: 2021/7/22  10:41
 * 负载均衡接口
 */
public interface LoadBalancer {

    /**
     * @param instances
     * @return [com.alibaba.nacos.api.naming.pojo.Instance]
     * @description 从一系列Instance中选择一个
     */
    Instance select(List<Instance> instances);
}

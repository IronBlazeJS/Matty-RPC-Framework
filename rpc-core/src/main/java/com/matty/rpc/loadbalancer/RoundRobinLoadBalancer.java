package com.matty.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * ClassName: RoundRobinLoadBalancer
 * author: Matty Roslak
 * date: 2021/7/22  10:44
 * 轮询实现负载均衡
 */
public class RoundRobinLoadBalancer implements LoadBalancer {

    /**
     * index表示当前选到了第几个服务器，并且每次选择后都会自增一
     */
    private int index = 0;

    @Override
    public Instance select(List<Instance> instances) {
        // 取模保证index不会超出范围
        if (index >= instances.size()) {
            index %= instances.size();
        }
        return instances.get(index++);
    }
}

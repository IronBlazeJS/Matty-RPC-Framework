package com.matty.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;
import java.util.Random;

/**
 * ClassName: RandomLoadBalancer
 * author: Matty Roslak
 * date: 2021/7/22  10:43
 * 随机算法进行负载均衡
 */
public class RandomLoadBalancer implements LoadBalancer {

    @Override
    public Instance select(List<Instance> instances) {
        return instances.get(new Random().nextInt(instances.size()));
    }
}

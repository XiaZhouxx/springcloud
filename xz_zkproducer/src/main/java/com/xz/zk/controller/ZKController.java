package com.xz.zk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xz
 * @ClassName ZKController
 * @Description
 * @date 2020/3/14 0014 19:09
 **/
@RestController
public class ZKController {
    @Autowired
    DiscoveryClient client;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    @LoadBalanced
    RestTemplate template;

    @Value("${spring.application.name}")
    String applicationName;

    @RequestMapping("/currentInfo")
    public List<String> CurrentInfo() {
        System.out.println(client.getServices());
        return client.getInstances("services")
                .stream()
                .map(s -> s.getHost() + ":" + s.getPort())
                .collect(Collectors.toList());
    }

    /**
     * 拉取Zookeeper中的所有注册信息 负载均衡中就可能采用定时拉取
     * @return
     */
    @RequestMapping("/discovery")
    public Map<String, List<String>> discoveryInfo() {
        Map<String, List<String>> discoveryInfo = new HashMap<>();
        client.getServices().forEach(app -> {
            List<String> instance = client.getInstances(app)
                    .stream()
                    .map(s -> s.getHost() + ":" + s.getPort())
                    .collect(Collectors.toList());
            discoveryInfo.put(app, instance);
        });
        return discoveryInfo;
    }


}

package com.xz.zk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
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
    @LoadBalanced
    RestTemplate template;

    @Autowired
    RestTemplate myRestTemplate;


    @Value("${spring.application.name}")
    String applicationName;

    @RequestMapping("/discovery")
    public List<String> discoveryInfo() {
        return client.getInstances(applicationName)
                .stream()
                .map(s -> s.getHost() + ":" + s.getPort())
                .collect(Collectors.toList());
    }
}

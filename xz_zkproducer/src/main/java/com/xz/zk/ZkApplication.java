package com.xz.zk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author xz
 * @Description SpringCloud 基于Zookeeper 注册注册
 * @date 2020/3/14 0014 17:58
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class ZkApplication {


    public static void main(String[] args) {
        SpringApplication.run(ZkApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RestTemplate myRestTemplate() {
        return new RestTemplate();
    }
}

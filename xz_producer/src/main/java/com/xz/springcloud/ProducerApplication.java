package com.xz.springcloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * @author xz
 * @ClassName ProducerApplication
 * @Description
 * @date 2019/5/11 0011 20:32
 **/
@EnableHystrix
@EnableDiscoveryClient // 尽可能使用该注解可以兼容大多数的注册中心
@SpringBootApplication
@MapperScan("com.xz.springcloud.mapper")
@EnableRedisRepositories
// 扫描@WebFilter 过滤器
@ServletComponentScan
public class ProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class,args);
    }


}

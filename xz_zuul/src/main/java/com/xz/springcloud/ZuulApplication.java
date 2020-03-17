package com.xz.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;

/**
 * @author xz
 * @ClassName ZuulApplication
 * @Description
 * @date 2019/5/13 0013 16:37
 **/
@EnableZuulProxy
@SpringBootApplication
public class ZuulApplication {

    /**
     * 自定义服务与路由的映射关系
     * 因为zuul中默认使用的是 http:/zuul-ip:zuul-port/微服务名/** 作为路由
     * 如果我们使用的是service-v1 并且路由映射想设置为 /service/v1/** 就需要自定义
     * @return
     */
    @Bean
    public PatternServiceRouteMapper patternServiceRouteMapper() {
        return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)","${name}/${version}");
    }

    public static void main(String[] args) {

        SpringApplication.run(ZuulApplication.class,args);
    }
}




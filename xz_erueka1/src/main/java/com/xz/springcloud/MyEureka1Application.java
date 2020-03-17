package com.xz.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author xz
 * @ClassName MyEureka1Application
 * @Description
 * @date 2019/5/13 0013 14:35
 **/
@EnableEurekaServer
@SpringBootApplication
public class MyEureka1Application {
    public static void main(String[] args) {
        SpringApplication.run(MyEureka1Application.class,args);
    }
}

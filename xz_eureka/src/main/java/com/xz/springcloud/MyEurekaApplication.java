package com.xz.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author xz
 * @ClassName MyEurekaApplication
 * @Description
 * @date 2019/5/11 0011 19:51
 **/
@EnableEurekaServer
@SpringBootApplication
public class MyEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyEurekaApplication.class,args);
    }
}

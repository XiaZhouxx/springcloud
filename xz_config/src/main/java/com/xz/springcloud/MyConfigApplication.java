package com.xz.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author xz
 * @ClassName MyConfigApplication
 * @Description
 * @date 2019/5/13 0013 19:27
 **/
@EnableConfigServer
@SpringBootApplication
public class MyConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyConfigApplication.class,args);
    }
}

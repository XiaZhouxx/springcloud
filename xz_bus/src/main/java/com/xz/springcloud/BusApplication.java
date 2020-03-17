package com.xz.springcloud;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xz
 * @ClassName BusApplication
 * @Description
 * @date 2019/5/26 0026 9:30
 **/
@SpringBootApplication
@EnableRabbit
public class BusApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusApplication.class,args);
    }
}

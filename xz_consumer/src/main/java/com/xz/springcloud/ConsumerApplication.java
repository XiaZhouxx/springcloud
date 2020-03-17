package com.xz.springcloud;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author xz
 * @ClassName ConsumerApplication
 * @Description
 * @date 2019/5/13 0013 15:43
 **/
@EnableCircuitBreaker
@EnableFeignClients
@SpringBootApplication
public class ConsumerApplication {

    /**
     * 打印Feign调用的详细日志
     * @return
     */
    @Bean
    public Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }
}

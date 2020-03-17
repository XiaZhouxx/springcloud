package com.xz.oauth.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author xz
 * @ClassName OAuth2ResouceApplication
 * @Description
 * @date 2020/2/14 0014 19:03
 **/
@SpringBootApplication
@MapperScan("com.xz.oauth.resource.mapper")
public class OAuth2ResourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OAuth2ResourceApplication.class, args);
    }
}

package com.xz.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author xz
 * @ClassName OAuth2ServerApplication
 * @Description
 * @date 2020/2/13 0013 16:13
 **/
@SpringBootApplication
@MapperScan("com.xz.oauth.server.mapper")
public class OAuth2ServerApplication {
    public static void main(String[] args) {

        SpringApplication.run(OAuth2ServerApplication.class, args);
    }

}

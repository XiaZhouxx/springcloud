package com.xz.oauth.resource.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author xz
 * @ClassName TokenStoreConfig
 * @Description 配置 Jwt 方式 资源服务器自己解析token
 * @date 2020/2/18 0018 14:54
 **/
@Configuration
public class TokenConfig {

    /**
     * 实际上不用配置了，通过yml 文件中配置 秘钥即可
     */
   /* @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }*/

}

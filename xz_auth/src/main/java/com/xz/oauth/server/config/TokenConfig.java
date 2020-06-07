package com.xz.oauth.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.Date;
import java.util.Map;

/**
 * @author xz
 * @Description Token 配置
 * @date 2020/6/2 14:36
 **/
@Configuration
public class TokenConfig {
    /**
     * JWT密钥
     */
    private final static String SIGNING_KEY = "test";

    /**
     * JWT 令牌转换器
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwt = new JwtAccessTokenConverter(){
            long timeout = 30 * 60 * 1000;

            @Override
            protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                // 自定义过期时间、自定义信息等
                long new_timeout = System.currentTimeMillis() + timeout;
                DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
                token.setExpiration(new Date(new_timeout));

                return super.encode(accessToken, authentication);
            }

            @Override
            protected Map<String, Object> decode(String token) {
                // JWT续约 -- 因为每次使用都会解析JWT 也说明用户活跃使用则对其续约
                Map<String, Object> decode = super.decode(token);
                // 过期时间
                if(decode.containsKey("exp")) {
                    long new_timeout = System.currentTimeMillis() + timeout;
                    decode.put("exp", new_timeout / 1000);
                }
                return decode;
            }
        };
        jwt.setSigningKey(SIGNING_KEY);
        return jwt;
    }

    /**
     * 配置 token 如何生成 <br/>
     * 1. 默认 InMemoryTokenStore 基于内存存储 <br/>
     * 2.     JdbcTokenStore 基于数据库存储 <br/>
     * 3.     JwtTokenStore 使用 JWT 存储 该方式可以让资源服务器自己校验令牌的有效性
     * 而不必远程连接认证服务器再进行认证。<br/>
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter()){
            @Override
            public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
                // 对于JWT 方法是不需要存储的，所以如果有需要存储可以在改方法实现
                super.storeAccessToken(token, authentication);
            }
        };
    }

    @Bean
    @Primary
    public TokenStore tokenStore(DataSource dataSource) {
        return new JdbcTokenStore(dataSource);
    }


}

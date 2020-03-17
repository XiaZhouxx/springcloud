package com.xz.oauth.server.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

/**
 * @author xz
 * @ClassName OAuth2ServerConfig
 * @Description OAuth 认证服务器配置
 * @date 2020/2/13 0013 16:14
 **/
@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserDetailServiceImpl userDetailService;
    /**
     * 密码模式需要添加管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * JWT秘钥
     */
    private final static String SIGNING_KEY = "test";

    /**
     * 因为使用的是 hikari 数据源 如果不指定会和Spring自身的冲突
     * @return
     */
    @Bean
    @Primary // 使用时优先使用该实现
    @ConfigurationProperties("spring.datasource")
    public HikariDataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    /**
     * JWT 令牌转换器
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwt = new JwtAccessTokenConverter(){
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                // 自定义附加信息 key - value map
                //((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(new HashMap<>());
                DefaultOAuth2AccessToken enhance = (DefaultOAuth2AccessToken) super.enhance(accessToken, authentication);
                System.out.println(enhance.getValue());
                return enhance;
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
     * <a href="www.baidu.com">百度一下</a>
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter()){
            @Override
            public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
                // 自定义令牌存储 例如存入Redis中
                super.storeAccessToken(token, authentication);
            }
        };
        //return new JdbcTokenStore(dataSource());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .passwordEncoder(passwordEncoder)
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Bean
    public ClientDetailsService jdbcClientDetails() {
        return new JdbcClientDetailsService(dataSource());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
       /* clients
                .inMemory() // 基于内存存储令牌
                .withClient("client") // client
                .secret(passwordEncoder.encode("secret"))
                .authorizedGrantTypes("authorization_code") // 认证模式 授权码
                .scopes("app") // 客户端能使用的范围
                .accessTokenValiditySeconds(60 * 60 * 24 * 30) // 令牌有效期 秒
                .redirectUris("www.baidu.com"); // 回调授权码 url*/
        // 基于数据库存储 令牌默认有效 12个小时
        clients.withClientDetails(jdbcClientDetails());
    }


    /**
     * 配置令牌
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore());
        // 设置令牌增强 JWT 转换
        TokenEnhancerChain enhancer = new TokenEnhancerChain();
        enhancer.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter()));
        endpoints.tokenEnhancer(enhancer)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailService);
    }
}

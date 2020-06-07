package com.xz.oauth.server.config;

import com.xz.oauth.server.exception.GlobalOauth2ExceptionTranslator;
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
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;

/**
 * @author xz
 * @Description OAuth 认证服务器配置
 * @date 2020/2/13 0013 16:14
 **/
@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {
    /** 密码加密编码器 */
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserDetailServiceImpl userDetailService;

    /** token的存储 */
    @Autowired
    TokenStore tokenStore;

    /** jwt 转换器 */
    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 密码模式需要添加管理器
     */
    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * 因为使用的是 hikari 数据源 如果不指定会和Spring自身的冲突
     * @return
     */
    @Bean
    @Primary // 注入时优先使用该实现
    @ConfigurationProperties("spring.datasource")
    public HikariDataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public ClientDetailsService clientDetailsService() {

        JdbcClientDetailsService service = new JdbcClientDetailsService(dataSource());
        return service;
    }

    /**
     * 用户授权操作记录 - 授权码模式才用
     * @author xz
     * @date 2020/6/4
     */
//    @Bean
//    public UserApprovalHandler userApprovalHandler() {
//        ApprovalStoreUserApprovalHandler userApprovalHandler = new ApprovalStoreUserApprovalHandler();
//        userApprovalHandler.setApprovalStore(approvalStore());
//        userApprovalHandler.setClientDetailsService(clientDetailsService());
//        userApprovalHandler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService()));
//        return userApprovalHandler;
//    }
//
//
//    @Bean
//    public ApprovalStore approvalStore() {
//        return new JdbcApprovalStore(dataSource());
//    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security
                // 允许表单登录
                .allowFormAuthenticationForClients()
                // 密码加密编码器
                .passwordEncoder(passwordEncoder)
                .checkTokenAccess("permitAll()");
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
        // OAuto2的客户端配置 - 基于 JDBC
        clients.withClientDetails(clientDetailsService());
    }


    /**
     * 配置令牌
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                // 认证管理器 - 在密码模式必须配置
                .authenticationManager(authenticationManager)
                // 自定义校验用户service
                .userDetailsService(userDetailService)
                // 用户授权记录 授权模式才有
                //.userApprovalHandler(userApprovalHandler())
                // 是否重复使用 refresh_token
                .reuseRefreshTokens(false)
                .exceptionTranslator(new GlobalOauth2ExceptionTranslator());

        // 在没有使用JWT转换器时，目前测试只能通过增强器设置自定义属性
        TokenEnhancer enhancer = (accessToken, authentication) -> {
            DefaultOAuth2AccessToken token1 = (DefaultOAuth2AccessToken) accessToken;
            LoginUser user = (LoginUser) authentication.getUserAuthentication().getPrincipal();
            token1.setAdditionalInformation(new HashMap<String, Object>(){{
                put("user_name", user.getUsername());
                put("user_id", user.getUserId());
            }});
            return token1;
        };
        endpoints.tokenEnhancer(enhancer);

        // 设置令牌增强 JWT 转换
//        TokenEnhancerChain enhancer = new TokenEnhancerChain();
//        enhancer.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter));
//        endpoints.tokenEnhancer(enhancer);

    }

}

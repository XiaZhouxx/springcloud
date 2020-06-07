package com.xz.oauth.resource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

import java.rmi.Remote;

/**
 * @author xz
 * @ClassName ResourceServerConfig
 * @Description
 * @date 2020/2/14 0014 19:15
 **/
@Configuration
@EnableResourceServer
// 在方法采用注解 更细粒度的授权 @PreAuthorize("hasAuthority('xxx')") 拥有xxx权限才能访问对应接口
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    RemoteTokenServices services;

    /**
     * 配置对资源授权
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 以下为全局配置所需保护的资源路径及需要的权限
                // 可以通过注解在Controller细粒度的控制
                // hasRole() 判断角色 hasAuthority() 判断权限
                .authorizeRequests().antMatchers().authenticated()
                .and().csrf().disable();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 使用Jwt方式需要配置 资源服务器token 如何自己转换解析 就不需要远程访问认证服务器
        // resources.tokenStore(tokenStore).stateless(true);

        // 资源服务器从远程check_token时 只保存了部分信息，自定义获取
        DefaultAccessTokenConverter converter = new DefaultAccessTokenConverter();
        converter.setUserTokenConverter(new MyUserAuthenticationConverter());
        services.setAccessTokenConverter(converter);
        resources.tokenServices(services);
    }

}



package com.xz.oauth.resource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

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
    TokenStore tokenStore;

    /**
     * 配置对资源如果授权
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
                .authorizeRequests()
                // 以下为配置所需保护的资源路径及需要的权限，实际从数据库中获取
                .antMatchers("/").hasAuthority("System")
                .antMatchers("/view/**").hasAuthority("SystemContentView")
                .antMatchers("/insert/**").hasAuthority("SystemContentInsert")
                .antMatchers("/update/**").hasAuthority("SystemContentUpdate")
                .antMatchers("/delete/**").hasAuthority("SystemContentDelete");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 使用Jwt方式需要配置 资源服务器token 如何自己转换解析 就不需要远程访问认证服务器
        // 注意去掉远程访问的配置
        // resources.tokenStore(tokenStore).stateless(true);
    }

}

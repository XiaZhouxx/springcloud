package com.xz.oauth.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author xz
 * @Description Security Web授权配置
 * @date 2020/2/13 0013 16:19
 **/
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /** 密码编码器-加密 更多实现查看 passwordEncoder */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }

    @Bean
    public  AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 密码需要加密 密码模式
        /*auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("123456")).roles("ADMIN")
                .and()
                .withUser("user").password(passwordEncoder().encode("123456")).roles("USER");*/
        // 从数据库中查询用户 以及校验\
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 设置不隐藏找不到用户异常
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        auth.authenticationProvider(provider);
        // .and().authenticationProvider()
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 跨域支持
                // 授权码表单登录,loginPage为登录请求的url,loginProcessingUrl为表单登录处理的URL
                // .and().formLogin().loginPage(LoginConstant.LOGIN_PAGE).loginProcessingUrl(LoginConstant.LOGIN_FROM_PAGE)
                // .and().authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // 允许访问的路径

                //禁用跨站伪造
                .authorizeRequests()
                .antMatchers(
                        "/actuator/**",
                        "/oauth/*",
                        "/token/**").permitAll()
                .anyRequest().authenticated()
                // 授权码模式需要
                .and().httpBasic()
                .and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 放行的路径
        web.ignoring()
                .antMatchers("/oauth/check_token")
                .antMatchers("/token/*");
    }
}

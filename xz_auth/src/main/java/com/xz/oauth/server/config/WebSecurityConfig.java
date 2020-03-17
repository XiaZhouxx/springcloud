package com.xz.oauth.server.config;

import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author xz
 * @ClassName WebOAuth2Config
 * @Description Security Web授权配置
 * @date 2020/2/13 0013 16:19
 **/
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 密码编码器-加密 更多实现查看 passwordEncoder
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
        // 基于数据库的 存储用户
        auth.userDetailsService(userDetailsService());
    }



    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/oauth/check_token");
    }



    @Test
    public void test() {
        System.out.println(new BCryptPasswordEncoder().encode("secret"));
    }
}

package com.xz.oauth.resource.controller;

import com.xz.oauth.resource.config.LoginUser;
import com.xz.oauth.resource.domain.TbContent;
import com.xz.oauth.resource.service.impl.TbContentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xz
 * @ClassName ContentController
 * @Description
 * @date 2020/2/14 0014 19:12
 **/
@RestController
@PreAuthorize("hasAuthority('System')")
@Slf4j
public class ContentController {
    @Autowired
    TbContentService tbContentService;

    @RequestMapping("/")
    public List<TbContent> findAll() {
        return tbContentService.findAll();
    }

    /**
     * 获取内容
     * @author xz
     * @date 2020/6/2
     */
    @RequestMapping("/{id}")
    @PreAuthorize("hasAuthority('xxxx')")
    public TbContent findById(@PathVariable Long id) {
        return tbContentService.findById(id);
    }

    /**
     * 获取当前登录用户
     * @author xz
     * @date 2020/6/3
     */
    @RequestMapping("/user")
    public Authentication currentUser(Authentication Authentication) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = (LoginUser) auth.getPrincipal();
        System.out.println(user.getUserId());
        System.out.println(user.getAuthorities());
        return Authentication;
    }
}

package com.xz.springcloud.controller;

import com.xz.springcloud.domain.TbUser;
import com.xz.springcloud.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xz
 * @ClassName UserController
 * @Description
 * @date 2020/3/4 0004 21:17
 **/
@RestController
@RequestMapping("/user")
public class UserController {



    @Autowired
    TbUserService userService;

    @GetMapping("/{id}")
    public TbUser getById(@PathVariable Integer id) {
        return userService.selectById(id);
    }

    @GetMapping("/msg")
    public String sendMsg(HttpServletRequest request) {

        return "success";
    }
}

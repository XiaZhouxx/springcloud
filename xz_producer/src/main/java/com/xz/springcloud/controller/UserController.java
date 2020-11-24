package com.xz.springcloud.controller;

import com.xz.springcloud.domain.TbUser;
import com.xz.springcloud.service.TbUserService;
import com.xz.springcloud.service.impl.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @Autowired
    TestService service;

    @GetMapping("/{id}")
    public TbUser getById(@PathVariable Integer id) {
        return userService.selectById(id);
    }

    @PostMapping("/insert")
    public String insertUsers(@RequestBody List<TbUser> users) {
        userService.insertAll(users);
        return "insert success";
    }

    /**
     * 测试事务
     * @return
     */
    @RequestMapping("/tx")
    public TbUser tx() {
        try{
            service.test();
        }catch (Exception e) {

        }

        return userService.findOne();
    }

    @GetMapping("/msg")
    public String sendMsg(HttpServletRequest request) {

        return "success";
    }
}

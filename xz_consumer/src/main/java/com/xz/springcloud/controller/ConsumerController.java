package com.xz.springcloud.controller;

import com.xz.springcloud.feign.ProducerFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xz
 * @ClassName ConsumerController
 * @Description
 * @date 2019/5/13 0013 15:44
 **/
@RestController
public class ConsumerController {
    @Value("${server.port}")
    private String port;

    @Autowired
    ProducerFeign feign; // feign 自带Ribbon 负载均衡

    @GetMapping("/add")
    public String add(){
        return port +  "通过Feign调用返回" + feign.add();
    }

    @GetMapping("/del")
    public String del(){
        return feign.del();
    }

}

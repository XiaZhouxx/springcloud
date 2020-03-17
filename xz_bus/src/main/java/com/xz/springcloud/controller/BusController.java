package com.xz.springcloud.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xz
 * @ClassName BusController
 * @Description
 * @date 2019/5/26 0026 10:02
 **/
@RestController
public class BusController {

    @Autowired
    AmqpTemplate template;

    @RequestMapping("/send")
    public void test() {
        // 发送消息到消息中间件中 exchange  routing key  message
        template.convertAndSend("ex_test",null,"hello world");
    }


}

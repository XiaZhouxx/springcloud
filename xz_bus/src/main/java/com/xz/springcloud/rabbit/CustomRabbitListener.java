package com.xz.springcloud.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xz
 * @ClassName CustomRabbitListener
 * @Description
 * @date 2019/6/23 0023 12:33
 **/
@Component
@RabbitListener(queues = "test") // queues 指定监听的消息队列
public class CustomRabbitListener {

    @RabbitHandler
    public void test(String text) {
        System.out.println("接收收到消息队列中的：" + text);
    }
}

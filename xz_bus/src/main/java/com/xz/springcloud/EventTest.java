package com.xz.springcloud;

import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @author xz
 * @ClassName EventTest
 * @Description
 * @date 2019/5/26 0026 9:31
 **/
@Configuration
public class EventTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.xz.springcloud");
        context.register(EventTest.class);

        ApplicationEventPublisher publisher = context;

        ApplicationEvent event = new ApplicationEvent("Hello world") {
            @Override
            public Object getSource() {
                return "Hello world";
            }
        };
        // 发布事件
        publisher.publishEvent(event);
    }

    // 监听事件
    @EventListener
    public void onApplicationEvent(RefreshRemoteApplicationEvent event) {
        System.out.println(event);
    }

}



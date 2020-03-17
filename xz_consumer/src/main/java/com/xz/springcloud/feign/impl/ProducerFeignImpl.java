package com.xz.springcloud.feign.impl;

import com.xz.springcloud.feign.ProducerFeign;
import org.springframework.stereotype.Component;

/**
 * @author xz
 * @Description 服务降级处理
 * @date 2019/5/13 0013 16:07
 **/
@Component
public class ProducerFeignImpl implements ProducerFeign {
    public String add() {
        return null;
    }

    public String del() {
        return "Feign Hystrix";
    }
}

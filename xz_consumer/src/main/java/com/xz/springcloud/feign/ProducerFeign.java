package com.xz.springcloud.feign;

import com.xz.springcloud.feign.impl.ProducerFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xz
 * @Description 调用远程接口 可以考虑将调用接口提取成模块
 * @date 2019/5/13 0013 15:44
 **/
@FeignClient(value = "eureka-producer",fallback = ProducerFeignImpl.class)
public interface ProducerFeign {
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    String add();
    @RequestMapping(value = "/del",method = RequestMethod.GET)
    String del();
}

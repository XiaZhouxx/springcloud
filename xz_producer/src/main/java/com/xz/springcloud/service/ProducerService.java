package com.xz.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xz
 * @ClassName ProducerService
 * @Description Hystrix 请求合并
 * @date 2019/11/8 0008 21:25
 **/
@Service
public class ProducerService {
    private Logger log = Logger.getLogger(ProducerService.class);

    // 指定请求合并的方法 和 配置是将多少毫秒内的请求合并
    @HystrixCollapser(batchMethod = "findAll", collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "100")
    })
    public String find(Long id){
        log.info("执行find方法....");
        return id + "";
    }

    @HystrixCommand
    public String findAll(List<String> id) {
        log.info("执行findAll批量查询中.....");
        return "findAll";
    }

    public void add() {
        System.out.println("ADD");
    }
}

package com.xz.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.xz.springcloud.annotation.CustomHystrix;
import com.xz.springcloud.service.ProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * @author xz
 * @ClassName ProducerController
 * @Description
 * @date 2019/5/13 0013 8:39
 **/
@RestController
public class ProducerController {

    private ProducerService service;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @GetMapping("/add")
    // 注解内配置项参考 HystrixCommandProperties 熔断方法注意方法签名(修饰符返回值参数)要相同
    @HystrixCommand(fallbackMethod = "addFallBack", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "200")
    })
    public String add() {
        throw new RuntimeException();
    }

    @GetMapping("/add2")
    public String customHystrix() throws Exception{
        // 低级版本自定义熔断 应该放入AOP中处理解决熔断
        Future<String> future = executorService.submit(() -> {
            service.add();
            return "success";
        });
        String result;
        try{
            result = future.get(100, TimeUnit.MILLISECONDS);
        }catch (TimeoutException e) {
            // 任务超时取消执行
            future.cancel(true);
            // 将异常抛出让统一异常处理做容错处理(调用熔断方法)
            throw e;
        }


        return result;
    }

    @CustomHystrix(timeout = 1000)
    @GetMapping("/add3")
    public String annotationHystrix() {
        return "success";
    }

    @GetMapping("/find/{id}")
    public String find(@PathVariable Long id){
        return service.find(id);
    }

    public String addFallBack(){
        return "熔断处理";
    }
    @GetMapping("/del")
    public String del(){
        throw new RuntimeException();
    }




    // 父子线程共用
    ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
    // 只适合当前线程，子线程不公用
    // ThreadLocal<String> threadLocal = new ThreadLocal<>();


    @Test
    public void testA() {
        threadLocal.set("123");
        System.out.println(Thread.currentThread().getName());
        System.out.println(threadLocal.get());
        testB();
    }

    public void testB(){
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(threadLocal.get());
        }).start();
    }

}

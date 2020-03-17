package com.xz.springcloud.aop;

import com.xz.springcloud.annotation.CustomHystrix;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author xz
 * @ClassName ControllerHystrixAspect
 * @Description
 * @date 2020/3/16 0016 11:58
 **/
@Aspect
@Component
@Slf4j
public class ControllerHystrixAspect {

    /**
     * 切入点为 包含CustomHystrix注解 且是controller包中的类的所有方法
     */
    @Pointcut("@annotation(com.xz.springcloud.annotation.CustomHystrix) && execution(* com.xz.springcloud.controller.*.*(..) )")
    public void point(){}

    @Around("point() && @annotation(hystrix)")
    public Object around(ProceedingJoinPoint joinPoint, CustomHystrix hystrix) throws Throwable {
        log.info(hystrix.timeout() + "");
        log.info("begin invoke method");
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<Object> future = service.submit(() -> {
            Object result = null;
            try {
                result = joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return result;
        });
        Object result;
        try {
            result = future.get(100, TimeUnit.MILLISECONDS);
        }catch (TimeoutException e) {
            future.cancel(true);
            // 抛出异常让全局异常做熔断
            throw e;
        }finally {
            service.shutdown();
        }
        return result;
    }

}

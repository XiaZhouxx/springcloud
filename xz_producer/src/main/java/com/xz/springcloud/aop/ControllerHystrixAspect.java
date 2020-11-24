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
 * @Description 自定义实现熔断机制
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
    public void point() {
    }

    @Around("point() && @annotation(hystrix)")
    public Object around(ProceedingJoinPoint joinPoint, CustomHystrix hystrix) throws Throwable {
        if (hystrix == null) {
            return joinPoint.proceed();
        }
        long timeout = Math.max(hystrix.timeout(), 1000L);
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
            // 带超时机制的熔断降级
            result = future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            // 抛出异常or 对应的降级策略
            throw e;
        } finally {
            service.shutdown();
        }
        return result;
    }

}

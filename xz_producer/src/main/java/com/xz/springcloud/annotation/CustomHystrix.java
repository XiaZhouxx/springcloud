package com.xz.springcloud.annotation;

import java.lang.annotation.*;

/**
 * @author xz
 * @ClassName CustomHystrix
 * @Description
 * @date 2020/3/16 0016 11:56
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CustomHystrix {
    int timeout();
}

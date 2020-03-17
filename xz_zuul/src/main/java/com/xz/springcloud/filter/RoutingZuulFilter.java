package com.xz.springcloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xz
 * @ClassName CustomZuulFilter
 * @Description
 * @date 2019/6/20 0020 13:19
 **/
@Component
public class RoutingZuulFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger(RoutingZuulFilter.class);
    /**
     * 过滤器类型 他决定了过滤器在请求的哪个阶段执行
     * pre 在请求路由之前执行
     * routing 在请求时执行
     * post 在routiong 和 error之后执行
     * error 在请求发生错误时被调用
     * @return
     */
    @Override
    public String filterType() {
        return "routing";
    }

    /**
     * 过滤器的执行顺序 当存在多个过滤器时 会根据该值排序执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 判断过滤器是否执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        // 获取当前请求上下文
        RequestContext cx = RequestContext.getCurrentContext();
        // 获取请求
        HttpServletRequest re = cx.getRequest();

        logger.info("Routing filter");

        return null;
    }
}

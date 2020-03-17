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
public class ErrorZuulFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger(ErrorZuulFilter.class);

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 获取当前请求上下文
        RequestContext cx = RequestContext.getCurrentContext();
        // 获取请求
        HttpServletRequest re = cx.getRequest();

        logger.info("error filter");


        return null;
    }
}

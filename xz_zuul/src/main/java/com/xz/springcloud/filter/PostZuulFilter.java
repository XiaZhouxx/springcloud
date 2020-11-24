package com.xz.springcloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author xz
 * @Description 前置过滤器
 * @date 2019/6/20 0020 13:19
 **/
@Component
public class PostZuulFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger(PostZuulFilter.class);

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
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
        InputStream stream = cx.getResponseDataStream();
        String body = null;
        try {
            body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            logger.info("response data = {}",body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 注意这里 在过滤器的任何地方获取了请求相关的 都要重新设置回去
        cx.setResponseBody("new body: "+body);
        logger.info("post filter");


        return null;
    }
}

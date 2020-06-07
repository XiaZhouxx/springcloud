package com.xz.oauth.server.controller;

import com.xz.oauth.server.domain.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

/**
 * @author xz
 * @Description 授权码模式自定义登录授权
 * @date 2020/6/2 13:43
 **/
// @SessionAttributes({"authorizationRequest"})
@RestController
@RequestMapping("oauth")
public class Oauth2Controller {
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Autowired
    TokenEndpoint tokenEndpoint;


    /**
     * 自定义处理授权后的结果集
     * @author xz
     * @date 2020/6/5
     */
    @PostMapping("/token")
    public BaseResult<String> customPostAccessToken(Principal principal,
                                                     @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Map<String, Object> data = new HashMap<>(4);
        data.put("token", accessToken.getValue());
        if (accessToken.getRefreshToken() != null) {
            data.put("refreshToken", accessToken.getRefreshToken().getValue());
        }
        // 根据需要添加其他信息....
        return BaseResult.ok(data);
    }
    /**
     * 对认证服务器发起的请求如果没有在配置中配置允许访问
     * 都会经过此方法 进行后续的授权逻辑处理
     * @author xz
     * @date 2020/6/3
     */
//    @RequestMapping("oauth2/login")
//    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
//    public String login(HttpServletResponse response, HttpServletRequest request) throws IOException {
//        SavedRequest savedRequest = requestCache.getRequest(request, response);
//        if (null != savedRequest) {
//            // 用户发起认证的url 因为是自定义，需要自己校验 url的合法性、参数的有效性
//            String targetUrl = savedRequest.getRedirectUrl();
//            log.info(targetUrl);
//            log.info(request.getRemoteHost());
//            // 并不是认证url, 直接拒绝
//            if (!targetUrl.contains(LoginConstant.LOGIN_URL_PROFIX)) {
//                redirectStrategy.sendRedirect(request, response, LoginConstant.LOGIN_ERROR_URL);
//            }
//            redirectStrategy.sendRedirect(request, response, "http://www.baidu.com");
//        }
//        // 重定向到登录页面
//        return "success";
//    }
//
//    @RequestMapping("oauth2/hello")
//    public String hello() {
//        return "hello,world";
//    }
//
//    /**
//     * 自定义的用户授权界面 - 可以像QQ登录一样 设定指定权限
//     * 只要登录成功就一起授权，减少授权页面
//     * @author xz
//     * @date 2020/6/3
//     */
//    @RequestMapping("/oauth/confirm_access")
//    public String getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
//        @SuppressWarnings("unchecked")
//        Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ? model.get("scopes") : request.getAttribute("scopes"));
//        List<String> scopeList = new ArrayList<>();
//        if (scopes != null) {
//            scopeList.addAll(scopes.keySet());
//        }
//        model.put("scopeList", scopeList);
//        return "";
//    }

}

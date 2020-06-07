package com.xz.oauth.server.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xz
 * @ClassName GlobalOauth2ExceptionHandler
 * @Description
 * @date 2020/6/3 13:52
 **/
public class GlobalOauth2ExceptionTranslator implements WebResponseExceptionTranslator {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        if (e instanceof OAuth2Exception) {
            OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(oAuth2Exception);
        }
        throw e;
    }
}

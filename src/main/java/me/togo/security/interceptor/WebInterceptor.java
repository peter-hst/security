package me.togo.security.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Slf4j
public class WebInterceptor extends FilterSecurityInterceptor {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("------------before----->> ", WebInterceptor.class.getName());
        super.doFilter(request, response, chain);
        log.debug("------------after------>> ", WebInterceptor.class.getName());
    }
}

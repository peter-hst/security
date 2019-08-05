package me.togo.security.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class WebFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        log.debug("-------- before ----{} --- session -> {}", ((HttpServletRequest) servletRequest).getRequestURI(), ((HttpServletRequest) servletRequest).getSession().getAttribute("username"));
        if (((HttpServletRequest) servletRequest).getRequestURI().matches("/hello.*")) return;
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

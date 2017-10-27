package com.aries.aries_boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class EncodeFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(EncodeFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        logger.info("EncodeFilter已经启动");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("EncodeFilter拦截请求中...");
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

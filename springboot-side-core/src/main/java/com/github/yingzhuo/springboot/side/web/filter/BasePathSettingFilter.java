package com.github.yingzhuo.springboot.side.web.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class BasePathSettingFilter extends OncePerRequestFilter {

    private String[] basepathAttributeNames = new String[]{"webroot", "WEBROOT", "basePath", "BASEPATH"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String basePath = getBasePath(request);

        for (String attrName : basepathAttributeNames) {
            request.setAttribute(attrName, basePath);
        }

        filterChain.doFilter(request, response);
    }

    private String getBasePath(HttpServletRequest request) {
        try {
            final String scheme = request.getScheme();
            final String serverName = request.getServerName();
            final int port = request.getServerPort();
            final String path = request.getContextPath();
            return scheme + "://" + serverName + ":" + port + path;
        } catch (Exception e) {
            return null;
        }
    }

    public void setBasepathAttributeNames(String[] basepathAttributeNames) {
        this.basepathAttributeNames = basepathAttributeNames;
    }

}

package com.github.yingzhuo.springboot.side.web.filter;

import com.github.yingzhuo.springboot.side.web.AttributeScope;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class BasePathSettingFilter extends OncePerRequestFilter {

    private String[] basepathAttributeNames = new String[]{"webroot", "WEBROOT", "basePath", "BASEPATH"};
    private AttributeScope scope = AttributeScope.REQUEST;
    private boolean endWithSlash = true;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String basePath = getBasePath(request);

        for (String attrName : basepathAttributeNames) {

            if (this.scope == AttributeScope.REQUEST) {
                request.setAttribute(attrName, basePath);
            } else {
                request.getSession(true).setAttribute(attrName, basePath);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getBasePath(HttpServletRequest request) {
        try {
            final String scheme = request.getScheme();
            final String serverName = request.getServerName();
            final int port = request.getServerPort();
            final String path = request.getContextPath();
            final String basePath = scheme + "://" + serverName + ":" + port + path;
            return endWithSlash ? basePath + "/" : basePath;
        } catch (Exception e) {
            return null;
        }
    }

    public void setBasepathAttributeNames(String[] basepathAttributeNames) {
        this.basepathAttributeNames = basepathAttributeNames;
    }

    public void setScope(AttributeScope scope) {
        this.scope = scope;
    }

    public void setEndWithSlash(boolean endWithSlash) {
        this.endWithSlash = endWithSlash;
    }

}

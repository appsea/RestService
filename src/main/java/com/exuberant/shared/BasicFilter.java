package com.exuberant.shared;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

//@Component(value = "basicFilter")
public class BasicFilter implements Filter {

    // This will be injected here


    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("In basicFilter: userManager is ");
        //filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig paramFilterConfig) throws ServletException {
        // do nothing
    }

    @Override
    public void destroy() {
        // do nothing
    }

}
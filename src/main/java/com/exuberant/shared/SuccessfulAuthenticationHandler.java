package com.exuberant.shared;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SuccessfulAuthenticationHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws ServletException, IOException {
        System.err.println("Authentication Successful!!!");
        SavedRequest savedRequest
                = requestCache.getRequest(request, response);
        request.getSession().setAttribute("userId", "Rakesh");
        response.addCookie(new Cookie("Hi", "Rakesh!!"));
        response.setHeader("Token", "Rakesh");
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
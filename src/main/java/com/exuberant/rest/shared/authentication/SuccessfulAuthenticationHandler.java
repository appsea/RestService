package com.exuberant.rest.shared.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.exuberant.rest.util.Constants.SUB;

@Component
public class SuccessfulAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String userId = authentication.getName();
        String token = jwtConfig.generateToken(userId);
        response.setHeader(SUB, token);
    }
}
package com.exuberant.rest.shared.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.exuberant.rest.util.Constants.SUB;

@Component
public class TokenAuthenticationFilter extends GenericFilterBean {

    public static final Log log = LogFactory.getLog(TokenAuthenticationFilter.class);

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserService userService;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        //extract token from header
        final String accessToken = httpRequest.getHeader(SUB);
        if (null != accessToken) {
            Jws<Claims> claims = jwtConfig.decode(accessToken);
            String userId = claims.getBody().get(SUB).toString();
            log.info("User logged in with token: " + userId);
            final UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId, null, userService.getAuthorities(userId));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

}
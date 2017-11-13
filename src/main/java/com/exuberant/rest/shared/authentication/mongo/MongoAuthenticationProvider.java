package com.exuberant.rest.shared.authentication.mongo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;

/**
 * Created by rakesh on 04-Nov-2017.
 */
public class MongoAuthenticationProvider implements AuthenticationProvider {

    public static final Log log = LogFactory.getLog(MongoAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (log.isDebugEnabled()) {
            log.debug("Inside Mongo Authentication" + authentication.getCredentials());
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getPrincipal(), Arrays.asList(new SimpleGrantedAuthority("USER")));
        return token;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}

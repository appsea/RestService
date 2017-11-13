package com.exuberant.rest.shared.authentication.mongo;

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

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.err.println("Inside Mongo Authentication" + authentication.getCredentials());
        //authentication.setAuthenticated(true);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),authentication.getPrincipal(), Arrays.asList(new SimpleGrantedAuthority("USER")));
        return token;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        System.err.println("Inside Mongo Support");
        return false;
    }
}

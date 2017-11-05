package com.exuberant.shared;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by rakesh on 04-Nov-2017.
 */
public class MongoAuthenticationProvider {

    //@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    //@Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}

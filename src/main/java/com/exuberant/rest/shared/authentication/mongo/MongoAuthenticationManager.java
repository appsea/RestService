package com.exuberant.rest.shared.authentication.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by rakesh on 11-Nov-2017.
 */

public class MongoAuthenticationManager implements AuthenticationManager {

    @Autowired
    private MongoAuthenticationProvider mongoAuthenticationProvider;

    public MongoAuthenticationManager(MongoAuthenticationProvider mongoAuthenticationProvider) {
        this.mongoAuthenticationProvider = mongoAuthenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.err.println("Inside Mongo Manager");
        return mongoAuthenticationProvider.authenticate(authentication);
    }
}

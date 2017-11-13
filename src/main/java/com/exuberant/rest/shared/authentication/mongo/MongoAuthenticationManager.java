package com.exuberant.rest.shared.authentication.mongo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by rakesh on 11-Nov-2017.
 */

public class MongoAuthenticationManager implements AuthenticationManager {

    public static final Log log = LogFactory.getLog(MongoAuthenticationManager.class);

    @Autowired
    private MongoAuthenticationProvider mongoAuthenticationProvider;

    public MongoAuthenticationManager(MongoAuthenticationProvider mongoAuthenticationProvider) {
        this.mongoAuthenticationProvider = mongoAuthenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("Checking Mongo Authentication!!!");
        return mongoAuthenticationProvider.authenticate(authentication);
    }
}

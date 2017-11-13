package com.exuberant.rest.shared.authentication;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by rakesh on 04-Nov-2017.
 */
public interface UserService extends UserDetailsService{

    User signup(User user);
    User login(User user);
    void logout();



}

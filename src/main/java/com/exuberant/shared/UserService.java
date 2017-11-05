package com.exuberant.shared;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by rakesh on 04-Nov-2017.
 */
public interface UserService extends UserDetailsService{

    User signup(User user);
    User login(User user);
    void logout();



}

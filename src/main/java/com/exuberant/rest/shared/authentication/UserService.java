package com.exuberant.rest.shared.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

/**
 * Created by rakesh on 04-Nov-2017.
 */
public interface UserService extends UserDetailsService {

    User signup(User user);

    User login(User user);

    Collection<GrantedAuthority> getAuthorities(String userId);

    void logout();
}

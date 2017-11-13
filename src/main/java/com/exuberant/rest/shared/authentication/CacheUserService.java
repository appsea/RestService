package com.exuberant.rest.shared.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by rakesh on 04-Nov-2017.
 */
@Service
public class CacheUserService implements UserService {

    @Autowired
    private CacheUserRepository cacheUserRepository;

    public CacheUserService(CacheUserRepository cacheUserRepository, PasswordEncoder passwordEncoder) {
        this.cacheUserRepository = cacheUserRepository;
    }

    @Override
    public User signup(User user) {
        return cacheUserRepository.signup(user);
    }

    @Override
    public User login(User user) {
        return cacheUserRepository.login(user);
    }

    @Override
    public void logout() {

    }

    @Override
    public Collection<GrantedAuthority> getAuthorities(String userId) {
        return cacheUserRepository.getAuthorities(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}

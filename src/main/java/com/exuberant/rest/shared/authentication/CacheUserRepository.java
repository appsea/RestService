package com.exuberant.rest.shared.authentication;

import com.exuberant.rest.util.MultiValueMap;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rakesh on 04-Nov-2017.
 */
@Service
public class CacheUserRepository {

    private Map<String, User> credentials = new HashMap<>();
    private MultiValueMap<String, GrantedAuthority> userAuthorities = new MultiValueMap<>();


    public CacheUserRepository() {
        credentials.put("r", new User("r", "r", Arrays.asList(new SimpleGrantedAuthority("ADMIN"))));
        userAuthorities.put("user", new SimpleGrantedAuthority("USER"));
        userAuthorities.put("admin", new SimpleGrantedAuthority("ADMIN"));
        userAuthorities.put("admin", new SimpleGrantedAuthority("USER"));
    }

    public User signup(User user) {
        User authenticateUser;
        if (isExistingUser(user)) {
            authenticateUser = login(user);
        } else {
            authenticateUser = save(user);
        }
        return authenticateUser;
    }

    private boolean isExistingUser(User user) {
        return credentials.containsKey(user.getUsername());
    }

    public User login(User user) {
        return null;
    }

    public User save(User user) {
        return credentials.put(user.getUsername(), user);
    }

    public Collection<GrantedAuthority> getAuthorities(String userId) {
        return userAuthorities.get(userId);
    }
}

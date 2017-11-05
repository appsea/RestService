package com.exuberant.shared;

import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by rakesh on 04-Nov-2017.
 */
@Controller
public class AuthenticationController {

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User login(User user){
        return user;
    }

    @RequestMapping("/logout")
    public void logout(){

    }
}

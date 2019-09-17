package com.plainprog.duobk_web_service.controllers;

import com.plainprog.duobk_web_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * Returns name of currently authenticated user
     * */
    @RequestMapping(value = "/currentName",method = RequestMethod.GET)
    public String user(OAuth2Authentication authentication) {
        LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();
        return (String)properties.get("name");
    }
    /**
     * Returns email of currently authenticated user
     * */
    @RequestMapping(value = "/currentEmail",method = RequestMethod.GET)
    public String userEmail(OAuth2Authentication authentication) {
        LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();
        return (String)properties.get("email");
    }
}

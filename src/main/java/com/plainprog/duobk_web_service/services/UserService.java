package com.plainprog.duobk_web_service.services;

import com.google.gson.Gson;
import com.plainprog.duobk_web_service.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    public static final  String NO_SUCH_USER = "NO_SUCH_USER";
    public static final String CONSTRUCTOR_SERVICE_URL = "http://constructor-service";

    @Autowired        // NO LONGER auto-created by Spring Cloud (see below)
    @LoadBalanced     // Explicitly request the load-balanced template
    // with Ribbon built-in
    protected RestTemplate restTemplate;

    public void createUser(String userEmail, String userType){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();
        User user = new User(userEmail,userType);
        HttpEntity<String> request = new HttpEntity<>(gson.toJson(user,User.class),headers);
        String url = CONSTRUCTOR_SERVICE_URL + "/users/create";
        restTemplate.exchange(url,HttpMethod.POST, request, Object.class);
    }
    public String getUserAuthorities(String email){
        ResponseEntity<String> response = restTemplate.getForEntity(CONSTRUCTOR_SERVICE_URL + "/users/getAuthorities?email=" + email,String.class);
        if(response.getStatusCode() == HttpStatus.OK){
            if(response.getBody()!= null && !response.getBody().isEmpty())
                return response.getBody();
        }
        return NO_SUCH_USER;
    }
}

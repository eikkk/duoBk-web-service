package com.plainprog.duobk_web_service.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ConstructorService {
    public static final String CONSTRUCTOR_SERVICE_URL = "http://constructor-service";
    @Autowired        // NO LONGER auto-created by Spring Cloud (see below)
    @LoadBalanced     // Explicitly request the load-balanced template
    // with Ribbon built-in
    protected RestTemplate restTemplate;


    public void task(){
        HttpEntity<String> request = new HttpEntity<>("");
        restTemplate.exchange(CONSTRUCTOR_SERVICE_URL + "/tasks", HttpMethod.GET,request,String.class);
    }

    public List<Object> getTaskPool(Boolean isAdmin){
        ResponseEntity<String> response = restTemplate.getForEntity(CONSTRUCTOR_SERVICE_URL + "/tasks/getTaskPool?admin=" + isAdmin.toString(),String.class);
        String body = response.getBody();
        Gson gson = new Gson();
        return gson.fromJson(body,new TypeToken<List<Object>>(){}.getType());
    }
    public List<Object> getUserTasks(String email){
        ResponseEntity<String> response = restTemplate.getForEntity(CONSTRUCTOR_SERVICE_URL + "/tasks/getByUser?email=" + email,String.class);
        String body = response.getBody();
        Gson gson = new Gson();
        return gson.fromJson(body,new TypeToken<List<Object>>(){}.getType());
    }
    public void takeTask(String userEmail, Integer taskId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = Integer.toString(taskId);
        HttpEntity<String> request = new HttpEntity<>(body,headers);
        restTemplate.exchange(CONSTRUCTOR_SERVICE_URL + "/tasks/take?email=" + userEmail,HttpMethod.POST,request,Object.class);
    }
}

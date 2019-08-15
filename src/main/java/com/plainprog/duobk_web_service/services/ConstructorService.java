package com.plainprog.duobk_web_service.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.plainprog.duobk_web_service.models.IndexesForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.List;

@Service
public class ConstructorService {
    public static final String CONSTRUCTOR_SERVICE_URL = "http://constructor-service";
    @Autowired        // NO LONGER auto-created by Spring Cloud (see below)
    @LoadBalanced     // Explicitly request the load-balanced template
    // with Ribbon built-in
    protected RestTemplate restTemplate;

    @Autowired
    private Gson gson;


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
    public void doPreProcess(IndexesForm indexesForm){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = gson.toJson(indexesForm);
        HttpEntity<String> request = new HttpEntity<>(body,headers);
        restTemplate.exchange(CONSTRUCTOR_SERVICE_URL + "/tasks/preProcess/do" ,HttpMethod.POST, request, Object.class);
    }
    public String getUnprocessedAsHTML(Integer taskId){
        ResponseEntity<String> response = restTemplate.getForEntity(CONSTRUCTOR_SERVICE_URL + "/tasks/preProcess/getUnprocessedAsHTML?id=" + taskId,String.class);
        return response.getBody();
    }
    public boolean checkIfPreProcessWasNotDone(Integer taskId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = gson.toJson(taskId);
        HttpEntity<String> request = new HttpEntity<>(body,headers);
        ResponseEntity<Boolean> response = restTemplate.postForEntity(CONSTRUCTOR_SERVICE_URL + "tasks//preProcess/checkUnprocessed",request,Boolean.class);
        return response.getBody();
    }
    public ResponseEntity isUserOwnerOfTask(String userEmail, Integer taskId){
        String url = CONSTRUCTOR_SERVICE_URL +
                "/tasks/checkPermission?taskId=" +
                taskId +
                "&userEmail=" +
                userEmail;
        ResponseEntity response = restTemplate.getForEntity(url,Object.class);
        return response;
    }
    public ResponseEntity<String> unprocessedToHtml(String taskId){
        String url = CONSTRUCTOR_SERVICE_URL +
                "/tasks/process/unprocessedToHTML?taskId=" +
                taskId;
        return restTemplate.getForEntity(url,String.class);
    }
    public void moveToBad(String taskId, String dpIndex){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = "";
        HttpEntity<String> request = new HttpEntity<>(body,headers);
        String url = CONSTRUCTOR_SERVICE_URL + "/tasks/process/moveToBad?id=" + taskId + "&index=" + dpIndex;
        restTemplate.exchange(url,HttpMethod.POST, request, Object.class);
    }
    public ResponseEntity<String> getBadResponse(String taskId){
        String url = CONSTRUCTOR_SERVICE_URL +
                "/tasks/process/getBadResponse?id=" +
                taskId;
        return restTemplate.getForEntity(url,String.class);
    }
}

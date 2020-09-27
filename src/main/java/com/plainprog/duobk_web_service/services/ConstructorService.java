package com.plainprog.duobk_web_service.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.plainprog.duobk_web_service.models.SubmitTaskForm;
import com.plainprog.duobk_web_service.models.Task;
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
    public void takeTask(String userEmail, String taskId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        String body = taskId;
        HttpEntity<String> request = new HttpEntity<>(body,headers);
        restTemplate.exchange(CONSTRUCTOR_SERVICE_URL + "/tasks/take?email=" + userEmail,HttpMethod.POST,request,Object.class);
    }
    public ResponseEntity isUserOwnerOfTask(String userEmail, String taskId){
        String url = CONSTRUCTOR_SERVICE_URL +
                "/tasks/checkPermission?taskId=" +
                taskId +
                "&userEmail=" +
                userEmail;
        ResponseEntity response = restTemplate.getForEntity(url,Object.class);
        return response;
    }
    public void submitTask(String userEmail, String taskId, SubmitTaskForm submitTaskForm){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();
        String jsonBody = gson.toJson(submitTaskForm);
        HttpEntity<String> request = new HttpEntity<>(jsonBody,headers);
        String url = CONSTRUCTOR_SERVICE_URL + "/tasks/process/submit?id=" + taskId + "&email=" + userEmail;
        restTemplate.exchange(url,HttpMethod.POST, request, Object.class);
    }
    public void editTask(String userEmail, String taskId, Task task){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();
        String jsonBody = gson.toJson(task);
        HttpEntity<String> request = new HttpEntity<>(jsonBody,headers);
        String url = CONSTRUCTOR_SERVICE_URL + "/tasks/update?email=" + userEmail;
        restTemplate.exchange(url,HttpMethod.POST, request, Object.class);
    }
    public void confirmBook(String userEmail, String taskId, SubmitTaskForm submitTaskForm){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();
        String jsonBody = gson.toJson(submitTaskForm);
        HttpEntity<String> request = new HttpEntity<>(jsonBody,headers);
        String url = CONSTRUCTOR_SERVICE_URL + "/tasks/confirmBook?id=" + taskId + "&email=" + userEmail;
        restTemplate.exchange(url,HttpMethod.POST, request, Object.class);
    }
    public void deleteBook(String bookId){
        HttpEntity<String> request = new HttpEntity<>("");
        String url = CONSTRUCTOR_SERVICE_URL + "/books/delete?id=" + bookId;
        restTemplate.exchange(url,HttpMethod.DELETE, request, Object.class);
    }

    public void deleteTask(String taskId){
        HttpEntity<String> request = new HttpEntity<>("");
        String url = CONSTRUCTOR_SERVICE_URL + "/tasks/delete?id=" + taskId;
        restTemplate.exchange(url,HttpMethod.DELETE, request, Object.class);
    }

    public List<Object> getAllUsers(){
        ResponseEntity<String> response = restTemplate.getForEntity(CONSTRUCTOR_SERVICE_URL + "/users/getAll",String.class);
        String body = response.getBody();
        Gson gson = new Gson();
        return gson.fromJson(body,new TypeToken<List<Object>>(){}.getType());
    }
}

package com.plainprog.duobk_web_service.controllers;

import com.plainprog.duobk_web_service.models.IndexesForm;
import com.plainprog.duobk_web_service.oauth.MyGrantedAuthority;
import com.plainprog.duobk_web_service.services.ConstructorService;
import com.plainprog.duobk_web_service.services.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class ConstructorController {

    @Autowired
    private ConstructorService constructorService;

    @GetMapping(value = "/tasks/getTaskPool")
    public List<Object> getPool(Principal principal){
        Authentication auth = ((OAuth2Authentication) principal).getUserAuthentication();
        Object[] authorities = auth.getAuthorities().toArray();
        boolean isAdmin = false;
        for(int i =0; i < authorities.length; i++){
            MyGrantedAuthority authority = (MyGrantedAuthority) authorities[i];
            if(authority.getAuthority().equals("ROLE_ADMIN"))
                isAdmin = true;
        }
        return constructorService.getTaskPool(isAdmin);
    }

    /**
     * Returns list of user's tasks like Objects, where Object is array that consist of values in next order:
     * task id, task name, task status, task last modified
     * */
    @GetMapping(value = "/tasks/getWithUser")
    public List<Object> getUserTasks(OAuth2Authentication authentication) {
        // get user mail
        LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();
        String email = (String)properties.get("email");
        return constructorService.getUserTasks(email);
    }
    /**
     * Sets user_id value for task row in db.
     * */
    @RequestMapping(value = "/tasks/take", method = RequestMethod.POST, consumes = "text/plain")
    public void takeTask(OAuth2Authentication authentication, @RequestBody Integer id){
        LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();
        String email = (String)properties.get("email");
        constructorService.takeTask(email,id);
    }
    @GetMapping(value = "/tasks/checkPermission")
    public ResponseEntity checkPermission(@RequestParam(value = "taskId", required = true) String taskId, OAuth2Authentication authentication){
        // get current user
        LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();
        String email = (String)properties.get("email");
        return constructorService.isUserOwnerOfTask(email,taskId);
    }
    /**
     * Changes task status to CHECK_NEEDED, sets result value, removes task from user, create HistoryItem
     * @param taskId ID of task to submit
     * @param param String in form "result !message! message", where result - new result, message - message for History
     * */
    @RequestMapping(value = "/process/submit", consumes = "text/plain")
    public void submitTask(@RequestParam (value = "id", required = true) String taskId, @RequestBody String param, OAuth2Authentication authentication){
        // get current user
        LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();
        String email = (String)properties.get("email");
        constructorService.submitTask(email,taskId,param);
    }
}

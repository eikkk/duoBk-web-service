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
    /**
     * Does auto-connecting process, forms unprocessed from auto-connecting result,
     * creates HistoryItem, save everything to db
     * */
    @RequestMapping(value = "/tasks/preProcess/do")
    public void doPreProcess(@RequestBody IndexesForm indexesForm) throws Exception {
        constructorService.doPreProcess(indexesForm);
    }

    @GetMapping(value = "/tasks/preProcess/getUnprocessedAsHTML")
    public String getUnprocessedValuesAsHTMLOptions(@RequestParam(value = "id",required = true) Integer taskId) {
        return  constructorService.getUnprocessedAsHTML(taskId);
    }
    /**
     * If Pre-Process hasn't yet been done on this task(status == NEW), returns true
     * */
    @RequestMapping(value = "/tasks/preProcess/checkUnprocessed", consumes = "text/plain")
    public boolean checkIfPreProcessWasNotDone(@RequestBody Integer taskId){
        return constructorService.checkIfPreProcessWasNotDone(taskId);
    }
    @GetMapping(value = "/tasks/checkPermission")
    public ResponseEntity checkPermission(@RequestParam(value = "taskId", required = true) Integer taskId, OAuth2Authentication authentication){
        // get current user
        LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();
        String email = (String)properties.get("email");
        return constructorService.isUserOwnerOfTask(email,taskId);
    }
    /**
     * Forms HTML code from tasks's unprocessed column for displaying inside of process.html
     * */
    @RequestMapping(value="/tasks/process/unprocessedToHTML", method = RequestMethod.GET)
    public ResponseEntity<String> unprocessedToHtml(@RequestParam(value="taskId",required = true) String taskId){
        return constructorService.unprocessedToHtml(taskId);
    }
    /**
     * Removes dp element from unprocessed column of task
     * , add all p1 and p2 elements of that dp to bad column of task
     * @param  index index of dp element
     * */
    @RequestMapping(value = "/tasks/process/moveToBad")
    public void moveToBad(@RequestParam(value = "id",required = true) String id, @RequestParam (value = "index",required =  true) String index){
        constructorService.moveToBad(id,index);
    }
    /**
     * Forms HTML code for displaying into selects of "Correcting" tab of process.html
     * Look inside TaskService.formBadResponse(String bad) to see how that code is formed
     * */
    @RequestMapping(value = "/tasks/process/getBadResponse")
    public ResponseEntity<?> getBadResponse(@RequestParam(value = "id",required = true) String taskId){
        return constructorService.getBadResponse(taskId);
    }
}

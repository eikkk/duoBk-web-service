package com.plainprog.duobk_web_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/tasks")
    public String tasks() {
        return "tasks.html";
    }

    @RequestMapping("/tasks/preProcess")
    public String pickIndexes(){
        return "pre-process.html";
    }
}

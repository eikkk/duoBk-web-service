package com.plainprog.duobk_web_service.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String defaultRedirect(){
        return "redirect:/tasks";
    }

    @RequestMapping("/index")
    public String indexPage(){return  "index.html";}

    @RequestMapping("/tasks")
    public String tasks() {
        return "tasks.html";
    }

    @RequestMapping("/tasks/process")
    public String process(){
        return "process.html";
    }

    @RequestMapping("/admin/tasks/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createTask(){
        return "create-task.html";
    }

    @RequestMapping("/tasks/preProcess")
    public String pickIndexes(){
        return "pre-process.html";
    }

    @RequestMapping("/admin/books/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createBook(){
        return "create-book.html";
    }

    @RequestMapping("/admin/books")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allBooks(){
        return "allbooks.html";
    }

    @RequestMapping("/admin/tasks")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allTasks(){
        return "alltasks.html";
    }

    @RequestMapping("/admin/books/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editBook(){
        return "book-edit.html";
    }

    @RequestMapping("/admin/tasks/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editTask(){
        return "task-edit.html";
    }

    @RequestMapping("/tasks/process/sent")
    public String processSent(){
        return  "process-sent.html";
    }

    @RequestMapping("/tasks/submit")
    public String submitTask(){
        return  "submit-task.html";
    }

    @RequestMapping("/admin/tasks/check")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String checkTask(){
        return "check-task.html";
    }

    @RequestMapping("/admin/users")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public String adminUsers(){
        return "allusers.html";
    }

    @RequestMapping("/admin/users/edit")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public String editUser(){
        return "user-edit.html";
    }

    @RequestMapping("/admin/authors/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createAuthor(){
        return "create-author.html";
    }

    @RequestMapping("/admin/authors")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminAuthors(){return "allauthors.html";}

    @RequestMapping("/admin/authors/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editAuthor(){return  "author-edit.html";}

    @RequestMapping("/duotexts")
    public String duoTexts(){return  "duotexts.html";}
}

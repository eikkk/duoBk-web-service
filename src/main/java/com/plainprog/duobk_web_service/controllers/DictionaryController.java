package com.plainprog.duobk_web_service.controllers;

import com.plainprog.duobk_web_service.services.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/dictionary")
public class DictionaryController {
    @Autowired
    private DictionaryService service;

    @RequestMapping(value = "/Create",method = RequestMethod.POST)
    public ResponseEntity createDict(@RequestBody String dictionaryJson) {
        return service.createDict(dictionaryJson);
    }
}

package com.main_service.main_service.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class Hello {


    @GetMapping("/hello")
    public String greet() {
        return "Hello Animesh Welcome to our application";
    }

}

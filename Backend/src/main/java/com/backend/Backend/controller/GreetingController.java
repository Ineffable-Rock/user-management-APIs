package com.backend.Backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.backend.Backend.service.GreetingService;

@RestController
public class GreetingController {

    // 1. Declare the dependency (the interface, not the implementation)
    private final GreetingService greetingService;

    // 2. Inject the dependencies using this annotation
    @Autowired
    public GreetingController(GreetingService greetingService){
        this.greetingService = greetingService;
    }


    @GetMapping("/hello")
    public String sayHello() {
        return greetingService.getGreeting();
    }
}
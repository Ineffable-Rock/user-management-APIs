package com.backend.Backend.service;

import org.springframework.stereotype.Service;

@Service
public class EnglishGreetingService implements GreetingService{
    @Override
    public String getGreeting(){
        return "Hi fromm ";
    }
}

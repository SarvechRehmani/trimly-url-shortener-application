package com.trimly.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UrlsController {

    @GetMapping("/")
    public String home(){
        return "home.html";
    }

    @GetMapping("/sign-in")
    public String login(){
        return "login.html";
    }

    @GetMapping("/register")
    public String register(){
        return "register.html";
    }
}

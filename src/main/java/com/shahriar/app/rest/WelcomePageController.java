package com.shahriar.app.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomePageController {

    @GetMapping("/")
    public String welcome() {
        return "<h1>Welcome to my very first Spring Boot application!<h1>";
    }
}
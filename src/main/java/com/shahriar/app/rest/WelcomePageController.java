// package com.shahriar.app.rest;

// public class WelcomePageController {
    
// }

package com.shahriar.app.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomePageController {

    @GetMapping("/")
    public String welcome() {
        return "Welcome to my very first Spring Boot application!";
    }
}
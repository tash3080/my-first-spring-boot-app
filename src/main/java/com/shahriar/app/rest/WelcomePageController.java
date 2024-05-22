// package com.shahriar.app.rest;

// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// public class WelcomePageController {

//     @GetMapping("/")
//     public String welcome() {
//         return "<h1>Welcome to my very first Spring Boot application!<h1>";
//     }
// }

package com.shahriar.app.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class WelcomePageController {

    @GetMapping("/")
    public String welcome(Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = LocalDateTime.now().format(formatter);

        model.addAttribute("currentDateTime", currentDateTime);
        model.addAttribute("usefulInfo", "This is some useful information!");

        return "welcome";
    }
}

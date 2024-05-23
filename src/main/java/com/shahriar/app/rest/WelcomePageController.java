package com.shahriar.app.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
public class WelcomePageController {

    @GetMapping("/")
    public String welcome(Model model) {
        // Define the time zone for Melbourne, Australia
        ZoneId zoneId = ZoneId.of("Australia/Melbourne");

        // Get current date and time in Melbourne time zone
        LocalDateTime now = LocalDateTime.now(zoneId);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String currentTime = now.format(timeFormatter);
        String currentDate = now.toLocalDate().format(dateFormatter);

        // Determine greeting based on the time of day
        LocalTime currentTimeOfDay = now.toLocalTime();
        String greeting;
        if (currentTimeOfDay.isBefore(LocalTime.NOON)) {
            greeting = "Good morning!";
        } else if (currentTimeOfDay.isBefore(LocalTime.of(18, 0))) {
            greeting = "Good afternoon!";
        } else {
            greeting = "Good evening!";
        }

        // Quote of the day (this could be fetched from an API or database in a real application)
        String quoteOfTheDay = "Keep your face always toward the sunshine—and shadows will fall behind you. - Walt Whitman";

        // Add attributes to the model
        model.addAttribute("currentTime", currentTime);
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("quoteOfTheDay", quoteOfTheDay);
        model.addAttribute("greeting", greeting);

        return "welcome";
    }
}

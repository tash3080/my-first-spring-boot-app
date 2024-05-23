package com.shahriar.app.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.ui.Model;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@ExtendWith(MockitoExtension.class)
class WelcomePageControllerTest {

    @Mock
    Model model;

    @InjectMocks
    WelcomePageController controller;

    @Test
    void testWelcome() {
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
        String expectedGreeting;
        if (currentTimeOfDay.isBefore(LocalTime.NOON)) {
            expectedGreeting = "Good morning!";
        } else if (currentTimeOfDay.isBefore(LocalTime.of(18, 0))) {
            expectedGreeting = "Good afternoon!";
        } else {
            expectedGreeting = "Good evening!";
        }

        // Call the method under test
        String viewName = controller.welcome(model);

        // Verify that model attributes are set correctly
        verify(model).addAttribute("currentTime", currentTime);
        verify(model).addAttribute("currentDate", currentDate);
        verify(model).addAttribute("quoteOfTheDay",
                "Keep your face always toward the sunshine—and shadows will fall behind you. - Walt Whitman");
        verify(model).addAttribute("greeting", expectedGreeting);

        // Assert the view name returned by the controller
        assertEquals("welcome", viewName);
    }
}

package com.shahriar.app.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.ui.Model;
import java.time.LocalDateTime;
import java.time.ZoneId;

// public class WelcomePageControllerTest {

//     @Test
//     public void testWelcome() {
//         // Mocking the model
//         Model model = mock(Model.class);

//         // Mocking the zone id
//         // ZoneId mockedZoneId = ZoneId.of("Australia/Melbourne");

//         // Mocking the current time
//         // LocalDateTime mockedNow = LocalDateTime.of(2024, 5, 23, 10, 0, 0); // Example time for testing
//         // when(LocalDateTime.now(mockedZoneId)).thenReturn(mockedNow);

//         // Create an instance of the controller
//         WelcomePageController controller = new WelcomePageController();

//         // Call the method under test
//         String viewName = controller.welcome(model);

//         // Verify that model attributes are set correctly
//         assertEquals("10:00:00", model.getAttribute("currentTime"));
//         assertEquals("2024-05-23", model.getAttribute("currentDate"));
//         assertEquals("Keep your face always toward the sunshine—and shadows will fall behind you. - Walt Whitman", model.getAttribute("quoteOfTheDay"));
//         assertEquals("Good morning!", model.getAttribute("greeting"));

//         // Assert the view name returned by the controller
//         assertEquals("welcome", viewName);
//     }
// }

import static org.mockito.Mockito.*;
// import static org.junit.Assert.*;

/// import org.junit.Test;
// import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
// import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.ui.Model;

// import java.time.LocalDateTime;
import java.time.LocalTime;
// import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@ExtendWith(MockitoExtension.class)
public class WelcomePageControllerTest {

    @Mock
    Model model;

    @InjectMocks
    WelcomePageController controller;

    @Test
    public void testWelcome() {
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
        verify(model).addAttribute("quoteOfTheDay", "Keep your face always toward the sunshine—and shadows will fall behind you. - Walt Whitman");
        verify(model).addAttribute("greeting", expectedGreeting);

        // Assert the view name returned by the controller
        assertEquals("welcome", viewName);
    }
}



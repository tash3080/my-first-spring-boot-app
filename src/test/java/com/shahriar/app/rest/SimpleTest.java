package com.shahriar.app.rest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SimpleTest {

    @Test
	public void simpleTest() {

        int a = 2;
        int b = 3;

        assertTrue(a + b == 5);
	}
}
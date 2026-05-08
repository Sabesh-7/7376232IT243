package com.affordmed.logging_middleware.controller;

import com.affordmed.logging_middleware.util.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String testLog() {

        Log.info(
                "controller",
                "Test endpoint called successfully"
        );

        return "Logging Success";
    }
}
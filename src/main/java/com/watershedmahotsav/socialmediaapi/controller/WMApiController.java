package com.watershedmahotsav.socialmediaapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WMApiController {
	
	@GetMapping("/")
    public String root() {
        return "Social Media API is running!<br>" +
               "Available endpoints:<br>" +
               "- GET /health<br>" +
               "- GET /test<br>" +
               "- GET /social/platforms<br>" +
               "- POST /social/stats";
    }
	
	@GetMapping("/health")
    public String healthCheck() {
        return "Social Media API is running successfully! Timestamp: " + System.currentTimeMillis();
    }
    
    @GetMapping("/test")
    public String test() {
        return "Social Media API - Working Fine!";
    }
    
}

package com.watershedmahotsav.socialmediaapi.controller;

import com.watershedmahotsav.socialmediaapi.dto.VideoStatsRequest;
import com.watershedmahotsav.socialmediaapi.dto.VideoStatsResponse;
import com.watershedmahotsav.socialmediaapi.service.UnifiedSocialMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/social")
public class SocialMediaController {

    @Autowired
    private UnifiedSocialMediaService unifiedSocialMediaService;

    @PostMapping("/stats")
    public VideoStatsResponse getMediaStats(@RequestBody VideoStatsRequest request) {
        System.out.println("Received request: " + request.getMediaUrl());
        return unifiedSocialMediaService.getMediaStats(request.getMediaUrl());
    }
    
    @GetMapping("/platforms")
    public String getSupportedPlatforms() {
        return "Supported platforms: YouTube, Instagram, Facebook, Twitter, LinkedIn";
    }
    
}
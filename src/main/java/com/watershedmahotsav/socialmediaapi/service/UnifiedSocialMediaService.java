package com.watershedmahotsav.socialmediaapi.service;

import com.watershedmahotsav.socialmediaapi.dto.VideoStatsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnifiedSocialMediaService {
    
    @Autowired
    private List<SocialMediaService> socialMediaServices;
    
    @Autowired
    private PlatformService platformService;
    
    // New method without platform parameter (for simplified API)
    public VideoStatsResponse getMediaStats(String mediaUrl) {
        // Auto-detect platform from URL
        String platform = platformService.detectPlatform(mediaUrl);
        System.out.println("Detected platform: " + platform + " for URL: " + mediaUrl);
        
        return getMediaStats(mediaUrl, platform);
    }
    
    // Keep existing method for backward compatibility and manual override
    public VideoStatsResponse getMediaStats(String mediaUrl, String platform) {
        // Auto-detect platform
        if (platform == null || platform.trim().isEmpty()) {
            platform = platformService.detectPlatform(mediaUrl);
            System.out.println("Detected platform: " + platform);
        }
        
        // Validate platform
        if (!platformService.isValidPlatform(platform)) {
        	VideoStatsResponse errorResponse = new VideoStatsResponse() {};
            errorResponse.setSuccess(false);
            errorResponse.setErrorMessage("Unsupported platform: " + platform);
            return errorResponse;
        }
        
        // Find appropriate service
        for (SocialMediaService service : socialMediaServices) {
            if (service.supports(platform)) {
                System.out.println("Using service for platform: " + platform);
                return service.getMediaStats(mediaUrl);
            }
        }
        
        VideoStatsResponse errorResponse = new VideoStatsResponse() {};
        errorResponse.setSuccess(false);
        errorResponse.setErrorMessage("No service available for platform: " + platform);
        return errorResponse;
    }
}
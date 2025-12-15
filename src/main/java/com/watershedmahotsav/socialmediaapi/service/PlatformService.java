package com.watershedmahotsav.socialmediaapi.service;

import org.springframework.stereotype.Service;

@Service
public class PlatformService {
    
    public String detectPlatform(String mediaUrl) {
        if (mediaUrl == null) return "unknown";
        
        String url = mediaUrl.toLowerCase();
        
        if (url.contains("youtube.com") || url.contains("youtu.be")) {
            return "youtube";
        } else if (url.contains("instagram.com") || url.contains("instagr.am")) {
            return "instagram";
        } else if (url.contains("facebook.com") || url.contains("fb.com")) {
            return "facebook";
        } else if (url.contains("twitter.com") || url.contains("x.com")) {
            return "twitter";
        } else if (url.contains("linkedin.com") || url.contains("lnkd.in")) {
            return "linkedin";
        } else {
            return "unknown";
        }
    }
    
    public boolean isValidPlatform(String platform) {
        return platform != null && 
               (platform.equals("youtube") || platform.equals("instagram") || 
                platform.equals("facebook") || platform.equals("twitter") || 
                platform.equals("linkedin"));
    }
}
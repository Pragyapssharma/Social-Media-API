package com.watershedmahotsav.socialmediaapi.service;

import com.watershedmahotsav.socialmediaapi.dto.VideoStatsResponse;

public interface SocialMediaService {
	
	boolean supports(String platform);
    
    VideoStatsResponse getMediaStats(String mediaUrl);
    
    String extractMediaId(String mediaUrl);
	
}

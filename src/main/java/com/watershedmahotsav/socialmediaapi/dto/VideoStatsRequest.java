package com.watershedmahotsav.socialmediaapi.dto;

import javax.validation.constraints.NotBlank;

public class VideoStatsRequest {
    
    @NotBlank(message = "Media URL is required")
    private String mediaUrl;
    

    public VideoStatsRequest() {}
    
    public VideoStatsRequest(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
    

    public String getMediaUrl() { 
        return mediaUrl; 
    }
    
    public void setMediaUrl(String mediaUrl) { 
        this.mediaUrl = mediaUrl; 
    }
    
    
    @Override
    public String toString() {
        return "VideoStatsRequest{mediaUrl='" + mediaUrl + "'}";
    }
}
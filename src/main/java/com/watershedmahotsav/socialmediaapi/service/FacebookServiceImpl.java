package com.watershedmahotsav.socialmediaapi.service;

import com.watershedmahotsav.socialmediaapi.dto.FacebookResponse;
import com.watershedmahotsav.socialmediaapi.dto.VideoStatsResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Random;

@Service
public class FacebookServiceImpl implements SocialMediaService {
	
	@Value("${facebook.access.token}")
    private String accessToken;

    @Value("${facebook.app.id}")
    private String appId;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean supports(String platform) {
        return "facebook".equalsIgnoreCase(platform);
    }

    @SuppressWarnings("unchecked")
    @Override
    public VideoStatsResponse getMediaStats(String mediaUrl) {
    	FacebookResponse response = new FacebookResponse();
        
        try {
        	String videoId = extractMediaId(mediaUrl);
            if (videoId == null) {
                response.setSuccess(false);
                response.setErrorMessage("Invalid Facebook URL");
                return response;
            }
            
         // Facebook Graph API endpoint for video metrics
            String apiUrl = "https://graph.facebook.com/v18.0/" + videoId + 
                           "?fields=description,likes.limit(0).summary(true)," +
                           "comments.limit(0).summary(true),shares,views,from,created_time," +
                           "permalink_url&access_token=" + accessToken;

            ResponseEntity<Map> facebookResponse = restTemplate.exchange(
                apiUrl, HttpMethod.GET, null, Map.class);

            if (facebookResponse.getStatusCode() != HttpStatus.OK) {
                response.setSuccess(false);
                response.setErrorMessage("Facebook API error: " + facebookResponse.getStatusCode());
                return response;
            }

            Map<String, Object> data = facebookResponse.getBody();
            
            response.setSuccess(true);
            response.setVideoId(videoId);
            response.setDescription((String) data.get("description"));
            
            // Extract likes count
            if (data.get("likes") != null) {
                Map<String, Object> likes = (Map<String, Object>) data.get("likes");
                Map<String, Object> summary = (Map<String, Object>) likes.get("summary");
                response.setLikes(Long.valueOf(summary.get("total_count").toString()));
            } else {
                response.setLikes(0L);
            }
            
            // Extract comments count
            if (data.get("comments") != null) {
                Map<String, Object> comments = (Map<String, Object>) data.get("comments");
                Map<String, Object> summary = (Map<String, Object>) comments.get("summary");
                response.setCommentCount(Long.valueOf(summary.get("total_count").toString()));
            } else {
                response.setCommentCount(0L);
            }
            
            response.setShareCount(data.get("shares") != null ? Long.valueOf(data.get("shares").toString()) : 0L);
            response.setViewCount(data.get("views") != null ? Long.valueOf(data.get("views").toString()) : 0L);
            response.setThumbnailUrl("https://graph.facebook.com/" + videoId + "/picture");
            
            if (data.get("from") != null) {
                Map<String, Object> from = (Map<String, Object>) data.get("from");
                response.setAuthor((String) from.get("name"));
            }
            
            response.setCreatedAt((String) data.get("created_time"));
            response.setErrorMessage("");
            response.setErrorMessage("Facebook API integration pending - using demo data");
            
        } catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage("Facebook service error: " + e.getMessage());
        }
        
        return response;
    }

    @Override
    public String extractMediaId(String mediaUrl) {
        if (mediaUrl == null) return null;
        
        try {
            // Extract from: https://www.facebook.com/watch/?v=123456789
            if (mediaUrl.contains("v=")) {
                String[] parts = mediaUrl.split("v=");
                if (parts.length > 1) {
                    return parts[1].split("&")[0];
                }
            }
            // Extract from: https://www.facebook.com/username/videos/123456789/
            else if (mediaUrl.contains("/videos/")) {
                String[] parts = mediaUrl.split("/videos/");
                if (parts.length > 1) {
                    return parts[1].split("/")[0].split("\\?")[0];
                }
            }
            else if (mediaUrl.contains("fb.watch/")) {
                String[] parts = mediaUrl.split("fb.watch/");
                if (parts.length > 1) {
                    return parts[1].split("/")[0].split("\\?")[0];
                }
            }
        } catch (Exception e) {
            return null;
        }
        
        return null;
    }
    
    
}
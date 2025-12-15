package com.watershedmahotsav.socialmediaapi.service;

import com.watershedmahotsav.socialmediaapi.dto.InstagramResponse;
import com.watershedmahotsav.socialmediaapi.dto.VideoStatsResponse;

import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InstagramServiceImpl implements SocialMediaService {
	
	@Value("${instagram.access.token}")
    private String accessToken;
	
	@Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean supports(String platform) {
        return "instagram".equalsIgnoreCase(platform);
    }

    @SuppressWarnings("unchecked")
    @Override
    public VideoStatsResponse getMediaStats(String mediaUrl) {
    	InstagramResponse response = new InstagramResponse();
    	
        try {

        	String mediaId = extractMediaId(mediaUrl);
            
            if (mediaId == null) {
            	response.setSuccess(false);
                response.setErrorMessage("Invalid Instagram URL");
                return response;
            }
            
         // Instagram Graph API endpoint
            String apiUrl = "https://graph.instagram.com/" + mediaId + 
                           "?fields=id,caption,media_type,media_url,permalink,timestamp,like_count,comments_count&access_token=" + accessToken;

            ResponseEntity<Map> instagramResponse = restTemplate.exchange(
                apiUrl, HttpMethod.GET, null, Map.class);

            if (instagramResponse.getStatusCode() != HttpStatus.OK) {
                response.setSuccess(false);
                response.setErrorMessage("Instagram API error: " + instagramResponse.getStatusCode());
                return response;
            }

            Map<String, Object> data = instagramResponse.getBody();
            
            response.setSuccess(true);
            response.setMediaId((String) data.get("id"));
            response.setCaption((String) data.get("caption"));
            response.setLikes(data.get("like_count") != null ? Long.valueOf(data.get("like_count").toString()) : 0L);
            response.setCommentCount(data.get("comments_count") != null ? Long.valueOf(data.get("comments_count").toString()) : 0L);
            response.setMediaUrl((String) data.get("permalink"));
            response.setAuthor("Instagram User"); // Would need separate user API call
            response.setCreatedAt((String) data.get("timestamp"));
            response.setErrorMessage("");
            
        } catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage("Instagram service error: " + e.getMessage());
        }
        
        return response;
    }

    @Override
    public String extractMediaId(String mediaUrl) {
        if (mediaUrl == null) return null;
        
        try {
            // Extract from: https://www.instagram.com/p/ABC123/
            if (mediaUrl.contains("/p/")) {
                String[] parts = mediaUrl.split("/p/");
                if (parts.length > 1) {
                    return parts[1].split("/")[0].split("\\?")[0];
                }
            }
            // Extract from: https://www.instagram.com/reel/ABC123/
            else if (mediaUrl.contains("/reel/")) {
                String[] parts = mediaUrl.split("/reel/");
                if (parts.length > 1) {
                    return parts[1].split("/")[0].split("\\?")[0];
                }
            }
            // Extract from: https://www.instagram.com/tv/ABC123/
            else if (mediaUrl.contains("/tv/")) {
                String[] parts = mediaUrl.split("/tv/");
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
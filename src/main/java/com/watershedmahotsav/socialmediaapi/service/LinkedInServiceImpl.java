package com.watershedmahotsav.socialmediaapi.service;

import com.watershedmahotsav.socialmediaapi.dto.LinkedInResponse;
import com.watershedmahotsav.socialmediaapi.dto.VideoStatsResponse;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LinkedInServiceImpl implements SocialMediaService {
	
	@Value("${linkedin.access.token:}")
    private String accessToken;
	
/*	@Value("${linkedin.client.id:}")
    private String clientId;

    @Value("${linkedin.client.secret:}")
    private String clientSecret;

    @Value("${linkedin.access.token:}")
    private String accessToken;
*/

    private final RestTemplate restTemplate;

    public LinkedInServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean supports(String platform) {
        return "linkedin".equalsIgnoreCase(platform);
    }

    @Override
    public VideoStatsResponse getMediaStats(String mediaUrl) {
    	LinkedInResponse response = new LinkedInResponse();
        
        try {
        	String postId = extractMediaId(mediaUrl);
            if (postId == null) {
                response.setSuccess(false);
                response.setErrorMessage("Invalid LinkedIn URL");
                return response;
            }
            
            if (!isAccessTokenConfigured()) {
                response.setSuccess(false);
                response.setErrorMessage("LinkedIn access token not configured. Please set linkedin.access.token in application.properties");
                return response;
            }
            
         // LinkedIn API endpoint for post statistics
            String apiUrl = "https://api.linkedin.com/v2/socialActions/" + postId;

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> linkedInResponse = restTemplate.exchange(
                apiUrl, HttpMethod.GET, entity, Map.class);

            if (linkedInResponse.getStatusCode() != HttpStatus.OK) {
                response.setSuccess(false);
                response.setErrorMessage("LinkedIn API error: " + linkedInResponse.getStatusCode());
                return response;
            }

            Map<String, Object> data = linkedInResponse.getBody();
            
            // Extract data from LinkedIn API response
            response.setSuccess(true);
            response.setPostId(postId);
            response.setText(extractText(data));
            response.setLikes(extractLikes(data));
            response.setCommentCount(extractCommentCount(data));
            response.setShareCount(extractShareCount(data));
            response.setViewCount(extractViewCount(data));
            response.setAuthor(extractAuthor(data));
            
        } catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage("LinkedIn service error: " + e.getMessage());
        }
        
        return response;
    }

    @Override
    public String extractMediaId(String mediaUrl) {
        if (mediaUrl == null) return null;
        
        try {
            
            // Format 1: https://www.linkedin.com/feed/update/urn:li:activity:123456789/
            if (mediaUrl.contains("urn:li:activity:")) {
                String[] parts = mediaUrl.split("urn:li:activity:");
                if (parts.length > 1) {
                    String activityId = parts[1].split("[?&#/]")[0];
                    return "urn:li:activity:" + activityId;
                }
            }
            
            // Format 2: https://www.linkedin.com/posts/username_activity-123456789
            else if (mediaUrl.contains("/posts/") && mediaUrl.contains("activity-")) {
                String[] parts = mediaUrl.split("/posts/");
                if (parts.length > 1) {
                    String activityPart = parts[1].split("[?&#]")[0];
                    if (activityPart.contains("activity-")) {
                        String activityId = activityPart.split("activity-")[1];
                        return "urn:li:activity:" + activityId;
                    }
                }
            }
            
            // Format 3: https://www.linkedin.com/posts/username_post-id-123456789
            else if (mediaUrl.contains("/posts/")) {
                String[] parts = mediaUrl.split("/posts/");
                if (parts.length > 1) {
                    String postPart = parts[1].split("[?&#]")[0];
                    // Extract numeric ID from the post part
                    String numericId = extractNumericId(postPart);
                    if (!numericId.isEmpty()) {
                        return "urn:li:activity:" + numericId;
                    }
                }
            }
            
            // Format 4: Direct URN
            else if (mediaUrl.startsWith("urn:li:activity:")) {
                return mediaUrl.split("[?&#]")[0];
            }
            
        } catch (Exception e) {
            System.err.println("Error extracting LinkedIn post ID from: " + mediaUrl);
            System.err.println("Error: " + e.getMessage());
        }
        
        return null;
    }

    private String extractNumericId(String input) {
        // Extract only numbers from the string
        return input.replaceAll("[^0-9]", "");
    }

    private boolean isAccessTokenConfigured() {
        return accessToken != null && 
               !accessToken.trim().isEmpty() && 
               !accessToken.equals("your_linkedin_access_token");
    }

    private String extractText(Map<String, Object> data) {
        try {
            // LinkedIn API response structure for socialActions
            if (data.containsKey("content")) {
                Map<String, Object> content = (Map<String, Object>) data.get("content");
                if (content.containsKey("article")) {
                    Map<String, Object> article = (Map<String, Object>) content.get("article");
                    if (article.containsKey("title")) {
                        return (String) article.get("title");
                    }
                }
            }
            
            // Alternative: Check for commentary text
            if (data.containsKey("commentary") && data.get("commentary") instanceof Map) {
                Map<String, Object> commentary = (Map<String, Object>) data.get("commentary");
                if (commentary.containsKey("text")) {
                    return (String) commentary.get("text");
                }
            }
        } catch (Exception e) {
            System.err.println("Error extracting text: " + e.getMessage());
        }
        return "LinkedIn Post";
    }

    private Long extractLikes(Map<String, Object> data) {
        try {
            if (data.containsKey("likesSummary")) {
                Map<String, Object> likesSummary = (Map<String, Object>) data.get("likesSummary");
                if (likesSummary != null && likesSummary.containsKey("totalLikes")) {
                    Object totalLikes = likesSummary.get("totalLikes");
                    return totalLikes != null ? Long.valueOf(totalLikes.toString()) : 0L;
                }
            }
            
            // Alternative: aggregatedLikes
            if (data.containsKey("aggregatedLikes")) {
                Object aggregatedLikes = data.get("aggregatedLikes");
                return aggregatedLikes != null ? Long.valueOf(aggregatedLikes.toString()) : 0L;
            }
        } catch (Exception e) {
            System.err.println("Error extracting likes: " + e.getMessage());
        }
        return 0L;
    }

    private Long extractCommentCount(Map<String, Object> data) {
        try {
            if (data.containsKey("commentsSummary")) {
                Map<String, Object> commentsSummary = (Map<String, Object>) data.get("commentsSummary");
                if (commentsSummary != null && commentsSummary.containsKey("totalFirstLevelComments")) {
                    Object totalComments = commentsSummary.get("totalFirstLevelComments");
                    return totalComments != null ? Long.valueOf(totalComments.toString()) : 0L;
                }
            }
        } catch (Exception e) {
            System.err.println("Error extracting comment count: " + e.getMessage());
        }
        return 0L;
    }

    private Long extractShareCount(Map<String, Object> data) {
        try {
            // LinkedIn typically doesn't provide share count directly
            // You might need to use a different endpoint or approach
            return 0L; // LinkedIn API limitation
        } catch (Exception e) {
            System.err.println("Error extracting share count: " + e.getMessage());
        }
        return 0L;
    }

    private Long extractViewCount(Map<String, Object> data) {
        try {
            if (data.containsKey("viewsSummary")) {
                Map<String, Object> viewsSummary = (Map<String, Object>) data.get("viewsSummary");
                if (viewsSummary != null && viewsSummary.containsKey("viewCounts")) {
                    Map<String, Object> viewCounts = (Map<String, Object>) viewsSummary.get("viewCounts");
                    if (viewCounts != null && viewCounts.containsKey("total")) {
                        Object totalViews = viewCounts.get("total");
                        return totalViews != null ? Long.valueOf(totalViews.toString()) : 0L;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error extracting view count: " + e.getMessage());
        }
        return 0L;
    }

    private String extractAuthor(Map<String, Object> data) {
        try {
            if (data.containsKey("actor")) {
                String actor = (String) data.get("actor");
                if (actor != null && actor.startsWith("urn:li:person:")) {
                    // For production, you'd make another API call to get person details
                    return "LinkedIn Member";
                }
                return actor;
            }
        } catch (Exception e) {
            System.err.println("Error extracting author: " + e.getMessage());
        }
        return "LinkedIn Member";
    }
    
}
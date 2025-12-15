package com.watershedmahotsav.socialmediaapi.service;

import com.watershedmahotsav.socialmediaapi.dto.TwitterResponse;
import com.watershedmahotsav.socialmediaapi.dto.VideoStatsResponse;

import java.util.Map;

import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TwitterServiceImpl implements SocialMediaService {
	
	@Value("${twitter.bearer.token}")
    private String bearerToken;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean supports(String platform) {
        return "twitter".equalsIgnoreCase(platform);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Cacheable(value = "twitterStats", key = "#mediaUrl", unless = "#result.success == false")
    public VideoStatsResponse getMediaStats(String mediaUrl) {
    	TwitterResponse response = new TwitterResponse();
        
        try {
        	
        	String tweetId = extractMediaId(mediaUrl);
        	
            if (tweetId == null) {
                response.setSuccess(false);
                response.setErrorMessage("Invalid Twitter URL");
                return response;
            }
            
            System.out.println("🔍 Fetching Twitter data for tweet: " + tweetId);
            
         // Twitter API v2 endpoint
            String apiUrl = "https://api.twitter.com/2/tweets/" + tweetId + 
                    "?tweet.fields=public_metrics,author_id,created_at,text,context_annotations,entities,attachments,geo,lang,source,conversation_id,referenced_tweets,in_reply_to_user_id&expansions=author_id,attachments.media_keys&user.fields=created_at,description,location,name,username,verified&media.fields=duration_ms,height,preview_image_url,public_metrics,type,url,width,alt_text";
            

            // Create headers with Bearer token
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(bearerToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Make API call
            ResponseEntity<Map> twitterResponse = restTemplate.exchange(
                apiUrl, HttpMethod.GET, entity, Map.class);

            System.out.println("📡 Twitter API Response Status: " + twitterResponse.getStatusCode());

            if (twitterResponse.getStatusCode() != HttpStatus.OK) {
                response.setSuccess(false);
                response.setErrorMessage("Twitter API returned: " + twitterResponse.getStatusCode());
                return response;
            }

            Map<String, Object> responseBody = twitterResponse.getBody();
            Map<String, Object> data = (Map<String, Object>) responseBody.get("data");
            
            if (data == null) {
                response.setSuccess(false);
                response.setErrorMessage("Tweet not found or private");
                return response;
            }

            // Extract metrics
            Map<String, Object> publicMetrics = (Map<String, Object>) data.get("public_metrics");
            
            response.setSuccess(true);
            response.setTweetId(tweetId);
            response.setText(truncateText((String) data.get("text")));
            response.setLikes(getLongValue(publicMetrics, "like_count"));
            response.setRetweetCount(getLongValue(publicMetrics, "retweet_count"));
            response.setReplyCount(getLongValue(publicMetrics, "reply_count"));
            response.setViewCount(getLongValue(publicMetrics, "impression_count"));
            response.setBookmarkCount(getLongValue(publicMetrics, "bookmark_count"));
            response.setQuoteCount(getLongValue(publicMetrics, "quote_count"));
            
            if (data.get("created_at") != null) {
                response.setCreatedAt((String) data.get("created_at"));
            }
            
            Map<String, Object> includes = (Map<String, Object>) responseBody.get("includes");
            if (includes != null) {
                java.util.List<Map<String, Object>> users = (java.util.List<Map<String, Object>>) includes.get("users");
                if (users != null && !users.isEmpty()) {
                    Map<String, Object> author = users.get(0);
                    response.setAuthor("@" + author.get("username") + " (" + author.get("name") + ")");
                }
            } else {
                response.setAuthor("User ID: " + data.get("author_id"));
            }
            
            response.setErrorMessage("");
            
        } catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage("Twitter service error: " + e.getMessage());
        }
        
        return response;
    }

    @Override
    public String extractMediaId(String mediaUrl) {
        if (mediaUrl == null) return null;
        
        try {
            // Extract from: https://twitter.com/user/status/123456789
            // or https://x.com/user/status/123456789
        	if (mediaUrl.contains("/status/")) {
                String[] parts = mediaUrl.split("/status/");
                if (parts.length > 1) {
                    String tweetId = parts[1].split("/")[0].split("\\?")[0];
                    // Validate Twitter ID format (numeric)
                    if (tweetId.matches("\\d+")) {
                        return tweetId;
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        
        return null;
    }
    
    private Long getLongValue(Map<String, Object> metrics, String key) {
        if (metrics != null && metrics.get(key) != null) {
            return Long.valueOf(metrics.get(key).toString());
        }
        return 0L;
    }

    private String truncateText(String text) {
        if (text != null && text.length() > 200) {
            return text.substring(0, 200) + "...";
        }
        return text;
    }
    
}
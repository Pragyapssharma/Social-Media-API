package com.watershedmahotsav.socialmediaapi.service;

import com.watershedmahotsav.socialmediaapi.dto.VideoStatsResponse;
import com.watershedmahotsav.socialmediaapi.dto.YouTubeResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class YouTubeServiceImpl implements SocialMediaService {

    @Value("${youtube.api.key}")
    private String apiKey;

    @Override
    public boolean supports(String platform) {
        return "youtube".equalsIgnoreCase(platform);
    }

    @Override
    public VideoStatsResponse getMediaStats(String mediaUrl) {
    	YouTubeResponse response = new YouTubeResponse();
        
        try {
            String videoId = extractMediaId(mediaUrl);
            if (videoId == null) {
            	response.setSuccess(false);
                response.setErrorMessage("Invalid YouTube URL");
                return response;
            }

            // Validate API key
            if (apiKey == null || apiKey.trim().isEmpty() || apiKey.equals("realkey")) {
                return new VideoStatsResponse(false, "YouTube API key not configured");
            }

            YouTube youtube = new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                null
            ).setApplicationName("social-media-api").build();

            YouTube.Videos.List videoRequest = youtube.videos()
                .list("snippet,statistics,contentDetails")
                .setId(videoId)
                .setKey(apiKey);

            VideoListResponse videoResponse = videoRequest.execute();
            
            if (videoResponse.getItems().isEmpty()) {
                response.setSuccess(false);
                response.setErrorMessage("Video not found or private");
                return response;
            }

            Video video = videoResponse.getItems().get(0);
            
            String channelId = video.getSnippet().getChannelId();
            Long subscriberCount = getSubscriberCount(youtube, channelId);
            
            // Populate response
            response.setSuccess(true);
            response.setVideoId(videoId);
            response.setTitle(video.getSnippet().getTitle());
            response.setDescription(video.getSnippet().getDescription());
            response.setLikes(getLongValue(video.getStatistics().getLikeCount()));
            response.setDislikes(getLongValue(video.getStatistics().getDislikeCount()));
            response.setViews(getLongValue(video.getStatistics().getViewCount()));
            response.setCommentCount(getLongValue(video.getStatistics().getCommentCount()));
            response.setThumbnailUrl(video.getSnippet().getThumbnails().getDefault().getUrl());
            response.setAuthor(video.getSnippet().getChannelTitle());
            response.setSubscriberCount(subscriberCount);
            if (video.getSnippet().getPublishedAt() != null) {
                response.setPublishedAt(video.getSnippet().getPublishedAt().toString());
            }

        } catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage("Error fetching YouTube data: " + e.getMessage());
        }
        
        return response;
    }

    @Override
    public String extractMediaId(String mediaUrl) {
        if (mediaUrl == null || mediaUrl.trim().isEmpty()) {
            return null;
        }
        
        String videoId = null;
        
        try {
            if (mediaUrl.contains("youtube.com/watch?v=")) {
                String[] parts = mediaUrl.split("v=");
                if (parts.length > 1) {
                    videoId = parts[1].split("&")[0];
                }
            } else if (mediaUrl.contains("youtube.com/shorts/")) {
                String[] parts = mediaUrl.split("shorts/");
                if (parts.length > 1) {
                    videoId = parts[1].split("\\?")[0];
                }
            } else if (mediaUrl.contains("youtu.be/")) {
                String[] parts = mediaUrl.split("youtu.be/");
                if (parts.length > 1) {
                    videoId = parts[1].split("\\?")[0];
                }
            } else if (mediaUrl.matches("[a-zA-Z0-9_-]{11}")) {
                videoId = mediaUrl;
            }
            
            if (videoId != null && videoId.length() == 11 && videoId.matches("[a-zA-Z0-9_-]+")) {
                return videoId;
            } else {
                return null;
            }
            
        } catch (Exception e) {
            return null;
        }
    }
    
    private Long getSubscriberCount(YouTube youtube, String channelId) {
        try {
            if (channelId == null || channelId.trim().isEmpty()) {
                return 0L;
            }

            // Get channel statistics
            YouTube.Channels.List channelRequest = youtube.channels()
                .list("statistics")
                .setId(channelId)
                .setKey(apiKey);

            ChannelListResponse channelResponse = channelRequest.execute();
            
            if (!channelResponse.getItems().isEmpty()) {
                Channel channel = channelResponse.getItems().get(0);
                return getLongValue(channel.getStatistics().getSubscriberCount());
            }
            
        } catch (Exception e) {
            System.err.println("Error fetching subscriber count: " + e.getMessage());
        }
        
        return 0L;
    }
    
    private Long getLongValue(BigInteger value) {
        return value != null ? value.longValue() : 0L;
    }
}
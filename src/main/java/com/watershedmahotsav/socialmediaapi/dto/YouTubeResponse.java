package com.watershedmahotsav.socialmediaapi.dto;

public class YouTubeResponse extends VideoStatsResponse {
	
	private String videoId;
    private String title;
    private String description;
    private Long likes;
    private Long dislikes;
    private Long views;
    private Long commentCount;
    private String thumbnailUrl;
    private String author;
    private String publishedAt;
    private Long subscriberCount;
	
    public YouTubeResponse() {
        this.platform = "youtube";
    }
    
    public YouTubeResponse(String videoId, String title, String description, Long likes, Long dislikes, Long views,
			Long commentCount, String thumbnailUrl, String author, String publishedAt, Long subscriberCount) {
		this.videoId = videoId;
		this.title = title;
		this.description = description;
		this.likes = likes;
		this.dislikes = dislikes;
		this.views = views;
		this.commentCount = commentCount;
		this.thumbnailUrl = thumbnailUrl;
		this.author = author;
		this.publishedAt = publishedAt;
		this.subscriberCount = subscriberCount;
	}



	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getLikes() {
		return likes;
	}
	public void setLikes(Long likes) {
		this.likes = likes;
	}
	public Long getDislikes() {
		return dislikes;
	}
	public void setDislikes(Long dislikes) {
		this.dislikes = dislikes;
	}
	public Long getViews() {
		return views;
	}
	public void setViews(Long views) {
		this.views = views;
	}
	public Long getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublishedAt() {
		return publishedAt;
	}
	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}
	
	public Long getSubscriberCount() {
		return subscriberCount;
	}

	public void setSubscriberCount(Long subscriberCount) {
		this.subscriberCount = subscriberCount;
	}

	@Override
	public String toString() {
		return "YouTubeResponse [videoId=" + videoId + ", title=" + title + ", description=" + description + ", likes="
				+ likes + ", dislikes=" + dislikes + ", views=" + views + ", commentCount=" + commentCount
				+ ", thumbnailUrl=" + thumbnailUrl + ", author=" + author + ", publishedAt=" + publishedAt + ", subscriberCount=" + subscriberCount +"]";
	}
	
	
}

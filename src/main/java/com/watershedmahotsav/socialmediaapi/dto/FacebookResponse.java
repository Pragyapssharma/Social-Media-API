package com.watershedmahotsav.socialmediaapi.dto;

public class FacebookResponse extends VideoStatsResponse {
	
	private String videoId;
    private String description;
    private Long likes;
    private Long commentCount;
    private Long shareCount;
    private Long viewCount;
    private String thumbnailUrl;
    private String author;
    private String createdAt;
    
    public FacebookResponse() {
        this.platform = "facebook";
    }

	public FacebookResponse(String videoId, String description, Long likes, Long commentCount, Long shareCount,
			Long viewCount, String thumbnailUrl, String author, String createdAt) {
		super();
		this.videoId = videoId;
		this.description = description;
		this.likes = likes;
		this.commentCount = commentCount;
		this.shareCount = shareCount;
		this.viewCount = viewCount;
		this.thumbnailUrl = thumbnailUrl;
		this.author = author;
		this.createdAt = createdAt;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
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

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	public Long getShareCount() {
		return shareCount;
	}

	public void setShareCount(Long shareCount) {
		this.shareCount = shareCount;
	}

	public Long getViewCount() {
		return viewCount;
	}

	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "FacebookResponse [videoId=" + videoId + ", description=" + description + ", likes=" + likes
				+ ", commentCount=" + commentCount + ", shareCount=" + shareCount + ", viewCount=" + viewCount
				+ ", thumbnailUrl=" + thumbnailUrl + ", author=" + author + ", createdAt=" + createdAt + "]";
	}

    
}

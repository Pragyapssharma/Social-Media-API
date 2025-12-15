package com.watershedmahotsav.socialmediaapi.dto;

public class InstagramResponse extends VideoStatsResponse {

	private String mediaId;
    private String caption;
    private Long likes;
    private Long commentCount;
    private Long shareCount;
    private Long viewCount;
    private String mediaUrl;
    private String author;
    private String createdAt;
    
    public InstagramResponse() {
        this.platform = "instagram";
    }

	public InstagramResponse(String mediaId, String caption, Long likes, Long commentCount, Long shareCount,
			Long viewCount, String mediaUrl, String author, String createdAt) {
		super();
		this.mediaId = mediaId;
		this.caption = caption;
		this.likes = likes;
		this.commentCount = commentCount;
		this.shareCount = shareCount;
		this.viewCount = viewCount;
		this.mediaUrl = mediaUrl;
		this.author = author;
		this.createdAt = createdAt;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
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

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
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
		return "InstagramResponse [mediaId=" + mediaId + ", caption=" + caption + ", likes=" + likes + ", commentCount="
				+ commentCount + ", shareCount=" + shareCount + ", viewCount=" + viewCount + ", mediaUrl=" + mediaUrl
				+ ", author=" + author + ", createdAt=" + createdAt + "]";
	}

	
}

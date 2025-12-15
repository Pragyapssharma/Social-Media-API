package com.watershedmahotsav.socialmediaapi.dto;

public class LinkedInResponse extends VideoStatsResponse {
	
	private String postId;
    private String text;
    private Long likes;
    private Long commentCount;
    private Long shareCount;
    private Long viewCount;
    private String author;
    
    public LinkedInResponse() {
        this.platform = "linkedin";
    }
    
	public LinkedInResponse(String postId, String text, Long likes, Long commentCount, Long shareCount, Long viewCount,
			String author) {
		super();
		this.postId = postId;
		this.text = text;
		this.likes = likes;
		this.commentCount = commentCount;
		this.shareCount = shareCount;
		this.viewCount = viewCount;
		this.author = author;
	}


	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "LinkedInResponse [postId=" + postId + ", text=" + text + ", likes=" + likes + ", commentCount="
				+ commentCount + ", shareCount=" + shareCount + ", viewCount=" + viewCount + ", author=" + author + "]";
	}
    
	
}

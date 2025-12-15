package com.watershedmahotsav.socialmediaapi.dto;

public class TwitterResponse extends VideoStatsResponse {
	
	private String tweetId;
    private String text;
    private Long likes;
    private Long retweetCount;
    private Long replyCount;
    private Long viewCount;
    private Long bookmarkCount;
    private Long quoteCount;
    private String author;
    private String createdAt;
    
    
    public TwitterResponse() {
        this.platform = "twitter";
    }

	public TwitterResponse(String tweetId, String text, Long likes, Long retweetCount, Long replyCount, Long viewCount,
			Long bookmarkCount, Long quoteCount, String author, String createdAt) {
		this.tweetId = tweetId;
		this.text = text;
		this.likes = likes;
		this.retweetCount = retweetCount;
		this.replyCount = replyCount;
		this.viewCount = viewCount;
		this.bookmarkCount = bookmarkCount;
		this.quoteCount = quoteCount;
		this.author = author;
		this.createdAt = createdAt;
	}

	public String getTweetId() {
		return tweetId;
	}

	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
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

	public Long getRetweetCount() {
		return retweetCount;
	}

	public void setRetweetCount(Long retweetCount) {
		this.retweetCount = retweetCount;
	}

	public Long getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Long replyCount) {
		this.replyCount = replyCount;
	}

	public Long getViewCount() {
		return viewCount;
	}

	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}

	public Long getBookmarkCount() {
		return bookmarkCount;
	}

	public void setBookmarkCount(Long bookmarkCount) {
		this.bookmarkCount = bookmarkCount;
	}

	public Long getQuoteCount() {
		return quoteCount;
	}

	public void setQuoteCount(Long quoteCount) {
		this.quoteCount = quoteCount;
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
		return "TwitterResponse [tweetId=" + tweetId + ", text=" + text + ", likes=" + likes + ", retweetCount="
				+ retweetCount + ", replyCount=" + replyCount + ", viewCount=" + viewCount + ", bookmarkCount="
				+ bookmarkCount + ", quoteCount=" + quoteCount + ", author=" + author + ", createdAt=" + createdAt
				+ "]";
	}

	
}

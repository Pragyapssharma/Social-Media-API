package com.watershedmahotsav.socialmediaapi.dto;

public class VideoStatsResponse {
	protected boolean success;
    protected String platform;
    protected String errorMessage;


    public VideoStatsResponse() {}

    public VideoStatsResponse(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	@Override
	public String toString() {
		return "VideoStatsResponse [success=" + success + ", platform=" + platform + ", errorMessage=" + errorMessage
				+ "]";
	}

}
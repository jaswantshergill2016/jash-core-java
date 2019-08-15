package io.reactivestax.commons.utils.dto;


import java.util.List;

/**
 * 
 * Represents the container ErrorInfo object that will be returned to the
 * calling client whenever there's an error (business or system related) in the
 * called api
 *
 */
public class ErrorInfo {

	private String url;
	private String httpStatusCode;
	private String statusCode;
	private String statusDesc;
	private String httpStatusMessage;
	private List<DetailedMessage> detailedMessages;

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	/**
	 * Basic contructor for ErrorInfo class
	 */
	public ErrorInfo() {
		super();
	}

	public String getHttpStatusMessage() {
		return httpStatusMessage;
	}

	public void setHttpStatusMessage(String httpStatusMessage) {
		this.httpStatusMessage = httpStatusMessage;
	}

	public String getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(String httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public List<DetailedMessage> getDetailedMessages() {
		return detailedMessages;
	}

	public void setDetailedMessages(List<DetailedMessage> detailedMessages) {
		this.detailedMessages = detailedMessages;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

}
package com.member.rest.exception;

import org.springframework.http.HttpStatus;

/**
 * This class enable the construction of a {@link MessageDTO} using the error
 * message, the type of the error and the HTTPStatus corresponding to the
 * exception
 * 
 * @author Imen Gharsalli
 *
 */
public class MessageDTO {
	private String message;
	private MessageType type;
	private HttpStatus status;

	/**
	 * 
	 */
	public MessageDTO() {
		super();
	}

	/**
	 * 
	 * @param type
	 * @param message
	 * @param status
	 */
	public MessageDTO(MessageType type, String message, HttpStatus status) {
		super();
		this.message = message;
		this.type = type;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
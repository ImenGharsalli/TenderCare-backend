package com.member.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * THis class constructs an {@link InvalidMemberException} to be thrown each
 * time an invalid member is provided for an update operation
 * 
 * @author Imen Gharsalli
 *
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidMemberException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidMemberException(Throwable ex) {
		super(ex);
	}
}

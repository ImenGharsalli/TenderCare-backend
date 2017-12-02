package com.member.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * {@link UserNotFoundException} should be thrown each time there is no account
 * corresponding to the given user.
 * 
 * @author Imen Gharsalli
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	/**
	 * Constructs a {@link UserNotFoundException} with an error message that
	 * includes the username
	 * 
	 * @param memberId
	 */
	public UserNotFoundException(String username) {
		super("could not find any account with the username '" + username + "'.");
	}
}

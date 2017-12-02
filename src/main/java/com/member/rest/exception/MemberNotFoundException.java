package com.member.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Enables the construction of a {@link MemberNotFoundException} which is to be
 * thrown each time no member corresponds to the given id.
 * 
 * @author Imen Gharsalli
 *
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MemberNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8200174473905262857L;

	/**
	 * Constructs a MemberNotFoundException to be thrown is there was no member
	 * correponding to the given memberId
	 * 
	 * @param memberId
	 *            the id of the member to find
	 */
	public MemberNotFoundException(Long memberId) {
		super("Could not find any member with id '" + memberId + "'.");
	}

	/**
	 * Constructs a MemberNotFoundException each time an empty body is provided
	 * for the creation of a new member. The message indicates that thereis no
	 * member to save and gives the list of the mendatory fields to include in any instance to be saved.
	 */
	public MemberNotFoundException() {
		super("Please provide the member you wish to save with the following (required) fields : firstname, lastname, birthdate and zip");
	}
}

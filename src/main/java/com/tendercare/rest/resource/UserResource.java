package com.tendercare.rest.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.tendercare.model.User;
import com.tendercare.rest.UserRestController;

/**
 * 
 * @author Imen Gharsalli
 *
 */
public class UserResource extends ResourceSupport {

	private final User user;

	/**
	 * 
	 * @param user
	 */
	public UserResource(User user) {
		this.user = user;
		this.add(linkTo(UserRestController.class).withRel("Users"));
		this.add(linkTo(methodOn(UserRestController.class).readUser(user.getId())).withSelfRel());
	}

	/**
	 * 
	 * @return
	 */
	public User getUser() {
		return user;
	}
}

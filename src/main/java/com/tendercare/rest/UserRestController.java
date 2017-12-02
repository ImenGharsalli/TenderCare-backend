package com.tendercare.rest;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tendercare.model.User;
import com.tendercare.rest.resource.UserResource;
import com.tendercare.service.UserService;

/**
 * 
 * @author Imen Gharsalli
 *
 */
@RestController
@RequestMapping("/api/users")
public class UserRestController {

	private final Logger LOG = LoggerFactory.getLogger(UserRestController.class);
	private UserService userService;

	/**
	 * 
	 * @param userService
	 */
	UserRestController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody User user) {
		LOG.debug("REST request to create a new user");
		User outputUser = userService.createUser(user);
		return new ResponseEntity<UserResource>(new UserResource(outputUser), (HttpStatus.CREATED));

	}

	/**
	 * 
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@GetMapping(produces = "application/json")
	public ResponseEntity<?> readUsers() {
		LOG.debug("REST request to list all users");
		Iterable<UserResource> userResourceList = StreamSupport.stream(userService.readUsers().spliterator(), false)
				.map(UserResource::new).collect(Collectors.toList());
		return new ResponseEntity<>(new Resources<>(userResourceList), (HttpStatus.OK));
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/{userId}")
	public ResponseEntity<?> readUser(@PathVariable Long userId) {
		LOG.debug("REST request to read user with id {}" + userId);
		User user = userService.readUser(userId);
		return new ResponseEntity<UserResource>(new UserResource(user), (HttpStatus.OK));
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@DeleteMapping(value = "/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
		LOG.debug("REST request to delete user with id {}" + userId);
		userService.deleteUser(userId);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	/**
	 * 
	 * @param user
	 * @param userId
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@PutMapping(value = "/{userId}")
	public ResponseEntity<?> UpdateUser(@PathVariable User user, @PathVariable Long userId) {
		LOG.debug("REST request to update user with id {}" + userId);
		userService.updateUser(user, userId);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}

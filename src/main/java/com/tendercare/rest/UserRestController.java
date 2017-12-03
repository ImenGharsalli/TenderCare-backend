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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The RestController that handles User related HTTP Requests, including GET,
 * POST, PUT and DELETE
 * 
 * @author Imen Gharsalli
 *
 */
@RestController
@RequestMapping("/api/users")
@Api("/api/users")
public class UserRestController {

	private final Logger LOG = LoggerFactory.getLogger(UserRestController.class);
	private UserService userService;

	/**
	 * Constructs the UserRestController by initializing the UserService that
	 * will be used by each Rest Service in it.
	 * 
	 * @param userService
	 */
	UserRestController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 
	 * @param user
	 *            The user to save
	 * @return a ResponseEntity which includes a Resource of the saved User as
	 *         well as the corresponding HttpStatus
	 */
	@PostMapping
	@ApiOperation(value = "Create a new User", notes = "Saving a new User", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = 201, message = "Created", response = ResponseEntity.class),
			@ApiResponse(code = 400, message = "Bad request", response = ResponseEntity.class) })
	public ResponseEntity<?> createUser(@RequestBody User user) {
		LOG.debug("REST request to create a new user");
		User outputUser = userService.createUser(user);
		return new ResponseEntity<UserResource>(new UserResource(outputUser), (HttpStatus.CREATED));

	}

	/**
	 * Returns the list of Users that has been saved
	 * 
	 * @return all saved instances of User
	 */
	@CrossOrigin(origins = "*")
	@GetMapping(produces = "application/json")
	@ApiOperation(value = "Find all Users", notes = "Returning the list of all Users", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class) })
	public ResponseEntity<?> readUsers() {
		LOG.debug("REST request to list all users");
		Iterable<UserResource> userResourceList = StreamSupport.stream(userService.readUsers().spliterator(), false)
				.map(UserResource::new).collect(Collectors.toList());
		return new ResponseEntity<>(new Resources<>(userResourceList), (HttpStatus.OK));
	}

	/**
	 * Reads the User corresponding to the userId given as parameter
	 * 
	 * @param userId
	 * @return the ResponseEntity that includes the returned User as well as the
	 *         HttpStatus
	 */
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/{userId}")
	@ApiOperation(value = "Find one User", notes = "Returning the User corresponding to the given id", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "NotFound") })
	public ResponseEntity<?> readUser(@PathVariable Long userId) {
		LOG.debug("REST request to read user with id {}" + userId);
		User user = userService.readUser(userId);
		return new ResponseEntity<UserResource>(new UserResource(user), (HttpStatus.OK));
	}

	/**
	 * Given a userId, deletes the corresponding user
	 * 
	 * @param userId
	 *            the id of the user to be deleted
	 * @return responseEntity including HTTPStatus
	 */
	@CrossOrigin(origins = "*")
	@DeleteMapping(value = "/{userId}")
	@ApiOperation(value = "Delete a User", notes = "Deleting an existing User")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "NotFound") })
	public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
		LOG.debug("REST request to delete user with id {}" + userId);
		userService.deleteUser(userId);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	/**
	 * Given a userId, updates the corresponding user
	 * 
	 * @param user
	 *            the new user to use for the update
	 * @param userId
	 *            the id of the User to be updated
	 * @return the ResponseEntity including the HttpStatus
	 */
	@CrossOrigin(origins = "*")
	@PutMapping(value = "/{userId}")
	@ApiOperation(value = "Update a User", notes = "Updating an existing User")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "NotFound") })
	public ResponseEntity<?> UpdateUser(@PathVariable User user, @PathVariable Long userId) {
		LOG.debug("REST request to update user with id {}" + userId);
		userService.updateUser(user, userId);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}

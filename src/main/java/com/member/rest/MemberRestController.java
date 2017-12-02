package com.member.rest;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.member.model.Member;
import com.member.service.MemberService;

/**
 * Handles all HTTP requests that are supported by this api, including GET,
 * POST, PUT and DELETE. Accepts and responds in json.
 * 
 * @author Imen Gharsalli
 *
 */
@RestController
@RequestMapping("/api/{username}/members")
public class MemberRestController {
	private final Logger LOG = LoggerFactory.getLogger(MemberRestController.class);

	private final MemberService memberService;

	/**
	 * Consturcts the {@link MemberRestController}.
	 * 
	 * @param memberService
	 *            the service that will handle all HTTP requests of
	 *            {@link MemberRestController}
	 */
	MemberRestController(MemberService memberService) {
		this.memberService = memberService;
	}

	/**
	 * Returns the list of members corresponding to the given username.
	 * 
	 * @param username
	 *            username of the user corresponding to the use account
	 * @return responseEntity the response entity including the member resource
	 *         list and the HttpStatus
	 */
	@GetMapping(produces = "application/json")
	ResponseEntity<?> readMembers(@PathVariable String username) {
		LOG.debug("REST request to get all members");
		Iterable<MemberResource> memberResourceList = StreamSupport
				.stream(memberService.readMembers(username).spliterator(), false).map(MemberResource::new)
				.collect(Collectors.toList());
		return new ResponseEntity<>(new Resources<>(memberResourceList), (HttpStatus.OK));
	}

	/**
	 * Returns the member having the given memberId corresponding to the given
	 * username.
	 * 
	 * @param username
	 *            name of the user corresponding to the used account
	 * @param memberId
	 * @return responseEntity including HTTPStatus
	 */
	@GetMapping(value = "/{memberId}")
	ResponseEntity<?> readMember(@PathVariable String username, @PathVariable Long memberId) {
		LOG.debug("REST request to get member with id : {}" + memberId);
		Member member = this.memberService.readMember(memberId, username);
		return new ResponseEntity<MemberResource>(new MemberResource(member), (HttpStatus.OK));
	}

	/**
	 * Saving inputMember as new instance.
	 * 
	 * @param username
	 *            name of the user corresponding to the use account
	 * @param inputMember
	 *            the member to be created
	 * @return responseEntity including HTTPStatus
	 */
	@PostMapping
	ResponseEntity<?> createMember(@PathVariable String username, @RequestBody Member inputMember) {
		LOG.debug("REST request to save member : {}", inputMember.getFirstname() + " " + inputMember.getLastname());
		Member member = memberService.createMember(inputMember, username);
		return new ResponseEntity<MemberResource>(new MemberResource(member), (HttpStatus.CREATED));

	}

	/**
	 * 
	 * @param username
	 *            name of the user corresponding to the use account
	 * @param memberId
	 *            the id of the member to be deleted
	 * @return responseEntity including HTTPStatus
	 */

	@DeleteMapping(value = "/{memberId}")
	public ResponseEntity<?> deleteMember(@PathVariable Long memberId, @PathVariable String username) {
		LOG.debug("REST request to delete member with id : {}", memberId);
		memberService.deleteMember(memberId, username);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	/**
	 * Updates the member with the given memberId corresponding to the given
	 * username, by making use of the inputMember
	 * 
	 * @param memberId
	 *            the id of the member to be updated
	 * @param username
	 *            name of the user corresponding to the use account
	 * @param inputMember
	 *            the instance to use for the update of the member with the
	 *            given memberId
	 * @return responseEntity including HTTPStatus
	 */

	@PutMapping(value = "/{memberId}")
	public ResponseEntity<?> updateMember(@PathVariable String username, @PathVariable Long memberId,
			@RequestBody Member inputMember) {
		LOG.debug("REST request to update member with id : {}", memberId);
		memberService.updateMember(inputMember, username, memberId);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}

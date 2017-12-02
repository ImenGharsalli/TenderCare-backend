package com.member.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.member.model.Member;

/**
 * This class handles the creation of a {@link ResourceMember} corresponding to
 * each {@link Member}
 * 
 * @author Imen Gharsalli
 *
 */
public class MemberResource extends ResourceSupport {
	private final Member member;

	/**
	 * Creates a new {@link MemberResource} corresponding to the
	 * {@link Member} given as a parameter. This constructor adds links to the
	 * given member related to the member itself as well as the entire list of
	 * members.
	 * 
	 * @param member
	 *            the member corresponding to the resource to be instanciated
	 */
	public MemberResource(Member member) {
		String username = "imen";
		this.member = member;
		this.add(linkTo(MemberRestController.class, username).withRel("members"));
		this.add(linkTo(methodOn(MemberRestController.class, username).readMember(username, member.getId()))
				.withSelfRel());
	}

	/**
	 * Return the corresponding member.
	 * 
	 * @return member
	 */
	public Member getMember() {
		return member;
	}
}

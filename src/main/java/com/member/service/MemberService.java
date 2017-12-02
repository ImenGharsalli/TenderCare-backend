package com.member.service;

import java.util.Collection;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;

import com.member.dao.AccountRepository;
import com.member.dao.MemberRepository;
import com.member.model.Account;
import com.member.model.Member;
import com.member.rest.exception.InvalidMemberException;
import com.member.rest.exception.MemberNotFoundException;
import com.member.rest.exception.UserNotFoundException;

/**
 * Provides a list of operations to execute in order to handles members These
 * opeartion include creating updating, finding and deleting members.
 * 
 * @author Imen Gharsalli
 *
 */
@Controller
public class MemberService {

	MemberRepository memberRepository;
	AccountRepository accountRepository;

	/**
	 * Constructs an instance of MemberService
	 * @param memberRepository
	 * @param accountRepository
	 */
	public MemberService(MemberRepository memberRepository, AccountRepository accountRepository) {
		this.memberRepository = memberRepository;
		this.accountRepository = accountRepository;
	}

	/**
	 * returns the list of members corresponding to the given username
	 * @param username
	 * @return the list of members corresponding to the given username
	 */
	public Iterable<Member> readMembers(String username) {
		this.validateUser(username);
		return memberRepository.findAll();
	}

	/**
	 * Creates a new member
	 * @param member
	 * @param username
	 * @return the created member
	 */
	public Member createMember(Member member, String username) {
		Account acc = this.validateUser(username);
		checkMemberNull(member);
		return memberRepository.save(
				new Member(member.getFirstname(), member.getLastname(), member.getBirthdate(), member.getZip(), acc));
	}

	/**
	 * Returns the member corresponding to the given memberId
	 * @param memberId
	 * @param username
	 * @return member
	 */
	public Member readMember(Long memberId, String username) {
		this.validateUser(username);
		Member member = memberRepository.findOne(memberId);
		if (member == null)
			throw new MemberNotFoundException(memberId);
		return member;
	}

	/**
	 * Deletes the member corresponding the given memberId
	 * @param memberId
	 * @param username
	 */
	public void deleteMember(Long memberId, String username) {
		this.validateUser(username);
		try {
			memberRepository.delete(memberId);
		} catch (EmptyResultDataAccessException e) {
			throw new MemberNotFoundException(memberId);
		}
	}

	/**
	 * 
	 * @param firstname
	 * @return
	 */
	public Collection<Member> findByFirstname(String firstname, String lastname) {
		return memberRepository.findByFirstnameAndLastname(firstname, lastname);
	}

	/**
	 * 
	 * @param member
	 * @param lastname
	 */
	public void updateMember(Member member, String username, long memberId) {
		this.validateUser(username);
		Member outputMember = memberRepository.findOne(memberId);
		outputMember.setFirstname(member.getFirstname());
		outputMember.setLastname(member.getLastname());
		outputMember.setBirthdate(member.getBirthdate());
		outputMember.setZip(member.getZip());
		try {
			memberRepository.save(outputMember);
		} catch (UnexpectedRollbackException ex) {
			throw new InvalidMemberException(ex.getRootCause());
		}
	}

	/**
	 * 
	 * @param username
	 */
	private Account validateUser(String username) {
		return accountRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
	}

	private void checkMemberNull(Member member) {
		if (member.getFirstname() == null && member.getLastname() == null && member.getBirthdate() == null
				&& member.getZip() == 0)
			throw new MemberNotFoundException();
	}
}

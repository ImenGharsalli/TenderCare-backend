package com.member.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.member.model.Member;

/**
 * 
 * @author Imen Gharsalli
 *
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
	/**
	 * 
	 * @param firstname
	 * @return
	 */
	Collection<Member> findByFirstnameAndLastname(String firstname, String lastname);
}

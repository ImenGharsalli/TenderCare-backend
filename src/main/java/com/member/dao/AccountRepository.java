package com.member.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.member.model.Account;
/**
 * 
 * @author Imen Gharsalli
 *
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
	/**
	 *  
	 * @param username
	 * @return
	 */
	Optional<Account> findByUsername(String username);
}

package com.member.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Constructs an account from a username and a password
 * To each account corresponds a list of {@link Member}
 * @author Imen Gharsalli
 *
 */
@Entity
public class Account {
	@Id
	@GeneratedValue
	private Long id;

	@OneToMany(mappedBy = "account")
	private Set<Member> members = new HashSet<>();

	@JsonIgnore
	public String password;
	public String username;

	/**
	 * 
	 * @param username
	 * @param password
	 */
	public Account(String username, String password) {
		this.password = password;
		this.username = username;
	}

	public Account() {
		super();
	}

	public Long getId() {
		return id;
	}

	public Set<Member> getMembers() {
		return members;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

}

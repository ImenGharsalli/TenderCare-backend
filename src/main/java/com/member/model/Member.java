package com.member.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Consturcts a member with a firstname, a lastname, a zip, an account and a birthdate.
 * @author Imen Gharsalli
 *
 */
@Entity
public class Member {

	@Id
	@GeneratedValue
	private Long id;
	@NotNull(message = "error.firstname.notnull")
	@Size(min = 1, max = 30, message = "error.firstname.size")
	private String firstname;
	@NotNull(message = "error.lastname.notnull")
	@Size(min = 1, max = 30, message = "error.lastname.size")
	private String lastname;
	@NotNull(message = "error.birthdate.notnull")
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Past(message = "error.birthdate.past")
	private Date birthdate;
	@NumberFormat
	@NotNull(message = "error.zip.notnull")
	@Min(value=10000, message = "error.zip.size")
	@Max(value = 99999, message = "error.zip.size")
	private int zip;
	@JsonIgnore
	@ManyToOne
	private Account account;

	/**
	 * 
	 * @param firstname
	 * @param lastname
	 * @param birthdate
	 * @param zip
	 * @param account
	 */
	public Member(String firstname, String lastname, Date birthdate, int zip, Account account) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.zip = zip;
		this.account = account;
	}

	public Long getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public int getZip() {
		return zip;
	}

	public Account getAccount() {
		return account;
	}

	public Member() {
		super();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", birthdate="
				+ birthdate.getTime() + ", zip=" + zip + "]";
	}

}

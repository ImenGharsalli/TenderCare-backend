package com.tendercare.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/****
 * 
 * @author Imen Gharsalli
 *
 */
@Entity
public class User {
	@Id
	@GeneratedValue
	public Long id;
	public String firstname;
	public String lastname;
	public String email;
	public String location;
	public String password;
	public String description;
	public String imageid;
	public String accountname;
	@OneToOne(mappedBy = "userr")
	public Job job;
	@OneToMany(mappedBy = "userr")
	public Set<Careservice> careservice = new HashSet<>();;

	public User() {
		super();
	}

	public User(String firstName, String lastName, String email, String location, String password, String description,
			Job job) {
		super();
		this.firstname = firstName;
		this.lastname = lastName;
		this.email = email;
		this.location = location;
		this.password = password;
		this.description = description;
		this.accountname = firstName + lastName;
		this.imageid = accountname;
		this.job = job;
	}

	public String getImageId() {
		return imageid;
	}

	public void setImageId(String imageId) {
		this.imageid = imageId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstname;
	}

	public void setFirstName(String firstName) {
		this.firstname = firstName;
	}

	public String getLastName() {
		return lastname;
	}

	public void setLastName(String lastName) {
		this.lastname = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAccountName() {
		return accountname;
	}

	public void setAccountName(String accountName) {
		this.accountname = accountName;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job jobId) {
		this.job = jobId;
	}

	public Set<Careservice> getCareService() {
		return careservice;
	}

	public void setCareService(Set<Careservice> careServiceId) {
		this.careservice = careServiceId;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstname + ", lastName=" + lastname + ", email=" + email
				+ ", location=" + location + ", password=" + password + ", description=" + description + ", imageId="
				+ imageid + ", accountName=" + accountname + "]";
	}

}

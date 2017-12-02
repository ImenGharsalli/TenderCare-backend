package com.tendercare.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/***
 * 
 * @author Imen Gharsalli
 *
 */
@Entity
public class Careservice {
	@Id
	@GeneratedValue
	private Long id;
	public String jobfucntion;
	public String overview;
	public String experiencelevel;
	public String location;
	public float availability;
	public float minprice;
	public float maxprice;
	@ManyToOne
	public User userr;

	public Careservice() {
		super();
	}

	public Careservice(String jobFucntion, String overview, String experienceLevel, String location, float availability,
			float minPrice, float maxPrice, User userr) {
		super();
		this.jobfucntion = jobFucntion;
		this.overview = overview;
		this.experiencelevel = experienceLevel;
		this.location = location;
		this.availability = availability;
		this.minprice = minPrice;
		this.maxprice = maxPrice;
		this.userr = userr;
	}

	public User getUser() {
		return userr;
	}

	public void setUser(User userr) {
		this.userr = userr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobFucntion() {
		return jobfucntion;
	}

	public void setJobFucntion(String jobFucntion) {
		this.jobfucntion = jobFucntion;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getExperienceLevel() {
		return experiencelevel;
	}

	public void setExperienceLevel(String experienceLevel) {
		this.experiencelevel = experienceLevel;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getAvailability() {
		return availability;
	}

	public void setAvailability(float availability) {
		this.availability = availability;
	}

	public float getMinprice() {
		return minprice;
	}

	public void setMinprice(float minPrice) {
		this.minprice = minPrice;
	}

	public float getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(float maxPrice) {
		this.maxprice = maxPrice;
	}

}

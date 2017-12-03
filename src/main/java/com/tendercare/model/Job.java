package com.tendercare.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.tendercare.business.DateUtility;

/***
 * 
 * @author Imen Gharsalli
 *
 */
@Entity
public class Job {
	@Id
	@GeneratedValue
	private Long id;
	public String jobfunction;
	public String description;
	public String location;
	public String postdate;
	public String expirationdate;
	public float estimatedbudget;
	public String imageid;
	@OneToOne
	public User userr;

	public Job() {
		this.postdate = DateUtility.dateTimeNow();
	}

	public Job(String jobFunction, String description, String location, String postDate, String expirationDate,
			float estimatedBudget, User userr, String imageId) {
		this.jobfunction = jobFunction;
		this.description = description;
		this.location = location;
		this.postdate = DateUtility.dateTimeNow();
		this.expirationdate = expirationDate;
		this.estimatedbudget = estimatedBudget;
		this.userr = userr;
		this.imageid = imageId;
	}

	public String getImageId() {
		return imageid;
	}

	public void setImageId(String imageId) {
		this.imageid = imageId;
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

	public String getJobFunction() {
		return jobfunction;
	}

	public void setJobFunction(String jobFunction) {
		this.jobfunction = jobFunction;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getEstimatedBudget() {
		return estimatedbudget;
	}

	public void setEstimatedBudget(float estimatedBudget) {
		this.estimatedbudget = estimatedBudget;
	}

	public String getPostDate() {
		return postdate;
	}

	public void setPostDate(String postDate) {
		this.postdate = postDate;
	}

	public String getExpirationDate() {
		return expirationdate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationdate = expirationDate;
	}

	@Override
	public String toString() {
		return "Job [id=" + id + ", jobFunction=" + jobfunction + ", description=" + description + ", location="
				+ location + ", postDate=" + postdate + ", expirationDate=" + expirationdate + ", estimatedBudget="
				+ estimatedbudget + ", userr=" + userr + ", imageId=" + imageid + "]";
	}

}

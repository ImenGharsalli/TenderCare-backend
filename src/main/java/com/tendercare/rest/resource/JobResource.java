package com.tendercare.rest.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.tendercare.model.Job;
import com.tendercare.rest.JobRestController;

/***
 * 
 * @author Imen Gharsalli
 *
 */
public class JobResource extends ResourceSupport {

	private final Job job;

	/****
	 * 
	 * @param job
	 */
	public JobResource(Job job) {
		this.job = job;
		this.add(linkTo(JobRestController.class).withRel("Jobs"));
		this.add(linkTo(methodOn(JobRestController.class).readJob(job.getId())).withSelfRel());
	}

	/***
	 * 
	 * @return
	 */
	public Job getJob() {
		return job;
	}
}

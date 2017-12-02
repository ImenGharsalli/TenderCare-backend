package com.tendercare.rest;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tendercare.model.Job;
import com.tendercare.rest.resource.JobResource;
import com.tendercare.service.JobService;

/**
 * 
 * @author Imen Gharsalli
 *
 */
@RestController
@RequestMapping("/jobs")
public class JobRestController {

	private JobService jobService;

	/**
	 * 
	 * @param jobService
	 */
	JobRestController(JobService jobService) {
		this.jobService = jobService;
	}

	/**
	 * 
	 * @param job
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> createJob(@RequestBody Job job) {
		Job outputJob = jobService.createJob(job);
		return new ResponseEntity<JobResource>(new JobResource(outputJob), (HttpStatus.CREATED));

	}

	/**
	 * 
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@GetMapping(produces = "application/json")
	public ResponseEntity<?> readJobs() {
		Iterable<JobResource> jobResourceList = StreamSupport.stream(jobService.readJobs().spliterator(), false)
				.map(JobResource::new).collect(Collectors.toList());
		return new ResponseEntity<>(new Resources<>(jobResourceList), (HttpStatus.OK));
	}

	/**
	 * 
	 * @param jobId
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/{jobId}")
	public ResponseEntity<?> readJob(@PathVariable Long jobId) {
		Job job = jobService.readJob(jobId);
		return new ResponseEntity<JobResource>(new JobResource(job), (HttpStatus.OK));
	}

	/**
	 * 
	 * @param jobId
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@DeleteMapping(value = "/{jobId}")
	public ResponseEntity<?> deleteJob(@PathVariable Long jobId) {
		jobService.deleteJob(jobId);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	/**
	 * 
	 * @param job
	 * @param jobId
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@PutMapping(value = "/{jobId}")
	public ResponseEntity<?> UpdateJob(@PathVariable Job job, @PathVariable Long jobId) {
		jobService.updateJob(job, jobId);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}

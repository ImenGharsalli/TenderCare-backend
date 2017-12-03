package com.tendercare.rest;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The RestController that handles Job related HTTP Requests, including GET,
 * POST, PUT and DELETE
 * 
 * @author Imen Gharsalli
 *
 */
@RestController
@RequestMapping("/api/jobs")
@Api("/api/jobs")
public class JobRestController {

	private final Logger LOG = LoggerFactory.getLogger(JobRestController.class);
	private JobService jobService;

	/**
	 * Constructs the JobRestController by initializing the JobService that will
	 * be used by each Rest Service in it.
	 * 
	 * @param jobService
	 */
	JobRestController(JobService jobService) {
		this.jobService = jobService;
	}

	/**
	 * 
	 * @param job
	 *            The job to save
	 * @return a ResponseEntity which includes a Resource of the saved Job as
	 *         well as the corresponding HttpStatus
	 */
	@PostMapping
	@ApiOperation(value = "Create a new job", notes = "Saving a new job", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = 201, message = "Created", response = ResponseEntity.class),
			@ApiResponse(code = 400, message = "Bad request", response = ResponseEntity.class) })
	public ResponseEntity<?> createJob(@RequestBody Job job) {
		LOG.debug("REST request to create a new job");
		Job outputJob = jobService.createJob(job);
		return new ResponseEntity<JobResource>(new JobResource(outputJob), (HttpStatus.CREATED));

	}

	/**
	 * Returns the list of Jobs that has been saved
	 * 
	 * @return all saved instances of Job
	 */
	@CrossOrigin(origins = "*")
	@GetMapping(produces = "application/json")
	@ApiOperation(value = "Find all Jobs", notes = "Returning the list of all Jobs", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class) })
	public ResponseEntity<?> readJobs() {
		LOG.debug("REST request to list all jobs");
		Iterable<JobResource> jobResourceList = StreamSupport.stream(jobService.readJobs().spliterator(), false)
				.map(JobResource::new).collect(Collectors.toList());
		return new ResponseEntity<>(new Resources<>(jobResourceList), (HttpStatus.OK));
	}

	/**
	 * Reads the Job corresponding to the jobId given as parameter
	 * 
	 * @param jobId
	 * @return the ResponseEntity that includes the returned Job as well as the
	 *         HttpStatus
	 */
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/{jobId}")
	@ApiOperation(value = "Find one Job", notes = "Returning the Job corresponding to the given id", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "NotFound") })
	public ResponseEntity<?> readJob(@PathVariable Long jobId) {
		LOG.debug("REST request to read job with id {}" + jobId);
		Job job = jobService.readJob(jobId);
		return new ResponseEntity<JobResource>(new JobResource(job), (HttpStatus.OK));
	}

	/**
	 * Given a jobId, deletes the corresponding job
	 * 
	 * @param jobId
	 *            the id of the job to be deleted
	 * @return responseEntity including HTTPStatus
	 */
	@CrossOrigin(origins = "*")
	@DeleteMapping(value = "/{jobId}")
	@ApiOperation(value = "Delete a Job", notes = "Deleting an existing Job")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "NotFound") })
	public ResponseEntity<?> deleteJob(@PathVariable Long jobId) {
		LOG.debug("REST request to delete job with id {}" + jobId);
		jobService.deleteJob(jobId);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	/**
	 * Given a jobId, updates the corresponding job
	 * 
	 * @param job
	 *            the new job to use for the update
	 * @param jobId
	 *            the id of the Job to be updated
	 * @return the ResponseEntity including the HttpStatus
	 */
	@CrossOrigin(origins = "*")
	@PutMapping(value = "/{jobId}")
	@ApiOperation(value = "Update a Job", notes = "Updating an existing Job")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "NotFound") })
	public ResponseEntity<?> UpdateJob(@PathVariable Long jobId, @RequestBody Job job) {
		LOG.debug("REST request to update job with id {}" + jobId);
		jobService.updateJob(job, jobId);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}

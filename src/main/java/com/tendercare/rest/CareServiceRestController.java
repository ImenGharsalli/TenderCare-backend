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

import com.tendercare.model.Careservice;
import com.tendercare.rest.resource.CareServiceResource;
import com.tendercare.service.CareServiceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The RestController that handles CareService related HTTP Requests, including
 * GET, POST, PUT and DELETE
 * 
 * @author Imen Gharsalli
 *
 */
@RestController
@RequestMapping("/api/careservices")
@Api("/api/careservices")
public class CareServiceRestController {

	private final Logger LOG = LoggerFactory.getLogger(CareServiceRestController.class);
	private CareServiceService careServiceService;

	/**
	 * Constructs the CareServiceRestController by initializing the
	 * CareServiceService that will be used by each Rest Service in it.
	 * 
	 * @param careServiceService
	 */
	CareServiceRestController(CareServiceService careServiceService) {
		this.careServiceService = careServiceService;
	}

	/**
	 * 
	 * @param careService
	 *            The care service to save
	 * @return a ResponseEntity which includes a Resource of the saved
	 *         CareService as well as the corresponding HttpStatus
	 */
	@PostMapping
	@ApiOperation(value = "Create a new careService", notes = "Saving a new careService", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = 201, message = "Created", response = ResponseEntity.class),
			@ApiResponse(code = 400, message = "Bad request", response = ResponseEntity.class) })
	public ResponseEntity<?> createCareService(@RequestBody Careservice careService) {
		LOG.debug("REST request to create a new careService");
		Careservice outputCareService = careServiceService.createCareService(careService);
		return new ResponseEntity<CareServiceResource>(new CareServiceResource(outputCareService),
				(HttpStatus.CREATED));
	}

	/**
	 * Returns the list of CareServices that has been saved
	 * 
	 * @return all saved instances of CareService
	 */
	@CrossOrigin(origins = "*")
	@GetMapping(produces = "application/json")
	@ApiOperation(value = "Find all careServices", notes = "Returning the list of all careServices", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class) })
	public ResponseEntity<?> readCareServices() {
		LOG.debug("REST request to list all careServices");
		Iterable<CareServiceResource> careServiceResourceList = StreamSupport
				.stream(careServiceService.readCareServices().spliterator(), false).map(CareServiceResource::new)
				.collect(Collectors.toList());
		return new ResponseEntity<>(new Resources<>(careServiceResourceList), (HttpStatus.OK));
	}

	/**
	 * Reads the CareService corresponding to the careServiceId given as
	 * parameter
	 * 
	 * @param careServiceId
	 * @return the ResponseEntity that includes the returned CareService as well
	 *         as the HttpStatus
	 */
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/{careServiceId}")
	@ApiOperation(value = "Find one careService", notes = "Returning the careService corresponding to the given id", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "NotFound") })
	public ResponseEntity<?> readCareService(@PathVariable Long careServiceId) {
		LOG.debug("REST request to read careService with id {}" + careServiceId);
		Careservice careService = careServiceService.readCareService(careServiceId);
		return new ResponseEntity<CareServiceResource>(new CareServiceResource(careService), (HttpStatus.OK));

	}

	/**
	 * Given a careServiceId, deletes the corresponding careService
	 * 
	 * @param careServiceId
	 *            the id of the careService to be deleted
	 * @return responseEntity including HTTPStatus
	 */
	@CrossOrigin(origins = "*")
	@DeleteMapping(value = "/{careServiceId}")
	@ApiOperation(value = "Delete a careService", notes = "Deleting an existing careService")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "NotFound") })
	public ResponseEntity<?> deleteCareService(@PathVariable Long careServiceId) {
		LOG.debug("REST request to delete careService with id {}" + careServiceId);
		careServiceService.deleteCareService(careServiceId);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	/**
	 * Given a careServiceId, updates the corresponding care service
	 * 
	 * @param careService
	 *            the new careService to use for the update
	 * @param careServiceId
	 *            the id of the CareService to be updated
	 * @return the ResponseEntity including the HttpStatus
	 */
	@CrossOrigin(origins = "*")
	@PutMapping(value = "/{careServiceId}")
	@ApiOperation(value = "Update a careService", notes = "Updating an existing careService")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "NotFound") })
	public ResponseEntity<?> UpdateCareService(@PathVariable Long careServiceId, @RequestBody Careservice careService) {
		LOG.debug("REST request to update careService with id {}" + careServiceId);
		careServiceService.updateCareService(careService, careServiceId);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}

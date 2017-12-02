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

import com.tendercare.model.Careservice;
import com.tendercare.rest.resource.CareServiceResource;
import com.tendercare.service.CareServiceService;

/**
 * The RestController that handles CareService related HTTP Requests, including
 * GET, POST, PUT and DELETE
 * 
 * @author Imen Gharsalli
 *
 */
@RestController
@RequestMapping("/careservices")
public class CareServiceRestController {
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
	public ResponseEntity<?> createCareService(@RequestBody Careservice careService) {
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
	public ResponseEntity<?> readCareServices() {
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
	public ResponseEntity<?> readCareService(@PathVariable Long careServiceId) {
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
	public ResponseEntity<?> deleteCareService(@PathVariable Long careServiceId) {
		careServiceService.deleteCareService(careServiceId);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	/**
	 * Given a careServiceId, updates the corresponding care service by
	 * relPacing it with the given CareService instance
	 * 
	 * @param careService
	 *            the new careService to use for the update
	 * @param careServiceId
	 *            the id of the CareService to be updated
	 * @return the ResponseEntity including the HttpStatus
	 */
	@CrossOrigin(origins = "*")
	@PutMapping(value = "/{careServiceId}")
	public ResponseEntity<?> UpdateCareService(@PathVariable Careservice careService,
			@PathVariable Long careServiceId) {
		careServiceService.updateCareService(careService, careServiceId);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}

package com.tendercare.rest.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.tendercare.model.Careservice;
import com.tendercare.rest.CareServiceRestController;

/***
 * 
 * @author Imen Gharsalli
 *
 */
public class CareServiceResource extends ResourceSupport {

	private final Careservice careService;

	/***
	 * 
	 * @param careService
	 */
	public CareServiceResource(Careservice careService) {
		this.careService = careService;
		this.add(linkTo(CareServiceRestController.class).withRel("CareServices"));
		this.add(linkTo(methodOn(CareServiceRestController.class).readCareService(careService.getId())).withSelfRel());
	}

	/***
	 * 
	 * @return
	 */
	public Careservice getCareService() {
		return careService;
	}
}

package com.tendercare.service;

import org.springframework.stereotype.Controller;

import com.tendercare.dao.CareServiceRepository;
import com.tendercare.model.Careservice;

/**
 * 
 * @author Imen Gharsalli
 *
 */
@Controller
public class CareServiceService {

	CareServiceRepository careServiceRepository;

	/**
	 * 
	 * @param careServiceRepository
	 */
	public CareServiceService(CareServiceRepository careServiceRepository) {
		this.careServiceRepository = careServiceRepository;
	}

	/**
	 * 
	 * @return
	 */
	public Iterable<Careservice> readCareServices() {
		return careServiceRepository.findAll();
	}

	/**
	 * 
	 * @param careService
	 * @return
	 */
	public Careservice createCareService(Careservice careService) {
		return careServiceRepository.save(careService);
	}

	/**
	 * 
	 * @param careServiceId
	 * @return
	 */
	public Careservice readCareService(Long careServiceId) {
		return careServiceRepository.findOne(careServiceId);
	}

	/**
	 * 
	 * @param careServiceId
	 */
	public void deleteCareService(Long careServiceId) {
		careServiceRepository.delete(careServiceId);
	}

	/**
	 * 
	 * @param careService
	 * @param careServiceId
	 */
	public void updateCareService(Careservice careService, long careServiceId) {

	}
}

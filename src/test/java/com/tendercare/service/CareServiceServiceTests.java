package com.tendercare.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tendercare.TenderCareApplication;
import com.tendercare.dao.CareServiceRepository;
import com.tendercare.model.Careservice;

/**
 * 
 * @author Imen Gharsalli
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TenderCareApplication.class)
@WebAppConfiguration
public class CareServiceServiceTests {
	private List<Careservice> careServiceList = new ArrayList<>();

	@Autowired
	private CareServiceService careServiceService;
	@Autowired
	private CareServiceRepository careServiceRepository;

	/**
	 * 
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {
		this.careServiceRepository.deleteAllInBatch();
		this.careServiceList.add(careServiceRepository
				.save(new Careservice("Child care", "Out-of-home care", "3 years", "Berlin", 6, 12, 15, null)));
	}

	/**
	 * 
	 */
	@Test
	public void readCareServices() {
		Iterable<Careservice> actual = careServiceService.readCareServices();
		careServiceList.forEach(actualCareService -> {
			Careservice expectedCareService = StreamSupport.stream(actual.spliterator(), false)
					.filter(careService -> careService.getJobFucntion().equals(actualCareService.getJobFucntion()))
					.findAny().orElseThrow(() -> new AssertionError(
							"CareService with the function : " + actualCareService.getJobFucntion() + " not found!"));
			Assert.assertEquals(actualCareService.getOverview(), expectedCareService.getOverview());
			Assert.assertTrue(actualCareService.getAvailability() == expectedCareService.getAvailability());
			Assert.assertEquals(actualCareService.getLocation(), expectedCareService.getLocation());
			Assert.assertEquals(actualCareService.getExperienceLevel(), expectedCareService.getExperienceLevel());
			Assert.assertTrue(actualCareService.getMaxprice() == expectedCareService.getMaxprice());
			Assert.assertTrue(actualCareService.getMinprice() == expectedCareService.getMinprice());

		});
	}
}

package com.tendercare.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.tendercare.TenderCareApplication;
import com.tendercare.dao.CareServiceRepository;
import com.tendercare.model.Careservice;

/**
 * Tests the different REST services that this API exposes in order to handle
 * CareService objects, which are GET, POST, PUT, DELETE
 * 
 * @author Imen Gharsalli
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TenderCareApplication.class)
@WebAppConfiguration
public class CareServiceRestControllerTests {
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;
	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	private List<Careservice> careServiceList = new ArrayList<>();
	@Autowired
	private CareServiceRepository careServiceRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(httpMessageConverter -> httpMessageConverter instanceof MappingJackson2HttpMessageConverter)
				.findAny().orElse(null);

		Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	/**
	 * Performs the necessary setup for CareServiceRestController test methods
	 * to run
	 * 
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		this.careServiceRepository.deleteAllInBatch();
		this.careServiceList.add(careServiceRepository
				.save(new Careservice("Child care", "Out-of-home care", "3 years", "Berlin", 6, 12, 15, null)));
	}

	/**
	 * Tests the readCareService() service by making sure that gibven a
	 * careServiceId, the API returns the corresponding object and the
	 * HTTPStatus is 200.
	 * 
	 * @throws Exception
	 */
	@Test
	public void readCareService() throws Exception {
		mockMvc.perform(get("/api/careservices/" + this.careServiceList.get(0).getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.careService.id", is(this.careServiceList.get(0).getId().intValue())))
				.andExpect(jsonPath("$.careService.jobfucntion", is("Child care")))
				.andExpect(jsonPath("$.careService.overview", is("Out-of-home care")))
				.andExpect(jsonPath("$.careService.experiencelevel", is("3 years")))
				.andExpect(jsonPath("$.careService.location", is("Berlin")))
				.andExpect(jsonPath("$.careService.availability", is(6.0)))
				.andExpect(jsonPath("$.careService.minprice", is(12.0)))
				.andExpect(jsonPath("$.careService.maxprice", is(15.0)));
	}

	/**
	 * Tests the readCareServices() service by making sure that the list of the
	 * saved careServices is returned, once the service is called and the status
	 * is 200.
	 * 
	 * @throws Exception
	 */
	@Test
	public void readCareServices() throws Exception {
		mockMvc.perform(get("/api/careservices/")).andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.careServiceResourceList", hasSize(1)))
				.andExpect(jsonPath("$._embedded.careServiceResourceList[0].careService.id",
						is(this.careServiceList.get(0).getId().intValue())))
				.andExpect(jsonPath("$._embedded.careServiceResourceList[0].careService.jobfucntion", is("Child care")))
				.andExpect(
						jsonPath("$._embedded.careServiceResourceList[0].careService.overview", is("Out-of-home care")))
				.andExpect(
						jsonPath("$._embedded.careServiceResourceList[0].careService.experiencelevel", is("3 years")))
				.andExpect(jsonPath("$._embedded.careServiceResourceList[0].careService.location", is("Berlin")))
				.andExpect(jsonPath("$._embedded.careServiceResourceList[0].careService.availability", is(6.0)))
				.andExpect(jsonPath("$._embedded.careServiceResourceList[0].careService.minprice", is(12.0)))
				.andExpect(jsonPath("$._embedded.careServiceResourceList[0].careService.maxprice", is(15.0)));
	}

	/**
	 * Tests the createCareService() service by making sure that given a valid
	 * CareService object the returned HTTPStatus is 201.
	 * 
	 * @throws Exception
	 */
	@Test
	public void createCareService() throws Exception {
		String careServiceJson = json(new Careservice("Pet care", "Grooming", "1 year", "Berlin", 12, 14, 18, null));
		this.mockMvc.perform(post("/api/careservices").contentType(contentType).content(careServiceJson))
				.andExpect(status().isCreated());
	}

	/**
	 * Tests the deleteCareService() service by making sure that given a valid
	 * careServiceId the returned HTTPStatus is200.
	 * 
	 * @throws Exception
	 */
	@Test
	public void deleteCareService() throws Exception {
		this.mockMvc.perform(delete("/api/careservices/" + this.careServiceList.get(0).getId()))
				.andExpect(status().isOk());
	}

	/**
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	protected String json(Object object) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(object, contentType, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}

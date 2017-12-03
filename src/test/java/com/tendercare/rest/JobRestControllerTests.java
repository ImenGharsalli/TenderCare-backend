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
import com.tendercare.dao.JobRepository;
import com.tendercare.model.Job;

/**
 * Tests the different REST services that this API exposes in order to handle
 * Job objects, which are GET, POST, PUT, DELETE
 * 
 * @author Imen Gharsalli
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TenderCareApplication.class)
@WebAppConfiguration
public class JobRestControllerTests {
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;
	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	private List<Job> jobList = new ArrayList<>();
	@Autowired
	private JobRepository jobRepository;

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
	 * Performs the necessary setup for JobRestController test methods to run
	 * 
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		this.jobRepository.deleteAllInBatch();
		this.jobList.add(jobRepository
				.save(new Job("Pet care", "Dog walking", "Berlin", "2017-11-11", "2017-12-12", 10, null, "1")));
	}

	/**
	 * Tests the readJob() service by making sure that gibven a jobId, the API
	 * returns the corresponding object and the HTTPStatus is 200.
	 * 
	 * @throws Exception
	 */
	@Test
	public void readJob() throws Exception {
		mockMvc.perform(get("/api/jobs/" + this.jobList.get(0).getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.job.id", is(this.jobList.get(0).getId().intValue())))
				.andExpect(jsonPath("$.job.jobfunction", is("Pet care")))
				.andExpect(jsonPath("$.job.description", is("Dog walking")))
				.andExpect(jsonPath("$.job.location", is("Berlin")))
				.andExpect(jsonPath("$.job.expirationdate", is("2017-12-12")))
				.andExpect(jsonPath("$.job.estimatedbudget", is(10.0))).andExpect(jsonPath("$.job.imageid", is("1")));
	}

	/**
	 * Tests the readJobs() service by making sure that the list of the saved
	 * jobs is returned, once the service is called and the status is 200.
	 * 
	 * @throws Exception
	 */
	@Test
	public void readJobs() throws Exception {
		mockMvc.perform(get("/api/jobs/")).andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.jobResourceList", hasSize(1)))
				.andExpect(
						jsonPath("$._embedded.jobResourceList[0].job.id", is(this.jobList.get(0).getId().intValue())))
				.andExpect(jsonPath("$._embedded.jobResourceList[0].job.jobfunction", is("Pet care")))
				.andExpect(jsonPath("$._embedded.jobResourceList[0].job.description", is("Dog walking")))
				.andExpect(jsonPath("$._embedded.jobResourceList[0].job.location", is("Berlin")))
				.andExpect(jsonPath("$._embedded.jobResourceList[0].job.expirationdate", is("2017-12-12")))
				.andExpect(jsonPath("$._embedded.jobResourceList[0].job.estimatedbudget", is(10.0)))
				.andExpect(jsonPath("$._embedded.jobResourceList[0].job.imageid", is("1")));
	}

	/**
	 * Tests the createJob() service by making sure that given a valid Job
	 * object the returned HTTPStatus is 201.
	 * 
	 * @throws Exception
	 */
	@Test
	public void createJob() throws Exception {
		String jobJson = json(
				new Job("Adult & senior care", "Personal care", "Berlin", "2017-10-10", "2017-11-11", 17, null, "2"));
		this.mockMvc.perform(post("/api/jobs").contentType(contentType).content(jobJson))
				.andExpect(status().isCreated());
	}

	/**
	 * Tests the deleteJob() service by making sure that given a valid jobId the
	 * returned HTTPStatus is200.
	 * 
	 * @throws Exception
	 */
	@Test
	public void deleteJob() throws Exception {
		this.mockMvc.perform(delete("/api/jobs/" + this.jobList.get(0).getId())).andExpect(status().isOk());
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

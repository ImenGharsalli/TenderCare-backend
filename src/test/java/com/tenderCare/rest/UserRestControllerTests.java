package com.tenderCare.rest;

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
import com.tendercare.dao.UserRepository;
import com.tendercare.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TenderCareApplication.class)
@WebAppConfiguration
public class UserRestControllerTests {
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;
	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	private List<User> userList = new ArrayList<>();
	@Autowired
	private UserRepository userRepository;

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
	 * Performs the necessary setup for UserRestController test methods to run
	 * 
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		this.userRepository.deleteAllInBatch();
		this.userList.add(userRepository
				.save(new User("Meredith", "Grey", "mgrey@gmail.com", "Berlin", "123", "Description", null)));
	}

	/**
	 * Tests the readUser() service by making sure that gibven a userId, the API
	 * returns the corresponding object and the HTTPStatus is 200.
	 * 
	 * @throws Exception
	 */
	@Test
	public void readUser() throws Exception {
		mockMvc.perform(get("/api/users/" + this.userList.get(0).getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.user.id", is(this.userList.get(0).getId().intValue())))
				.andExpect(jsonPath("$.user.firstName", is("Meredith")))
				.andExpect(jsonPath("$.user.lastName", is("Grey")))
				.andExpect(jsonPath("$.user.email", is("mgrey@gmail.com")))
				.andExpect(jsonPath("$.user.location", is("Berlin"))).andExpect(jsonPath("$.user.password", is("123")))
				.andExpect(jsonPath("$.user.description", is("Description")));
	}

	/**
	 * Tests the readUsers() service by making sure that the list of the saved
	 * users is returned, once the service is called and the status is 200.
	 * 
	 * @throws Exception
	 */
	@Test
	public void readUsers() throws Exception {
		mockMvc.perform(get("/api/users/")).andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.userResourceList[0].user.id",
						is(this.userList.get(0).getId().intValue())))
				.andExpect(jsonPath("$._embedded.userResourceList[0].user.firstName", is("Meredith")))
				.andExpect(jsonPath("$._embedded.userResourceList[0].user.lastName", is("Grey")))
				.andExpect(jsonPath("$._embedded.userResourceList[0].user.email", is("mgrey@gmail.com")))
				.andExpect(jsonPath("$._embedded.userResourceList[0].user.location", is("Berlin")))
				.andExpect(jsonPath("$._embedded.userResourceList[0].user.password", is("123")))
				.andExpect(jsonPath("$._embedded.userResourceList[0].user.description", is("Description")));
	}

	/**
	 * Tests the createUser() service by making sure that given a valid User
	 * object the returned HTTPStatus is 201.
	 * 
	 * @throws Exception
	 */
	@Test
	public void createUser() throws Exception {
		String userJson = json(new User("Cristina", "Yang", "cyang@gmail.com", "Berlin", "123", "Description", null));
		this.mockMvc.perform(post("/api/users").contentType(contentType).content(userJson))
				.andExpect(status().isCreated());
	}

	/**
	 * Tests the deleteUser() service by making sure that given a valid userId
	 * the returned HTTPStatus is200.
	 * 
	 * @throws Exception
	 */
	@Test
	public void deleteUser() throws Exception {
		this.mockMvc.perform(delete("/api/users/" + this.userList.get(0).getId())).andExpect(status().isOk());
	}

	@SuppressWarnings("unchecked")
	protected String json(Object object) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(object, contentType, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}

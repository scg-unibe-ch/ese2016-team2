package ch.unibe.ese.team1.test.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"file:src/main/webapp/WEB-INF/config/springMVC.xml",
		"file:src/main/webapp/WEB-INF/config/springData.xml",
		"file:src/main/webapp/WEB-INF/config/springSecurity.xml" })
@WebAppConfiguration
public class EnquiryControllerTest {

	@Autowired
	WebApplicationContext wac;
	@Autowired
	MockHttpServletRequest request;
	
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void getEnquiry() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(get("/profile/enquiries").principal(principal))
					.andExpect(status().isOk())
					.andExpect(view().name("enquiries"))
					.andExpect(model().attributeExists("enquiries"));
	}
	
	@Test
	public void testSendEnquiryForVisis() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(get("/profile/enquiries/sendEnquiryForVisit")
						.param("id", "1")
						.principal(principal))
					.andExpect(status().isOk());
	}
	
	@Test
	public void testEnquiry() throws Exception {
		this.mockMvc.perform(get("/profile/enquiries/acceptEnquiry").param("id", "4"))
					.andExpect(status().isOk());
		this.mockMvc.perform(get("/profile/enquiries/declineEnquiry").param("id", "4"))
					.andExpect(status().isOk());
		this.mockMvc.perform(get("/profile/enquiries/reopenEnquiry").param("id", "4"))
					.andExpect(status().isOk());
	}
	
	@Test
	public void testRating() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(get("/profile/rateUser")
						.param("rate", "2")
						.param("stars", "4")
						.principal(principal))
					.andExpect(status().isOk());
		
		MvcResult result = this.mockMvc.perform(get("/profile/ratingFor")
				.param("user", "2")
				.principal(principal))
			.andExpect(status().isOk()).andReturn();
		
		String rating = result.getResponse().getContentAsString();
		assertEquals("4", rating);
	}
	
}

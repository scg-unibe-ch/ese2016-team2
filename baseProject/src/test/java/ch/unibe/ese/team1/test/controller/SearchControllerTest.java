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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ch.unibe.ese.team1.controller.service.AuctionService;

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
public class SearchControllerTest {

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
	public void getSearchAdPage() throws Exception {
		this.mockMvc.perform(get("/searchAd"))
			 		.andExpect(status().isOk())
			 		.andExpect(view().name("searchAd"));
	}
	
	@Test
	public void postInvalidSearchForm() throws Exception {
		this.mockMvc.perform(post("/results")
						.param("prize", "0"))
 					.andExpect(status().isOk())
 					.andExpect(view().name("searchAd"));
	}
	
	@Test
	public void postValidSearchForm() throws Exception {
		this.mockMvc.perform(post("/results")
						.param("prize", "4000")
						.param("radius", "100")
						.param("city", "3000 - Bern")
						.param("neither", "false")
						.param("buyableNotFilled", "false")
						.param("studio", "true")
						.param("room", "true"))
 					.andExpect(status().isOk())
 					.andExpect(view().name("results"))
 					.andExpect(model().attributeExists("results", "premiumResults"));
	}
	
}

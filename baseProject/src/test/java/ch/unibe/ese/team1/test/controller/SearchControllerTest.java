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
						.param("city", "")
						.param("results", "Results"))
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
						.param("room", "true")
						.param("results", "Results")
						.param("ad", "true")
						.param("auction", "true"))
 					.andExpect(status().isOk())
 					.andExpect(view().name("results"))
 					.andExpect(model().attributeExists("results", "premiumResults"));
		
		this.mockMvc.perform(post("/results")
						.param("prize", "4000")
						.param("radius", "100")
						.param("city", "3000 - Bern")
						.param("neither", "false")
						.param("buyableNotFilled", "false")
						.param("studio", "true")
						.param("room", "true")
						.param("results", "Results")
						.param("ad", "true")
						.param("auction", "true")
						.param("buyable", "true"))
					.andExpect(status().isOk())
					.andExpect(view().name("results"))
					.andExpect(model().attributeExists("results", "premiumResults"));
		
		this.mockMvc.perform(post("/results")
						.param("prize", "4000")
						.param("radius", "100")
						.param("city", "3000 - Bern")
						.param("neither", "false")
						.param("buyableNotFilled", "false")
						.param("studio", "true")
						.param("room", "true")
						.param("results", "Results")
						.param("ad", "true")
						.param("auction", "false"))
					.andExpect(status().isOk())
					.andExpect(view().name("results"))
					.andExpect(model().attributeExists("results", "premiumResults"));
		
		this.mockMvc.perform(post("/results")
						.param("prize", "4000")
						.param("radius", "100")
						.param("city", "3000 - Bern")
						.param("neither", "false")
						.param("buyableNotFilled", "false")
						.param("studio", "true")
						.param("room", "true")
						.param("results", "Results")
						.param("ad", "false")
						.param("auction", "true")
						.param("buyable", "true"))
					.andExpect(status().isOk())
					.andExpect(view().name("results"))
					.andExpect(model().attributeExists("results", "premiumResults"));
	}
	
	@Test
	public void testCreateAlert() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(post("/results")
				.param("prize", "4000")
				.param("radius", "100")
				.param("city", "3000 - Bern")
				.param("neither", "false")
				.param("buyableNotFilled", "false")
				.param("studio", "true")
				.param("room", "true")
				.param("house", "true")
				.param("createAlert", "CreateAlert")
				.principal(principal))
			.andExpect(status().is(302));
		
		this.mockMvc.perform(post("/results")
				.param("prize", "4000")
				.param("radius", "100")
				.param("city", "3000 - Bern")
				.param("neither", "false")
				.param("buyableNotFilled", "false")
				.param("studio", "false")
				.param("room", "true")
				.param("house", "true")
				.param("createAlert", "CreateAlert")
				.principal(principal))
			.andExpect(status().is(302));
		
		this.mockMvc.perform(post("/results")
				.param("prize", "4000")
				.param("radius", "100")
				.param("city", "3000 - Bern")
				.param("neither", "false")
				.param("buyableNotFilled", "false")
				.param("studio", "true")
				.param("room", "false")
				.param("house", "true")
				.param("createAlert", "CreateAlert")
				.principal(principal))
			.andExpect(status().is(302));
		
		this.mockMvc.perform(post("/results")
				.param("prize", "4000")
				.param("radius", "100")
				.param("city", "3000 - Bern")
				.param("neither", "false")
				.param("buyableNotFilled", "false")
				.param("studio", "true")
				.param("room", "true")
				.param("house", "false")
				.param("createAlert", "CreateAlert")
				.principal(principal))
			.andExpect(status().is(302));
		
		this.mockMvc.perform(post("/results")
				.param("prize", "4000")
				.param("radius", "100")
				.param("city", "3000 - Bern")
				.param("neither", "false")
				.param("buyableNotFilled", "false")
				.param("studio", "false")
				.param("room", "false")
				.param("house", "true")
				.param("createAlert", "CreateAlert")
				.principal(principal))
			.andExpect(status().is(302));
		
		this.mockMvc.perform(post("/results")
				.param("prize", "4000")
				.param("radius", "100")
				.param("city", "3000 - Bern")
				.param("neither", "false")
				.param("buyableNotFilled", "false")
				.param("studio", "false")
				.param("room", "true")
				.param("house", "false")
				.param("createAlert", "CreateAlert")
				.principal(principal))
			.andExpect(status().is(302));
		
		this.mockMvc.perform(post("/results")
				.param("prize", "4000")
				.param("radius", "100")
				.param("city", "3000 - Bern")
				.param("neither", "false")
				.param("buyableNotFilled", "false")
				.param("studio", "true")
				.param("room", "false")
				.param("house", "false")
				.param("createAlert", "CreateAlert")
				.principal(principal))
			.andExpect(status().is(302));
	}
	
	@Test
	public void createAlertPrincipalNull() throws Exception {
		this.mockMvc.perform(post("/results").param("createAlert", "CreateAlert"))
					.andExpect(status().isOk());
		
		this.mockMvc.perform(post("/results")
				.param("prize", "4000")
				.param("radius", "100")
				.param("city", "3000 - Bern")
				.param("neither", "false")
				.param("buyableNotFilled", "false")
				.param("studio", "true")
				.param("room", "false")
				.param("house", "false")
				.param("createAlert", "CreateAlert"))
			.andExpect(status().isOk());
	}
	
}

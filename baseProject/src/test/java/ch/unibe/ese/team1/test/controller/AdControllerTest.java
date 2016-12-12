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
public class AdControllerTest {

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
	public void getAd() throws Exception {
		this.mockMvc.perform(get("/ad")
						.param("id", "7"))
					.andExpect(status().isOk())
					.andExpect(view().name("adDescription"))
					.andExpect(model().attributeExists("shownAd", "messageForm", "loggedInUserEmail", "visits"));
	}
	
	@Test
	public void postAdInvalidMessageForm() throws Exception {
		this.mockMvc.perform(post("/ad")
						.param("id", "7")
						.param("recipient", ""))
					.andExpect(status().isOk())
					.andExpect(view().name("adDescription"));
		
	}
	
	@Test
	public void deleteAdPricipalNotCreator() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.ch");
		
		this.mockMvc.perform(get("/deleteAd")
						.param("id", "2")
						.principal(principal))
					.andExpect(status().is(302)); //means redirect
	}
	
	@Test
	public void deleteAdPrincipalIsCreator() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
	
		this.mockMvc.perform(get("/deleteAd")
				.param("id", "1")
				.principal(principal))
			.andExpect(status().is(302));
	}
	
}

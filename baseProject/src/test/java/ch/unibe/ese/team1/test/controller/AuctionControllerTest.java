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
public class AuctionControllerTest {

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
	public void getAuction() throws Exception {
		this.mockMvc.perform(get("/auction")
						.param("id", "2"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("shownAuction", "messageForm", "loggedInUserEmail", "visits"))
					.andExpect(view().name("auctionDescription"));	
	}
	
	@Test
	public void postAuction() throws Exception {
		this.mockMvc.perform(post("/auction").param("id", "2")
						.param("recipient", ""))
					.andExpect(status().isOk());
	}
	
	@Test
	public void deleteAuctionPrincipalNotCreator() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.ch");
		
		this.mockMvc.perform(get("/deleteAuction").param("id", "2")
						.principal(principal))
					.andExpect(status().is(302));
	}
	
	@Test
	public void deleteAuctionNotEnded() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(get("/deleteAuction").param("id", "2")
				.principal(principal))
			.andExpect(status().is(302));
	}

	@Test
	public void deleteAuctionPricipalIsCreator() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("ese@unibe.ch");
		
		this.mockMvc.perform(get("/deleteAuction").param("id", "1")
				.principal(principal))
			.andExpect(status().is(302));
	}
}
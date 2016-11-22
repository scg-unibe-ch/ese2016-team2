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
public class PlaceBidControllerTest {

	@Autowired
	WebApplicationContext wac;
	@Autowired
	MockHttpServletRequest request;
	
	private MockMvc mockMvc;
	
	@Autowired
	AuctionService auctionService;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void getPlaceBidForm() throws Exception {	
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(get("/auction/placeBid")
						.param("id", "1")
						.principal(principal))
					.andExpect(status().isOk())
					.andExpect(view().name("placeBid"))
					.andExpect(model().attributeExists("shownAuction", "loggedInUser"));
	}
	
	@Test
	public void postInvalidPlaceBidForm() throws Exception {	
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(post("/auction/placeBid")
						.param("id", "1")
						.param("prize", "-1")
						.principal(principal))
					.andExpect(status().isOk())
					.andExpect(forwardedUrl("/pages/auction?id=1.jsp"));
	}
	
	@Test
	public void postValidPlaceBidFormWithPrizeToLow() throws Exception {	
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(post("/auction/placeBid")
						.param("id", "1")
						.param("prize", "1")
						.principal(principal))
					.andExpect(status().is(302));
	}
	
	@Test
	public void postValidPlaceBidForm() throws Exception {	
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(post("/auction/placeBid")
						.param("id", "1")
						.param("prize", "1000000")
						.principal(principal))
					.andExpect(status().is(302));
		
		assertEquals(1000000, auctionService.getAuctionById(1).getPrize());
	}
	
}

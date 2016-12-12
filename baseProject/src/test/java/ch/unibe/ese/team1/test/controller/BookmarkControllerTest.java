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
public class BookmarkControllerTest {

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
	public void getBookmarkPage() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(get("/profile/bookmarks").principal(principal))
					.andExpect(status().isOk())
					.andExpect(view().name("bookmarks"))
					.andExpect(model().attributeExists("bookmarkedAdvertisements", "bookmarkedAuctions"));
	}
	
	@Test
	public void getBookmarkPagePrincipalNull() throws Exception {
		this.mockMvc.perform(get("/profile/bookmarks"))
					.andExpect(status().isOk())
					.andExpect(view().name("home"));
	}
	
	@Test
	public void bookmarkPrincipalNull() throws Exception {
		MvcResult adResult = this.mockMvc.perform(post("/bookmark")
											.param("id", "2")
											.param("screening", "false")
											.param("bookmarked", "false"))
										.andReturn();
		
		int numberAd = Integer.parseInt(adResult.getResponse().getContentAsString());
		
		MvcResult auctionResult = this.mockMvc.perform(post("/bookmarkAuction")
													.param("id", "2")
													.param("screening", "false")
													.param("bookmarked", "false"))
												.andReturn();
		
		int numberAuction = Integer.parseInt(auctionResult.getResponse().getContentAsString());
		
		assertEquals(0, numberAd);
		assertEquals(0, numberAuction);
	}
	
	@Test
	public void bookmarkUserDoesNotExist() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.ch");
		
		MvcResult adResult = this.mockMvc.perform(post("/bookmark")
											.param("id", "2")
											.param("screening", "false")
											.param("bookmarked", "false")
											.principal(principal))
										.andReturn();
		
		int numberAd = Integer.parseInt(adResult.getResponse().getContentAsString());
		
		MvcResult auctionResult = this.mockMvc.perform(post("/bookmarkAuction")
													.param("id", "2")
													.param("screening", "false")
													.param("bookmarked", "false")
													.principal(principal))
												.andReturn();
		
		int numberAuction = Integer.parseInt(auctionResult.getResponse().getContentAsString());
		
		assertEquals(1, numberAd);
		assertEquals(1, numberAuction);
	}
	
	@Test
	public void bookmarkScreening() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		MvcResult adResult = this.mockMvc.perform(post("/bookmark")
											.param("id", "2")
											.param("screening", "true")
											.param("bookmarked", "false")
											.principal(principal))
										.andReturn();
		
		int numberAd = Integer.parseInt(adResult.getResponse().getContentAsString());
		
		MvcResult auctionResult = this.mockMvc.perform(post("/bookmarkAuction")
													.param("id", "2")
													.param("screening", "true")
													.param("bookmarked", "false")
													.principal(principal))
												.andReturn();
		
		int numberAuction = Integer.parseInt(auctionResult.getResponse().getContentAsString());
		
		assertEquals(3, numberAd);
		assertEquals(4, numberAuction);
		
		MvcResult adResult2 = this.mockMvc.perform(post("/bookmark")
												.param("id", "3")
												.param("screening", "true")
												.param("bookmarked", "false")
												.principal(principal))
											.andReturn();

		int numberAd2 = Integer.parseInt(adResult2.getResponse().getContentAsString());
		
		assertEquals(4, numberAd2);
	}
	
	@Test
	public void bookmarkINotBookmarkedYet() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		MvcResult adResult = this.mockMvc.perform(post("/bookmark")
											.param("id", "100")
											.param("screening", "true")
											.param("bookmarked", "false")
											.principal(principal))
										.andReturn();
		
		int numberAd = Integer.parseInt(adResult.getResponse().getContentAsString());
		
		MvcResult auctionResult = this.mockMvc.perform(post("/bookmarkAuction")
													.param("id", "100")
													.param("screening", "true")
													.param("bookmarked", "false")
													.principal(principal))
												.andReturn();
		
		int numberAuction = Integer.parseInt(auctionResult.getResponse().getContentAsString());
		
		assertEquals(2, numberAd);
		assertEquals(2, numberAuction);
	}
	
	@Test
	public void bookmarkINoScreening() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(post("/bookmark")
						.param("id", "2")
						.param("screening", "false")
						.param("bookmarked", "false")
						.principal(principal))
					.andExpect(status().isOk());
		
		this.mockMvc.perform(post("/bookmarkAuction")
						.param("id", "100")
						.param("screening", "false")
						.param("bookmarked", "false")
						.principal(principal))
					.andExpect(status().isOk());
	}
	
}

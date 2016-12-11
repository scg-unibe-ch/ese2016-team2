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
public class PlaceAdControllerTest {

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
	public void getPlaceAdvertisementPage() throws Exception {
		this.mockMvc.perform(get("/profile/placeAdvertisement"))
					.andExpect(status().isOk())
					.andExpect(view().name("placeAdvertisement"));
	}
	
	@Test 
	public void getPlaceAdPage() throws Exception {
		this.mockMvc.perform(get("/profile/placeAd"))
					.andExpect(status().isOk())
					.andExpect(view().name("placeAd"));
	}
	
	@Test
	public void postInvalidPlaceAdForm() throws Exception {	
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(post("/profile/placeAd")
						.param("title", "")
						.principal(principal))
					.andExpect(status().isOk())
					.andExpect(view().name("placeAd"));
	}
	
	@Test
	public void postValidPlaceAdForm() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(post("/profile/placeAd")
				.param("title", "Test Title")
				.param("street", "Test street")
				.param("city", "3000 - Bern")
				.param("moveInDate", "21-12-2012")
				.param("moveOutDate", "")
				.param("preferences", "")
				.param("roomType", "House")
				.param("prize", "500")
				.param("squareFootage", "50")
				.param("roomDescription", "Test Description")
				.param("visits", "28-02-2014;10:02;13:14")
				.principal(principal))
			.andExpect(status().is(302)); //redirected
	}
	
	@Test
	public void testGetUploadedPictures() throws Exception {
		this.mockMvc.perform(post("/profile/placeAd/getUploadedPictures"))
					.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteUploadedPicture() throws Exception {
		this.mockMvc.perform(post("/profile/placeAd/deletePicture").param("url", "Test url")).andExpect(status().isOk());
	}
	
}

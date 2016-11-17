package ch.unibe.ese.team1.test.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import ch.unibe.ese.team1.controller.pojos.forms.MessageForm;
import ch.unibe.ese.team1.controller.service.AuctionService;
import ch.unibe.ese.team1.model.dao.UserDao;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/config/springMVC.xml",
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
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void getAd() throws Exception {
		this.mockMvc.perform(get("/ad")
						.param("id", "1"))
					.andExpect(status().isOk())
					.andExpect(view().name("adDescription"))
					.andExpect(model().attributeExists("shownAd", "messageForm", "loggedInUserEmail", "visits"));
	}
	
	@Test
	public void postAd() throws Exception {
		MessageForm messageForm = new MessageForm();
		messageForm.setRecipient("");
		messageForm.setSubject("");
		messageForm.setText("");
		this.mockMvc.perform(post("/ad")
						.param("id", "1")
						.requestAttr("messageForm", messageForm))
					.andExpect(status().isOk())
					.andExpect(view().name("adDescription"));
		
	}
	
}

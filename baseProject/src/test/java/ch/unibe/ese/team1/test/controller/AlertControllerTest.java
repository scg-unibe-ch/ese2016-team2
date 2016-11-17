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

import ch.unibe.ese.team1.controller.pojos.forms.AlertForm;
import ch.unibe.ese.team1.controller.pojos.forms.MessageForm;
import ch.unibe.ese.team1.controller.service.AlertService;
import ch.unibe.ese.team1.controller.service.AuctionService;
import ch.unibe.ese.team1.model.dao.UserDao;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/config/springMVC.xml",
		"file:src/main/webapp/WEB-INF/config/springData.xml",
		"file:src/main/webapp/WEB-INF/config/springSecurity.xml" })
@WebAppConfiguration
public class AlertControllerTest {
	
	@Autowired
	WebApplicationContext wac;
	@Autowired
	MockHttpServletRequest request;
	
	private MockMvc mockMvc;
	
	@Autowired
	AlertService alertService;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void getAlert() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.ch");
		
		this.mockMvc.perform(get("/profile/alerts")
						.principal(principal))
					.andExpect(status().isOk())
					.andExpect(view().name("alerts"))
					.andExpect(model().attributeExists("alerts", "alertForm"));
	}
	
	@Test
	public void postAlertNoErrors() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.ch");
		
		AlertForm alertForm = new AlertForm();
		alertForm.setCity("3000 - Bern");
		alertForm.setRadius(100);
		alertForm.setPrice(500);
		alertForm.setNoRoomNoStudio(false);
		
		this.mockMvc.perform(post("/profile/alerts")
						.principal(principal)
						.requestAttr("alertForm", alertForm))
					.andExpect(status().isOk())
					.andExpect(view().name("alerts"))
					.andExpect(model().attributeExists("alertForm"));
		
	}
	
	@Test
	public void postAlertWithErrors() throws Exception {
		AlertForm alertForm = new AlertForm();
		alertForm.setCity("3000 - Bern");
		alertForm.setRadius(100);
		alertForm.setPrice(500);
		alertForm.setNoRoomNoStudio(true);
		
		this.mockMvc.perform(post("/profile/alerts")
						.requestAttr("alertForm", alertForm))
					.andExpect(status().isOk())
					.andExpect(view().name("alerts"));
	}

}

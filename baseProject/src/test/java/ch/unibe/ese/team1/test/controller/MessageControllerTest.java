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
public class MessageControllerTest {

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
	public void getMessages() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(get("/profile/messages")
						.principal(principal))
					.andExpect(status().isOk())
					.andExpect(view().name("messages"))
					.andExpect(model().attributeExists("messageForm", "messages"));
	}
	
	@Test
	public void postInvalidMessage() throws Exception {		
		this.mockMvc.perform(post("/profile/messages")
						.param("recipient", ""))
					.andExpect(status().isOk())
					.andExpect(view().name("messages"));
	}
	
	@Test
	public void testMessages() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(post("/profile/message/inbox")
						.principal(principal))
					.andExpect(status().isOk());
		this.mockMvc.perform(post("/profile/message/sent")
						.principal(principal))
					.andExpect(status().isOk());
		this.mockMvc.perform(get("/profile/messages/getMessage").param("id", "1"))
					.andExpect(status().isOk());
		this.mockMvc.perform(get("/profile/readMessage").param("id", "1"))
		 			.andExpect(status().isOk());
	}
	
	@Test
	public void testValidateEmail() throws Exception {
		MvcResult result = this.mockMvc.perform(post("/profile/messages/validateEmail")
											.param("email", "user@bern.ch"))
										.andExpect(status().isOk())
										.andReturn();
		String feedback = result.getResponse().getContentAsString();
		
		assertEquals("\"This user does not exist.\"", feedback);
		
		result = this.mockMvc.perform(post("/profile/messages/validateEmail")
										.param("email", "user@bern.com"))
									.andExpect(status().isOk())
									.andReturn();
		feedback = result.getResponse().getContentAsString();

		assertEquals("\"user@bern.com\"", feedback);
	}
	
	@Test
	public void testUnreadMessagesAndSendMessage() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		MvcResult resultBefore = this.mockMvc.perform(get("/profile/unread")
												.principal(principal))
											.andExpect(status().isOk())
											.andReturn();
		
		int numberBefore = Integer.parseInt(resultBefore.getResponse().getContentAsString());
		
		this.mockMvc.perform(post("/profile/messages/sendMessage")
						.principal(principal)
						.param("subject", "asdfasdf")
						.param("text", "asdfasdf")
						.param("recipientEmail", "user@bern.com"))
					.andExpect(status().isOk());
		
		MvcResult resultAfter = this.mockMvc.perform(get("/profile/unread")
												.principal(principal))
											.andExpect(status().isOk())
											.andReturn();

		int numberAfter = Integer.parseInt(resultAfter.getResponse().getContentAsString());
		
		assertEquals(numberBefore+1, numberAfter);
	} 
}

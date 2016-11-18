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
public class ProfileControllerTest {
	
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
	public void getLoginPage() throws Exception {
		this.mockMvc.perform(get("/login"))
					.andExpect(view().name("login"))
					.andExpect(status().isOk());
	}
	
	@Test
	public void getSignupPage() throws Exception {
		this.mockMvc.perform(get("/signup"))
					.andExpect(view().name("signup"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("signupForm"));
	}
	
	@Test
	public void getRegisterPage() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(get("/register").principal(principal))
					.andExpect(status().isOk())
					.andExpect(view().name("register"))
					.andExpect(model().attributeExists("registerForm"));
	}
	
	@Test
	public void postInvalidSignupFrom() throws Exception {
		this.mockMvc.perform(post("/signup")
						.param("password", ""))
					.andExpect(status().isOk())
					.andExpect(view().name("signup"))
					.andExpect(model().attributeExists("signupForm"));
	}
	
	@Test
	public void postValidSignupFrom() throws Exception {
		this.mockMvc.perform(post("/signup")
						.param("password", "123456")
						.param("email", "test@test.test")
						.param("firstName", "Test")
						.param("lastName", "Person")
						.param("account", "Premium")
						.param("gender", "MALE"))
					.andExpect(status().isOk())
					.andExpect(view().name("login"))
					.andExpect(model().attributeExists("confirmationMessage"));
	}
	
	@Test
	public void getEditProfilePage() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(get("/profile/editProfile")
						.principal(principal))
					.andExpect(status().isOk())
					.andExpect(view().name("editProfile"))
					.andExpect(model().attributeExists("editProfileForm", "currentUser"));
	}
	
	@Test
	public void postInvalidEditProfileForm() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(post("/profile/editProfile")
						.principal(principal)
						.param("username", ""))
					.andExpect(status().isOk())
					.andExpect(view().name("updatedProfile"))
					.andExpect(model().attributeExists("message"));
	}
	
	@Test
	public void postValidEditProfileForm() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(post("/profile/editProfile")
						.principal(principal)
						.param("username", "user@bern.com")
						.param("password", "password")
						.param("firstName", "Test name")
						.param("lastName", "Test name"))
					.andExpect(status().isOk())
					.andExpect(view().name("updatedProfile"))
					.andExpect(model().attributeExists("message", "currentUser"));
	}
	
	@Test
	public void getRegisterProfilePage() throws Exception {
		this.mockMvc.perform(get("/profile/registerProfile"))
					.andExpect(status().isOk())
					.andExpect(view().name("register"));
	}
	
	@Test
	public void postInvalidRegisterForm() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(post("/profile/registerProfile")
						.principal(principal)
						.param("username", ""))
					.andExpect(status().isOk())
					.andExpect(view().name("register"));
	}
	
	@Test
	public void postValidRegisterForm() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(post("/profile/registerProfile")
						.principal(principal)
						.param("username", "user@bern.com")
						.param("street", "Test street")
						.param("city", "3000 - Bern")
						.param("account", "Premium"))
					.andExpect(status().isOk())
					.andExpect(view().name("updatedProfile"))
					.andExpect(model().attributeExists("message", "currentUser"));
	}
	
	@Test
	public void getUserPagePrincipalNull() throws Exception {
		this.mockMvc.perform(get("/user")
						.param("id", "1"))
					.andExpect(status().isOk())
					.andExpect(view().name("user"))
					.andExpect(model().attributeExists("user", "messageForm"))
					.andExpect(model().attributeDoesNotExist("principalID"));
	}
	
	@Test
	public void getUserPage() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(get("/user")
						.param("id", "1")
						.principal(principal))
					.andExpect(status().isOk())
					.andExpect(view().name("user"))
					.andExpect(model().attributeExists("user", "messageForm", "principalID"));
	}
	
	@Test
	public void getSchedulePage() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user@bern.com");
		
		this.mockMvc.perform(get("/profile/schedule")
						.principal(principal))
					.andExpect(status().isOk())
					.andExpect(view().name("schedule"))
					.andExpect(model().attributeExists("visits", "presentations"));
	}
	
	@Test
	public void getVisitorsPage() throws Exception {		
		this.mockMvc.perform(get("/profile/visitors")
						.param("visit", "1"))
					.andExpect(status().isOk())
					.andExpect(view().name("visitors"))
					.andExpect(model().attributeExists("visitors"));
	}
}

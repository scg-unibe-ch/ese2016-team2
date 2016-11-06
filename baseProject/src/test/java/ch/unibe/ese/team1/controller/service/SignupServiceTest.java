package ch.unibe.ese.team1.controller.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ch.unibe.ese.team1.controller.pojos.forms.PlaceAdForm;
import ch.unibe.ese.team1.controller.pojos.forms.SignupForm;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Gender;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.UserRole;
import ch.unibe.ese.team1.model.dao.UserDao;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/config/springMVC.xml",
		"file:src/main/webapp/WEB-INF/config/springData.xml",
		"file:src/main/webapp/WEB-INF/config/springSecurity.xml"})
@WebAppConfiguration
public class SignupServiceTest {

	@Autowired
	private SignupService signupService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDao;

	/**
	 *
	 */
	@Test
	public void checkSignupForm() throws ParseException {
		//Preparation
		SignupForm signupForm = new SignupForm();
		signupForm.setEmail("hans.muster@ese.ese");
		signupForm.setFirstName("Hans");
		signupForm.setLastName("Muster");
		signupForm.setPassword("password");
		signupForm.setGender(Gender.MALE);
		signupForm.setAccount("Normal");
		signupService.saveFrom(signupForm);
		
		User currentUser = userService.findUserByUsername("hans.muster@ese.ese");
		
		//Testing
		assertTrue(signupService.doesUserWithUsernameExist("hans.muster@ese.ese"));
		assertEquals(signupForm.getEmail(), currentUser.getEmail());
		assertEquals(signupForm.getFirstName(), currentUser.getFirstName());
		assertEquals(signupForm.getLastName(), currentUser.getLastName());
		assertEquals(signupForm.getPassword(), currentUser.getPassword());
		assertEquals(signupForm.getGender(), currentUser.getGender());
		assertEquals(signupForm.getAccount(), currentUser.getAccount());
	}	
	
	private User createUser(String email, String password, String firstName,
			String lastName, Gender gender, String account) {
		User user = new User();
		user.setUsername(email);
		user.setPassword(password);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEnabled(true);
		user.setGender(gender);
		user.setAccount(account);
		Set<UserRole> userRoles = new HashSet<>();
		UserRole role = new UserRole();
		role.setRole("ROLE_USER");
		role.setUser(user);
		userRoles.add(role);
		user.setUserRoles(userRoles);
		return user;
	}
	
}

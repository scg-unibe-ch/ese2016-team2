package ch.unibe.ese.team1.test.controller.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.HashSet;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ch.unibe.ese.team1.controller.pojos.forms.RegisterForm;
import ch.unibe.ese.team1.controller.service.RegisterService;
import ch.unibe.ese.team1.controller.service.UserService;
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
public class RegisterServiceTest {

	@Autowired
	private RegisterService registerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDao;

	/**
	 * 
	 */
	@Test
	public void upgradeToPremiumAccount() throws ParseException {
		//Preparation
		
		User testPersonRegister1 = createUser("testPersonRegister@1.ch", "password", "testPerson", "Register1",
				Gender.MALE, "Premium");
		testPersonRegister1.setAboutMe("TestPersonRegister1");
		userDao.save(testPersonRegister1);
		
		RegisterForm registerForm = new RegisterForm();
		registerForm.setUsername("testPersonRegister@1.ch");
		registerForm.setStreet("Schanzeneckstrasse 2");
		registerForm.setCity("3012 - Bern");
		registerForm.setAccount("Premium");
		registerService.updateForm(registerForm);
		User currentUser = userService.findUserByUsername(registerForm.getUsername());
		
		//Testing
		assertEquals("Schanzeneckstrasse 2", currentUser.getStreet());
		assertEquals("Bern", currentUser.getCity());
		assertEquals(3012, currentUser.getZipcode());
		assertEquals("Premium", currentUser.getAccount());
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

package ch.unibe.ese.team1.test.controller.service;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ch.unibe.ese.team1.controller.pojos.forms.EditProfileForm;
import ch.unibe.ese.team1.controller.service.UserService;
import ch.unibe.ese.team1.controller.service.UserUpdateService;
import ch.unibe.ese.team1.model.Gender;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.UserPicture;
import ch.unibe.ese.team1.model.UserRole;
import ch.unibe.ese.team1.model.dao.UserDao;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/config/springMVC.xml",
		"file:src/main/webapp/WEB-INF/config/springData.xml",
		"file:src/main/webapp/WEB-INF/config/springSecurity.xml"})
@WebAppConfiguration
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserUpdateService userUpdateService;

	@Test
	public void findUserById() throws ParseException {
		//Preparation		
		User currentUser = userService.findUserById(1);
		
		//Testing
		assertEquals(1, currentUser.getId());
		assertEquals("Admin", currentUser.getLastName());
		assertEquals("1234", currentUser.getPassword());
	}
	
	@Test
	public void testUserUpdateService() {
		User testUser = createUser("testUser@User.ch", "12345", "testUser", "User", Gender.MALE, "Normal");
		testUser.setAboutMe("Test Person for UserService Test");
		userDao.save(testUser);
		
		//test findUserByUsername
		User currentUser = userService.findUserByUsername("testUser@User.ch");
		
		UserPicture userPic = new UserPicture();
		userPic.setFilePath("asdfasd");
		userPic.setUser(currentUser);
		currentUser.setPicture(userPic);
		
		userDao.save(currentUser);
		
		long userId = currentUser.getId();
		
		assertEquals("testUser@User.ch", currentUser.getEmail());
		assertEquals("testUser", currentUser.getFirstName());
		assertEquals("User", currentUser.getLastName());
		assertEquals("12345", currentUser.getPassword());
		
		EditProfileForm editForm = new EditProfileForm();
		editForm.setUsername("testUser@User.ch");
		editForm.setFirstName("testUser");
		editForm.setLastName("UserUpdate");
		editForm.setPassword("123456");
		editForm.setImagePath("Test path");
		assertEquals("Test path", editForm.getImagePath());
		editForm.setAboutMe("Test Person for UserUpdateSerice Test");
		
		userUpdateService.updateForm(editForm);
		
		User updatedUser = userService.findUserById(userId);
		
		assertEquals("testUser@User.ch", updatedUser.getEmail());
		assertEquals("testUser", updatedUser.getFirstName());
		assertEquals("UserUpdate", updatedUser.getLastName());
		assertEquals("123456", updatedUser.getPassword());
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

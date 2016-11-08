package ch.unibe.ese.team1.test.controller.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ch.unibe.ese.team1.controller.service.UserService;
import ch.unibe.ese.team1.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/config/springMVC.xml",
		"file:src/main/webapp/WEB-INF/config/springData.xml",
		"file:src/main/webapp/WEB-INF/config/springSecurity.xml"})
@WebAppConfiguration
public class UserServiceTest {
	
	@Autowired
	private UserService userService;

	/**
	 *
	 */
	@Test
	public void findUserById() throws ParseException {
		//Preparation		
		User currentUser = userService.findUserById(1);
		
		//Testing
		assertEquals(1, currentUser.getId());
		assertEquals("Admin", currentUser.getLastName());
		assertEquals("1234", currentUser.getPassword());
	}	
}

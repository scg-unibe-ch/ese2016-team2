package ch.unibe.ese.team1.test.controller.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ch.unibe.ese.team1.controller.service.AlertService;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Alert;
import ch.unibe.ese.team1.model.Gender;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.UserRole;
import ch.unibe.ese.team1.model.dao.AdDao;
import ch.unibe.ese.team1.model.dao.AlertDao;
import ch.unibe.ese.team1.model.dao.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/config/springMVC.xml",
		"file:src/main/webapp/WEB-INF/config/springData.xml",
		"file:src/main/webapp/WEB-INF/config/springSecurity.xml"})
@WebAppConfiguration
public class AlertServiceTest {
	
	@Autowired
	AdDao adDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	AlertDao alertDao;
	
	@Autowired
	AlertService alertService;
	
	@Test
	public void createAlerts() {
		ArrayList<Alert> alertList = new ArrayList<Alert>();
		
		// Create user testPersonAlert1
		User testPersonAlert1 = createUser("testPersonAlert@1.ch", "password", "testPerson", "Alert1",
				Gender.MALE, "Normal");
		testPersonAlert1.setAboutMe("Wallis rocks");
		userDao.save(testPersonAlert1);
		
		// Create 2 alerts for first test Person
		Alert alert = new Alert();
		alert.setUser(testPersonAlert1);
		alert.setAlertType("Studio");
		alert.setRoom(false);
		alert.setStudio(true);
		alert.setHouse(false);
		alert.setCity("Bern");
		alert.setZipcode(3000);
		alert.setPrice(1500);
		alert.setRadius(100);
		alertDao.save(alert);
		
		alert = new Alert();
		alert.setUser(testPersonAlert1);
		alert.setAlertType("Room and Studio");
		alert.setRoom(true);
		alert.setStudio(true);
		alert.setHouse(false);
		alert.setCity("Bern");
		alert.setZipcode(3002);
		alert.setPrice(1000);
		alert.setRadius(5);
		alertDao.save(alert);
		
		//copy alerts to a list
		Iterable<Alert> alerts = alertService.getAlertsByUser(testPersonAlert1);
		for(Alert returnedAlert: alerts)
			alertList.add(returnedAlert);
		
		//begin the actual testing
		assertEquals(2, alertList.size());
		assertEquals(testPersonAlert1, alertList.get(0).getUser());
		assertEquals("Bern", alertList.get(1).getCity());
		assertTrue(alertList.get(0).getRadius() > alertList.get(1).getRadius());
	}
	
	@Test
	public void mismatchChecks() {
		ArrayList<Alert> alertList = new ArrayList<Alert>();
		
		User testPersonAlert2 = createUser("testPersonAlert@2.ch", "password", "testPerson", "Alert2",
				Gender.MALE, "Premium");
		testPersonAlert2.setAboutMe("Supreme hustler");
		userDao.save(testPersonAlert2);
		
		// Create 2 alerts for second test person
		Alert alert = new Alert();
		alert.setUser(testPersonAlert2);
		alert.setAlertType("Studio");
		alert.setRoom(false);
		alert.setStudio(true);
		alert.setHouse(false);
		alert.setCity("Bern");
		alert.setZipcode(3000);
		alert.setPrice(1500);
		alert.setRadius(100);
		alertDao.save(alert);
		
		alert = new Alert();
		alert.setUser(testPersonAlert2);
		alert.setAlertType("Room and Studio");
		alert.setRoom(true);
		alert.setStudio(true);
		alert.setHouse(false);
		alert.setCity("Bern");
		alert.setZipcode(3002);
		alert.setPrice(1000);
		alert.setRadius(5);
		alertDao.save(alert);
		
		Iterable<Alert> alerts = alertService.getAlertsByUser(userDao.findByUsername("testPersonAlert@2.ch"));
		for(Alert returnedAlert: alerts)
			alertList.add(returnedAlert);
		
		//save an ad
		Date date = new Date();
		Ad oltenResidence= new Ad();
		oltenResidence.setZipcode(4600);
		oltenResidence.setMoveInDate(date);
		oltenResidence.setCreationDate(date);
		oltenResidence.setPrize(1200);
		oltenResidence.setSquareFootage(42);
		oltenResidence.setRoomType("Room");
		oltenResidence.setSmokers(true);
		oltenResidence.setAnimals(false);
		oltenResidence.setRoomDescription("blah");
		oltenResidence.setPreferences("blah");
		oltenResidence.setUser(testPersonAlert2);
		oltenResidence.setTitle("Olten Residence");
		oltenResidence.setStreet("Florastr. 100");
		oltenResidence.setCity("Olten");
		oltenResidence.setGarden(false);
		oltenResidence.setBalcony(false);
		oltenResidence.setCellar(false);
		oltenResidence.setFurnished(false);
		oltenResidence.setCable(false);
		oltenResidence.setGarage(false);
		oltenResidence.setInternet(false);
		adDao.save(oltenResidence);
		
		assertFalse(alertService.radiusMismatch(oltenResidence, alertList.get(0)));
		assertTrue(alertService.radiusMismatch(oltenResidence, alertList.get(1)));
		assertTrue(alertService.typeMismatch(oltenResidence, alertList.get(0)));
		assertFalse(alertService.typeMismatch(oltenResidence, alertList.get(1)));
	}
	
	//Lean user creating method
	User createUser(String email, String password, String firstName,
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

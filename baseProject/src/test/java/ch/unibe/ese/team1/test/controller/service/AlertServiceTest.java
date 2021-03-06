package ch.unibe.ese.team1.test.controller.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ch.unibe.ese.team1.controller.pojos.forms.AlertForm;
import ch.unibe.ese.team1.controller.pojos.forms.PlaceAdForm;
import ch.unibe.ese.team1.controller.service.AdService;
import ch.unibe.ese.team1.controller.service.AlertService;
import ch.unibe.ese.team1.controller.service.MessageService;
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
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	AdService adService;
	
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
		alert.setCity("Bern");
		alert.setZipcode(3000);
		alert.setPrice(1500);
		alert.setRadius(100);
		alertDao.save(alert);
		
		alert = new Alert();
		alert.setUser(testPersonAlert1);
		alert.setAlertType("Room and Studio");
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
		alert.setCity("Bern");
		alert.setZipcode(3000);
		alert.setPrice(1500);
		alert.setRadius(100);
		alertDao.save(alert);
		
		alert = new Alert();
		alert.setUser(testPersonAlert2);
		alert.setAlertType("Room and Studio");
		alert.setCity("Bern");
		alert.setZipcode(3008);
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
	
	//tests radius mismatch too
	@Test
	public void testSaveFrom() {
		AlertForm alertForm = new AlertForm();
		alertForm.setAlertType("Room and Studio");
		alertForm.setCity("3000 - Bern");
		alertForm.setPrice(100);
		alertForm.setRadius(10);
		
		User testUserAlert = createUser("testUser@Alert.ch", "123456", "testUser", "Alert", Gender.MALE, "Normal");
		testUserAlert.setAboutMe("Test Person Alert");
		userDao.save(testUserAlert);
		
		User testUserAd = createUser("testUser@Ad.ch", "1234567", "testUser", "Ad", Gender.MALE, "Premium");
		testUserAd.setAboutMe("Test Person which places Ad");
		userDao.save(testUserAd);
		
		assertEquals(0, messageService.unread(userDao.findByUsername("testUser@Alert.ch").getId()));
		
		alertService.saveFrom(alertForm, testUserAlert);
		
		PlaceAdForm placeAdForm = new PlaceAdForm();
		placeAdForm.setTitle("Test Ad");
		placeAdForm.setStreet("Test Street");
		placeAdForm.setCity("9000 - St. Gallen");
		placeAdForm.setMoveInDate("12-12-2012");
		placeAdForm.setMoveOutDate("");
		placeAdForm.setPrize(50);
		placeAdForm.setSquareFootage(50);
		placeAdForm.setRoomDescription("Test description");
		placeAdForm.setPreferences("Test preference");
		placeAdForm.setRoomType("Room");
		
		List<String> filePaths = new ArrayList<String>();
		filePaths.add("/img/test/ad1_1.jpg");
		
		adService.saveFrom(placeAdForm, filePaths, userDao.findByUsername("testUser@Ad.ch"));
		
		Iterable<Ad> ads = adDao.findAll();
		Ad ad = new Ad();
		for (Ad tempAd: ads) {
			ad = tempAd;
		}
		alertService.triggerAlerts(ad);
		
		assertEquals(0, messageService.unread(userDao.findByUsername("testUser@Alert.ch").getId()));
	
		placeAdForm.setTitle("Test Ad");
		placeAdForm.setStreet("Test Street");
		placeAdForm.setCity("3000 - Bern");
		placeAdForm.setMoveInDate("12-12-2012");
		placeAdForm.setMoveOutDate("");
		placeAdForm.setPrize(50);
		placeAdForm.setSquareFootage(50);
		placeAdForm.setRoomDescription("Test description");
		placeAdForm.setPreferences("Test preference");
		placeAdForm.setRoomType("Room");
		
		adService.saveFrom(placeAdForm, filePaths, userDao.findByUsername("testUser@Ad.ch"));
		
		ads = adDao.findAll();
		ad = new Ad();
		for (Ad tempAd: ads) {
			ad = tempAd;
		}
		alertService.triggerAlerts(ad);
		
		assertEquals(1, messageService.unread(userDao.findByUsername("testUser@Alert.ch").getId()));
	}
	
	@Test
	public void testTypeMismatch() {
		Alert alert = new Alert();
		Ad room = new Ad();
		Ad studio = new Ad();
		Ad house = new Ad();
		
		alert.setAlertType("Room and Studio");
		room.setRoomType("Room");
		studio.setRoomType("Studio");
		house.setRoomType("House");
		assertFalse(alertService.typeMismatch(room, alert));
		assertFalse(alertService.typeMismatch(studio, alert));
		assertTrue(alertService.typeMismatch(house, alert));
		
		alert.setAlertType("Room and House");
		assertFalse(alertService.typeMismatch(room, alert));
		assertTrue(alertService.typeMismatch(studio, alert));
		assertFalse(alertService.typeMismatch(house, alert));
		
		alert.setAlertType("Studio and House");
		assertTrue(alertService.typeMismatch(room, alert));
		assertFalse(alertService.typeMismatch(studio, alert));
		assertFalse(alertService.typeMismatch(house, alert));
		
		alert.setAlertType("All");
		assertFalse(alertService.typeMismatch(room, alert));
		assertFalse(alertService.typeMismatch(studio, alert));
		assertFalse(alertService.typeMismatch(house, alert));
		
		alert.setAlertType("Room");
		assertFalse(alertService.typeMismatch(room, alert));
		assertTrue(alertService.typeMismatch(studio, alert));
		assertTrue(alertService.typeMismatch(house, alert));
	}
	
	//Lean user creating method
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

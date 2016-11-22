package ch.unibe.ese.team1.test.scheduler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ch.unibe.ese.team1.controller.pojos.forms.PlaceAuctionForm;
import ch.unibe.ese.team1.controller.service.AuctionService;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.Gender;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.UserRole;
import ch.unibe.ese.team1.model.dao.AuctionDao;
import ch.unibe.ese.team1.model.dao.UserDao;
import ch.unibe.ese.team1.scheduler.AuctionSchedule;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/config/springMVC.xml",
		"file:src/main/webapp/WEB-INF/config/springData.xml",
		"file:src/main/webapp/WEB-INF/config/springSecurity.xml"})
@WebAppConfiguration
public class AuctionSchedulerTest {

	@Autowired
	private AuctionService auctionService;
	
	@Autowired
	private AuctionSchedule auctionSchedule;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired 
	private AuctionDao auctionDao;
	
	private PlaceAuctionForm placeAuctionForm = new PlaceAuctionForm();
	private ArrayList<String> filePaths = new ArrayList<String>();

	@Before
	public void placeAuctionInDataBase() {
		// Preparation
		placeAuctionForm.setCity("3000-Bern");
		placeAuctionForm.setPreferences("Test preferences");
		placeAuctionForm.setRoomDescription("Test Room description");
		placeAuctionForm.setPrize(600);
		placeAuctionForm.setSquareFootage(50);
		placeAuctionForm.setTitle("title");
		placeAuctionForm.setStreet("Hauptstrasse 13");
		placeAuctionForm.setRoomType("Studio");
		placeAuctionForm.setMoveInDate("27-02-2015");
		placeAuctionForm.setEndDate("12-12-2014");
		placeAuctionForm.setEndTime("12:00");

		placeAuctionForm.setSmokers(true);
		placeAuctionForm.setAnimals(false);
		placeAuctionForm.setGarden(true);
		placeAuctionForm.setBalcony(false);
		placeAuctionForm.setCellar(true);
		placeAuctionForm.setFurnished(false);
		placeAuctionForm.setCable(false);
		placeAuctionForm.setGarage(true);
		placeAuctionForm.setInternet(false);
		
		filePaths.add("/img/test/ad1_1.jpg");
		
		User testPersonAuctionSchedule1 = createUser("testPersonAuctionSchedule@1.ch", "password", "testPerson", "AuctionSchedule1", Gender.MALE, "Normal");
		testPersonAuctionSchedule1.setAboutMe("TestPersonAuctionSchedule1");
		userDao.save(testPersonAuctionSchedule1);

		auctionService.saveFrom(placeAuctionForm, filePaths, testPersonAuctionSchedule1);
		
		Auction auction1 = new Auction();
		Iterable<Auction> ads = auctionService.getAllAds();
		Iterator<Auction> iterator = ads.iterator();

		while (iterator.hasNext()) {
			auction1 = iterator.next();
		}
		
		assertFalse(auction1.getAuctionEnded());
	}
	
	@Test
	public void checkEndOfAuction() {
		auctionSchedule.updateAuctions();
		
		Auction auction1 = new Auction();
		Iterable<Auction> ads = auctionService.getAllAds();
		Iterator<Auction> iterator = ads.iterator();

		while (iterator.hasNext()) {
			auction1 = iterator.next();
		}
		
		try {
			assertTrue(new SimpleDateFormat("HH:mm, dd.MM.yyyy").parse(auction1.getEndTime()).before(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		assertTrue(auction1.getAuctionEnded());
		
		User testPersonAuctionSchedule2 = createUser("testPersonAuctionSchedule@2.ch", "password", "testPerson", "AuctionSchedule2", Gender.MALE, "Normal");
		testPersonAuctionSchedule2.setAboutMe("TestPersonAuctionSchedule2");
		userDao.save(testPersonAuctionSchedule2);
		
		auction1.setAuctionEnded(false);
		auction1.setBidderName("testPersonAuctionSchedule@2");
		auctionDao.save(auction1);
		
		auctionSchedule.updateAuctions();
		
		Auction auction2 = new Auction();
		Iterable<Auction> ads2 = auctionService.getAllAds();
		Iterator<Auction> iterator2 = ads2.iterator();

		while (iterator2.hasNext()) {
			auction2 = iterator2.next();
		}
		
		assertTrue(auction2.getAuctionEnded());
	}
	
	private User createUser(String email, String password, String firstName, String lastName, Gender gender,
			String account) {
		User user = new User();
		user.setUsername(email);
		user.setPassword(password);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEnabled(true);
		user.setGender(gender);
		Set<UserRole> userRoles = new HashSet<>();
		UserRole role = new UserRole();
		role.setRole("ROLE_USER");
		role.setUser(user);
		userRoles.add(role);
		user.setUserRoles(userRoles);
		user.setAccount(account);
		return user;
	}

}

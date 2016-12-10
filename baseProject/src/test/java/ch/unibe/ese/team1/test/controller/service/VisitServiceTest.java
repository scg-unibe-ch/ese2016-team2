package ch.unibe.ese.team1.test.controller.service;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ch.unibe.ese.team1.controller.pojos.forms.PlaceAdForm;
import ch.unibe.ese.team1.controller.pojos.forms.PlaceAuctionForm;
import ch.unibe.ese.team1.controller.service.AdService;
import ch.unibe.ese.team1.controller.service.AuctionService;
import ch.unibe.ese.team1.controller.service.VisitService;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.Gender;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.UserRole;
import ch.unibe.ese.team1.model.Visit;
import ch.unibe.ese.team1.model.dao.AdDao;
import ch.unibe.ese.team1.model.dao.AuctionDao;
import ch.unibe.ese.team1.model.dao.UserDao;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/config/springMVC.xml",
		"file:src/main/webapp/WEB-INF/config/springData.xml",
		"file:src/main/webapp/WEB-INF/config/springSecurity.xml"})
@WebAppConfiguration
public class VisitServiceTest {

	@Autowired
	VisitService visitService;
	
	@Autowired
	AdService adService;
	
	@Autowired
	AdDao adDao;
	
	@Autowired
	AuctionService auctionService;
	
	@Autowired
	AuctionDao auctionDao;
	
	@Autowired
	UserDao userDao;
	
	@Test
	public void testVisitSericeWithAd() {
		User testUserCreator = createUser("testUser@Creator.ch", "password", "testUser", "Creator", Gender.MALE, "Normal");
		testUserCreator.setAboutMe("Test Person to creat an Ad");
		userDao.save(testUserCreator);
		
		PlaceAdForm placeAdForm = new PlaceAdForm();
		placeAdForm.setTitle("Test Ad");
		placeAdForm.setStreet("Test street");
		placeAdForm.setCity("3000 - Bern");
		placeAdForm.setRoomType("Room");
		placeAdForm.setRoomDescription("Test description");
		placeAdForm.setPreferences("Test preference");
		placeAdForm.setMoveInDate("12-12-2012");
		placeAdForm.setMoveOutDate("");
		placeAdForm.setPrize(1000);
		placeAdForm.setSquareFootage(50);
		List<String> visits = new ArrayList<String>();
		String visit1 = "28-02-2014;10:02;13:14";
		String visit2 = "27-02-2014;10:02;13:14";
		visits.add(visit1);
		visits.add(visit2);
		placeAdForm.setVisits(visits);
		
		List<String> filePaths = new ArrayList<String>();
		filePaths.add("/img/test/ad1_1.jpg");
		
		adService.saveFrom(placeAdForm, filePaths, userDao.findByUsername("testUser@Creator.ch"));
		
		Iterable<Ad> ads = adDao.findByUser(userDao.findByUsername("testUser@Creator.ch"));
		Ad ad = new Ad();
		for (Ad tempAd: ads) {
			ad = tempAd;
		}
		Iterable<Visit> iterableVisits = visitService.getVisitsByAd(ad);
		Iterator<Visit> iterator = iterableVisits.iterator();
		List<Visit> adVisits = new ArrayList<Visit>();
		while(iterator.hasNext()) {
			adVisits.add(iterator.next());
		}
		assertEquals(2, adVisits.size());
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		assertEquals("27-02-2014 10:02", format.format(adVisits.get(0).getStartTimestamp()));
		assertEquals("27-02-2014 13:14", format.format(adVisits.get(0).getEndTimestamp()));
		assertEquals("28-02-2014 10:02", format.format(adVisits.get(1).getStartTimestamp()));
		assertEquals("28-02-2014 13:14", format.format(adVisits.get(1).getEndTimestamp()));
	}
	
	@Test
	public void testVisitSericeWithAuction() {
		User testUserCreatorAuction = createUser("testUser@CreatorAuction.ch", "password", "testUser", "Creator", Gender.MALE, "Normal");
		testUserCreatorAuction.setAboutMe("Test Person to creat an Auction");
		userDao.save(testUserCreatorAuction);
		
		PlaceAuctionForm placeAdForm = new PlaceAuctionForm();
		placeAdForm.setTitle("Test Ad");
		placeAdForm.setStreet("Test street");
		placeAdForm.setCity("3000 - Bern");
		placeAdForm.setRoomType("Room");
		placeAdForm.setRoomDescription("Test description");
		placeAdForm.setPreferences("Test preference");
		placeAdForm.setMoveInDate("12-12-2012");
		placeAdForm.setEndDate("12.12.2017");
		placeAdForm.setEndTime("12:12");
		placeAdForm.setPrize(1000);
		placeAdForm.setSquareFootage(50);
		List<String> visits = new ArrayList<String>();
		String visit1 = "28-02-2014;10:02;13:14";
		String visit2 = "27-02-2014;10:02;13:14";
		visits.add(visit1);
		visits.add(visit2);
		placeAdForm.setVisits(visits);
		
		List<String> filePaths = new ArrayList<String>();
		filePaths.add("/img/test/ad1_1.jpg");
		
		auctionService.saveFrom(placeAdForm, filePaths, userDao.findByUsername("testUser@Creator.ch"));
		
		Iterable<Auction> auctions = auctionDao.findByUser(userDao.findByUsername("testUser@Creator.ch"));
		Auction ad = new Auction();
		for (Auction tempAd: auctions) {
			ad = tempAd;
		}
		Iterable<Visit> iterableVisits = visitService.getVisitsByAuction(ad);
		Iterator<Visit> iterator = iterableVisits.iterator();
		List<Visit> adVisits = new ArrayList<Visit>();
		while(iterator.hasNext()) {
			adVisits.add(iterator.next());
		}
		assertEquals(2, adVisits.size());
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		assertEquals("27-02-2014 10:02", format.format(adVisits.get(0).getStartTimestamp()));
		assertEquals("27-02-2014 13:14", format.format(adVisits.get(0).getEndTimestamp()));
		assertEquals("28-02-2014 10:02", format.format(adVisits.get(1).getStartTimestamp()));
		assertEquals("28-02-2014 13:14", format.format(adVisits.get(1).getEndTimestamp()));
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

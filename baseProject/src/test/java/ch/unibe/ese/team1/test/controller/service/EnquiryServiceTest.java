package ch.unibe.ese.team1.test.controller.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import ch.unibe.ese.team1.controller.service.EnquiryService;
import ch.unibe.ese.team1.controller.service.VisitService;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.Gender;
import ch.unibe.ese.team1.model.Rating;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.UserRole;
import ch.unibe.ese.team1.model.Visit;
import ch.unibe.ese.team1.model.VisitEnquiry;
import ch.unibe.ese.team1.model.VisitEnquiryState;
import ch.unibe.ese.team1.model.dao.AdDao;
import ch.unibe.ese.team1.model.dao.AuctionDao;
import ch.unibe.ese.team1.model.dao.RatingDao;
import ch.unibe.ese.team1.model.dao.UserDao;
import ch.unibe.ese.team1.model.dao.VisitDao;
import ch.unibe.ese.team1.model.dao.VisitEnquiryDao;

/**
 * 
 * Tests both Visit and VisitEnquiry functionality. Since one makes no sense
 * without the other, these tests were grouped into one suite.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/config/springMVC.xml",
		"file:src/main/webapp/WEB-INF/config/springData.xml",
		"file:src/main/webapp/WEB-INF/config/springSecurity.xml" })
@WebAppConfiguration
public class EnquiryServiceTest {

	@Autowired
	VisitService visitService;

	@Autowired
	EnquiryService enquiryService;

	@Autowired
	AdDao adDao;

	@Autowired
	UserDao userDao;

	@Autowired
	VisitDao visitDao;

	@Autowired
	AuctionDao auctionDao;
	
	@Autowired
	RatingDao ratingDao;

	@Autowired
	VisitEnquiryDao visitEnquiryDao;

	@Test
	public void createVisits() throws Exception {
		// create user
		User thomyF = createUser("thomy@f.ch", "password", "Thomy", "F", Gender.MALE, "Normal");
		thomyF.setAboutMe("Supreme hustler");
		userDao.save(thomyF);

		// save an ad
		Date date = new Date();
		Ad oltenResidence = new Ad();
		oltenResidence.setZipcode(4600);
		oltenResidence.setMoveInDate(date);
		oltenResidence.setCreationDate(date);
		oltenResidence.setPrize(1200);
		oltenResidence.setSquareFootage(42);
		oltenResidence.setSmokers(true);
		oltenResidence.setRoomType("Room");
		oltenResidence.setAnimals(false);
		oltenResidence.setRoomDescription("blah");
		oltenResidence.setPreferences("blah");
		oltenResidence.setUser(thomyF);
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

		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");

		// ad two possible visiting times ("visits") to the ad
		Visit visit = new Visit();
		visit.setAd(oltenResidence);
		visit.setStartTimestamp(formatter.parse("16.12.2014 10:00"));
		visit.setEndTimestamp(formatter.parse("16.12.2014 12:00"));
		visitDao.save(visit);

		Visit visit2 = new Visit();
		visit2.setAd(oltenResidence);
		visit2.setStartTimestamp(formatter.parse("18.12.2014 10:00"));
		visit2.setEndTimestamp(formatter.parse("18.12.2014 12:00"));
		visitDao.save(visit2);

		Iterable<Visit> oltenVisits = visitService.getVisitsByAd(oltenResidence);
		ArrayList<Visit> oltenList = new ArrayList<Visit>();
		for (Visit oltenvis : oltenVisits)
			oltenList.add(oltenvis);

		assertEquals(2, oltenList.size());
	}

	@Test
	public void enquireAndAccept() throws Exception {
		// create two users
		User adolfOgi = createUser("adolf@ogi.ch", "password", "Adolf", "Ogi", Gender.MALE, "Premium");
		adolfOgi.setAboutMe("Wallis rocks");
		userDao.save(adolfOgi);

		User blocher = createUser("christoph@blocher.eu", "svp", "Christoph", "Blocher", Gender.MALE, "Normal");
		blocher.setAboutMe("I own you");
		userDao.save(blocher);

		// save an ad
		Date date = new Date();
		Ad oltenResidence = new Ad();
		oltenResidence.setZipcode(4600);
		oltenResidence.setMoveInDate(date);
		oltenResidence.setCreationDate(date);
		oltenResidence.setPrize(1200);
		oltenResidence.setSquareFootage(42);
		oltenResidence.setRoomType("Studio");
		oltenResidence.setSmokers(true);
		oltenResidence.setAnimals(false);
		oltenResidence.setRoomDescription("blah");
		oltenResidence.setPreferences("blah");
		oltenResidence.setUser(adolfOgi);
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

		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");

		// ad two possible visiting times ("visits") to the ad
		Visit visit = new Visit();
		visit.setAd(oltenResidence);
		visit.setStartTimestamp(formatter.parse("16.12.2014 10:00"));
		visit.setEndTimestamp(formatter.parse("16.12.2014 12:00"));
		visitDao.save(visit);

		Visit visit2 = new Visit();
		visit2.setAd(oltenResidence);
		visit2.setStartTimestamp(formatter.parse("18.12.2014 10:00"));
		visit2.setEndTimestamp(formatter.parse("18.12.2014 12:00"));
		visitDao.save(visit2);

		// Ogi is enquiring about Blocher's apartment
		VisitEnquiry enquiry = new VisitEnquiry();
		enquiry.setVisit(visit);
		enquiry.setSender(adolfOgi);
		enquiry.setState(VisitEnquiryState.OPEN);
		visitEnquiryDao.save(enquiry);

		Iterable<VisitEnquiry> ogiEnquiries = visitEnquiryDao.findBySender(adolfOgi);
		ArrayList<VisitEnquiry> ogiEnquiryList = new ArrayList<VisitEnquiry>();
		for (VisitEnquiry venq : ogiEnquiries)
			ogiEnquiryList.add(venq);

		long venqID = ogiEnquiryList.get(0).getId();

		enquiryService.acceptEnquiry(venqID);

		assertEquals(VisitEnquiryState.ACCEPTED, visitEnquiryDao.findOne(venqID).getState());

		enquiryService.reopenEnquiry(venqID);

		assertEquals(VisitEnquiryState.OPEN, visitEnquiryDao.findOne(venqID).getState());

		enquiryService.declineEnquiry(venqID);

		assertEquals(VisitEnquiryState.DECLINED, visitEnquiryDao.findOne(venqID).getState());

	}

	@Test
	public void testEnquiryList() throws Exception {
		// create two users
		User testPersonEnquiryService1 = createUser("testPerson@EnquiryTest1.ch", "password", "Test Person",
				"EnquiryTest1", Gender.MALE, "Premium");
		testPersonEnquiryService1.setAboutMe("Test Person EnquiryTest1");
		userDao.save(testPersonEnquiryService1);

		User testPersonEnquiryService2 = createUser("testPerson@EnquiryTest2.ch", "password", "Test Person",
				"EnquiryTest2", Gender.MALE, "Premium");
		testPersonEnquiryService2.setAboutMe("Test Person EnquiryTest2");
		userDao.save(testPersonEnquiryService2);

		// save an ad
		Date date = new Date();
		Ad oltenResidence = new Ad();
		oltenResidence.setZipcode(4600);
		oltenResidence.setMoveInDate(date);
		oltenResidence.setCreationDate(date);
		oltenResidence.setPrize(1200);
		oltenResidence.setSquareFootage(42);
		oltenResidence.setRoomType("Studio");
		oltenResidence.setSmokers(true);
		oltenResidence.setAnimals(false);
		oltenResidence.setRoomDescription("blah");
		oltenResidence.setPreferences("blah");
		oltenResidence.setUser(testPersonEnquiryService1);
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

		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");

		Visit visit = new Visit();
		visit.setAd(oltenResidence);
		visit.setStartTimestamp(formatter.parse("16.12.2014 10:00"));
		visit.setEndTimestamp(formatter.parse("16.12.2014 12:00"));
		visitDao.save(visit);

		Auction auction = new Auction();
		auction.setCity("3000 - Bern");
		auction.setPreferences("Test preferences");
		auction.setRoomDescription("Test Room description");
		auction.setCreationDate(new Date());
		auction.setPrize(600);
		auction.setSquareFootage(50);
		auction.setTitle("title");
		auction.setStreet("Hauptstrasse 13");
		auction.setRoomType("Studio");
		auction.setMoveInDate(new Date());
		auction.setEndTime("12:00");
		auction.setSmokers(true);
		auction.setAnimals(true);
		auction.setGarden(true);
		auction.setBalcony(true);
		auction.setCellar(true);
		auction.setFurnished(true);
		auction.setCable(true);
		auction.setGarage(true);
		auction.setInternet(false);
		auction.setUser(testPersonEnquiryService1);
		auction.setAuction(true);
		auctionDao.save(auction);

		Visit visit2 = new Visit();
		visit2.setAuction(auction);
		visit2.setStartTimestamp(formatter.parse("16.12.2014 10:00"));
		visit2.setEndTimestamp(formatter.parse("16.12.2014 12:00"));
		visitDao.save(visit2);

		VisitEnquiry enquiry = new VisitEnquiry();
		enquiry.setVisit(visit);
		enquiry.setDateSent(new Date());
		enquiry.setSender(testPersonEnquiryService2);
		enquiry.setState(VisitEnquiryState.OPEN);
		enquiryService.saveVisitEnquiry(enquiry);

		VisitEnquiry enquiry2 = new VisitEnquiry();
		enquiry2.setVisit(visit2);
		enquiry2.setDateSent(new Date());
		enquiry2.setSender(testPersonEnquiryService2);
		enquiry2.setState(VisitEnquiryState.ACCEPTED);
		enquiryService.saveVisitEnquiry(enquiry2);

		Iterable<VisitEnquiry> enquiries = enquiryService.getEnquiriesByRecipient(testPersonEnquiryService1);
		ArrayList<VisitEnquiry> enquiryList = new ArrayList<VisitEnquiry>();
		for (VisitEnquiry tempEnquiry : enquiries) {
			enquiryList.add(tempEnquiry);
		}

		assertEquals(testPersonEnquiryService2.getUsername(), enquiryList.get(0).getSender().getUsername());
		assertEquals(testPersonEnquiryService2.getUsername(), enquiryList.get(0).getSender().getUsername());
		assertFalse(enquiryList.get(0).getVisit().getAd().getAuction());
		assertTrue(enquiryList.get(1).getVisit().getAuction().getAuction());
		
		Iterable<Visit> visitsForSender = visitService.getVisitsForUser(userDao.findByUsername("testPerson@EnquiryTest2.ch"));
		Iterator<Visit> iterator = visitsForSender.iterator();
		List<Visit> visits = new ArrayList<Visit>();
		while(iterator.hasNext()) {
			visits.add(iterator.next());
		}
		
		assertEquals(1, visits.size());
		
		//test delete the visit of the last added auction
		Iterable<Auction> auctionIterable = auctionDao.findAll();
		Iterator<Auction> auctions = auctionIterable.iterator();
		Auction auc = new Auction();
		while (auctions.hasNext()) {
			auc = auctions.next();
		}
		
		visitService.delete(auc);
		
		visitsForSender = visitService.getVisitsForUser(userDao.findByUsername("testPerson@EnquiryTest2.ch"));
		iterator = visitsForSender.iterator();
		List<Visit> visits2 = new ArrayList<Visit>();
		while(iterator.hasNext()) {
			visits2.add(iterator.next());
		}
		
		assertEquals(0, visits2.size());
	}

	@Test
	public void testRating() {		
		User testPersonEnquiryService3 = createUser("testPerson@EnquiryTest3.ch", "password", "Test Person",
				"EnquiryTest3", Gender.MALE, "Premium");
		testPersonEnquiryService3.setAboutMe("Test Person EnquiryTest3");
		userDao.save(testPersonEnquiryService3);

		User testPersonEnquiryService4 = createUser("testPerson@EnquiryTest4.ch", "password", "Test Person",
				"EnquiryTest4", Gender.MALE, "Premium");
		testPersonEnquiryService4.setAboutMe("Test Person EnquiryTest4");
		userDao.save(testPersonEnquiryService4);
		
		Rating rating = new Rating();
		rating.setRatee(testPersonEnquiryService4);
		rating.setRater(testPersonEnquiryService3);
		rating.setRating(0);
		ratingDao.save(rating);

		enquiryService.rate(testPersonEnquiryService3, testPersonEnquiryService4, 5);

		Iterable<Rating> ratingOfRater = enquiryService.getRatingsByRater(testPersonEnquiryService3);
		ArrayList<Rating> ratingsOfRater = new ArrayList<Rating>();
		for (Rating tempRating : ratingOfRater) {
			ratingsOfRater.add(tempRating);
		}
		
		assertEquals(5, ratingsOfRater.get(0).getRating());
		assertEquals(testPersonEnquiryService3.getUsername(), ratingsOfRater.get(0).getRater().getUsername());
		
		rating = enquiryService.getRatingByRaterAndRatee(testPersonEnquiryService3, testPersonEnquiryService4);
		assertEquals(5, rating.getRating());
	}

	// Lean user creating method
	User createUser(String email, String password, String firstName, String lastName, Gender gender, String account) {
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

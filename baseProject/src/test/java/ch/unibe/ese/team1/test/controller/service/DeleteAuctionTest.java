package ch.unibe.ese.team1.test.controller.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
import ch.unibe.ese.team1.controller.service.EnquiryService;
import ch.unibe.ese.team1.controller.service.VisitService;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.Gender;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.UserRole;
import ch.unibe.ese.team1.model.VisitEnquiry;
import ch.unibe.ese.team1.model.VisitEnquiryState;
import ch.unibe.ese.team1.model.dao.AuctionDao;
import ch.unibe.ese.team1.model.dao.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"file:src/main/webapp/WEB-INF/config/springMVC.xml",
		"file:src/main/webapp/WEB-INF/config/springData.xml",
		"file:src/main/webapp/WEB-INF/config/springSecurity.xml" })
@WebAppConfiguration
public class DeleteAuctionTest {

	@Autowired
	private AuctionService auctionService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private AuctionDao auctionDao;
	@Autowired
	private EnquiryService enquiryService;
	@Autowired
	private VisitService visitService;
	
	private PlaceAuctionForm placeAuctionForm = new PlaceAuctionForm();
	private ArrayList<String> filePaths = new ArrayList<String>();
	
	@Before
	public void setUp() {
		placeAuctionForm.setCity("3000 - Bern");
		placeAuctionForm.setPreferences("Test preferences");
		placeAuctionForm.setRoomDescription("Test Room description");
		placeAuctionForm.setPrize(600);
		placeAuctionForm.setBalcony(false);
		placeAuctionForm.setSquareFootage(50);
		placeAuctionForm.setTitle("title");
		placeAuctionForm.setStreet("Hauptstrasse 13");
		placeAuctionForm.setRoomType("Studio");
		placeAuctionForm.setMoveInDate("27-02-2015");
		placeAuctionForm.setEndDate("27-04-2015");
		placeAuctionForm.setEndTime("12:12");

		placeAuctionForm.setSmokers(true);
		placeAuctionForm.setAnimals(true);
		placeAuctionForm.setGarden(true);
		placeAuctionForm.setBalcony(true);
		placeAuctionForm.setCellar(true);
		placeAuctionForm.setFurnished(true);
		placeAuctionForm.setCable(true);
		placeAuctionForm.setGarage(true);
		placeAuctionForm.setInternet(false);
		
		filePaths.add("/img/test/ad1_1.jpg");
	}
	
	@Test
	public void deleteAdWithoutVisits() {
		Iterable<Auction> allAdsBefore = auctionService.getAllAds();
		ArrayList<Auction> allAdsListBefore = new ArrayList<Auction>();
		for (Auction tempAd: allAdsBefore) {
			allAdsListBefore.add(tempAd);
		}
		
		int numberOfAds = allAdsListBefore.size();
		
		User deleteAdTest = createUser("deleteAuction@Test1.ch", "password", "deleteAuction", "Test1", Gender.MALE, "Normal");
		deleteAdTest.setAboutMe("deleteAuctionTest1");
		userDao.save(deleteAdTest);

		auctionService.saveFrom(placeAuctionForm, filePaths, deleteAdTest);
		
		Auction ad = new Auction();
		Iterable<Auction> ads = auctionService.getAllAds();
		Iterator<Auction> iterator = ads.iterator();

		while (iterator.hasNext()) {
			ad = iterator.next();
		}
		
		long adId = ad.getId();
		
		Iterable<Auction> allAds = auctionService.getAllAds();
		ArrayList<Auction> allAdsList = new ArrayList<Auction>();
		for (Auction tempAd: allAds) {
			allAdsList.add(tempAd);
		}
		
		int numberOfAdsBefore = allAdsList.size();
		
		assertEquals(numberOfAds, numberOfAdsBefore-1);
		
		auctionDao.delete(adId);
		
		allAds = auctionService.getAllAds();
		ArrayList<Auction> allAdsListAfter = new ArrayList<Auction>();
		for (Auction tempAd: allAds) {
			if(tempAd != null) 
			allAdsListAfter.add(tempAd);
		}
		
		int numberOfAdsAfter = allAdsListAfter.size();
		
		assertEquals(numberOfAdsBefore, numberOfAdsAfter+1);
	}
	
	@Test
	public void deleteAdsWithVisits() {
		Iterable<Auction> allAdsBefore = auctionService.getAllAds();
		ArrayList<Auction> allAdsListBefore = new ArrayList<Auction>();
		for (Auction tempAd: allAdsBefore) {
			allAdsListBefore.add(tempAd);
		}
		
		int numberOfAds = allAdsListBefore.size();
		
		List<String> visits = new ArrayList<String>();
		visits.add("28-02-2014;10:02;13:14");
		placeAuctionForm.setVisits(visits);
		
		User deleteAdTest2 = createUser("deleteAuction@Test2.ch", "password", "deleteAuction", "Test2", Gender.MALE, "Normal");
		deleteAdTest2.setAboutMe("deleteAuctionTest2");
		userDao.save(deleteAdTest2);

		auctionService.saveFrom(placeAuctionForm, filePaths, deleteAdTest2);
		
		Auction ad = new Auction();
		Iterable<Auction> ads = auctionService.getAllAds();
		Iterator<Auction> iterator = ads.iterator();

		while (iterator.hasNext()) {
			ad = iterator.next();
		}
		
		long adId = ad.getId();
		
		Iterable<Auction> allAds = auctionService.getAllAds();
		ArrayList<Auction> allAdsList = new ArrayList<Auction>();
		for (Auction tempAd: allAds) {
			allAdsList.add(tempAd);
		}
		
		int numberOfAdsBefore = allAdsList.size();
		
		assertEquals(numberOfAds, numberOfAdsBefore-1);
		
		auctionDao.delete(adId);
		
		allAds = auctionService.getAllAds();
		ArrayList<Auction> allAdsListAfter = new ArrayList<Auction>();
		for (Auction tempAd: allAds) {
			if(tempAd != null) 
			allAdsListAfter.add(tempAd);
		}
		
		int numberOfAdsAfter = allAdsListAfter.size();
		
		assertEquals(numberOfAdsBefore, numberOfAdsAfter+1);
		
	}
	
	@Test
	public void deleteAdWithEnquiry() {
		Iterable<Auction> allAdsBefore = auctionService.getAllAds();
		ArrayList<Auction> allAdsListBefore = new ArrayList<Auction>();
		for (Auction tempAd: allAdsBefore) {
			allAdsListBefore.add(tempAd);
		}
		
		int numberOfAds = allAdsListBefore.size();
		
		List<String> visits = new ArrayList<String>();
		visits.add("28-02-2014;10:02;13:14");
		placeAuctionForm.setVisits(visits);
		
		User deleteAdTest3 = createUser("deleteAuction@Test3.ch", "password", "deleteAuction", "Test3", Gender.MALE, "Normal");
		deleteAdTest3.setAboutMe("deleteAdTest3");
		userDao.save(deleteAdTest3);
		User deleteAdTest4 = createUser("deleteAuction@Test4.ch", "password", "deleteAuction", "Test4", Gender.MALE, "Normal");
		deleteAdTest4.setAboutMe("deleteAdTest4");
		userDao.save(deleteAdTest4);

		auctionService.saveFrom(placeAuctionForm, filePaths, deleteAdTest3);
		
		Auction ad = new Auction();
		Iterable<Auction> ads = auctionService.getAllAds();
		Iterator<Auction> iterator = ads.iterator();

		while (iterator.hasNext()) {
			ad = iterator.next();
		}
		
		long adId = ad.getId();
		long visitId = ad.getVisits().get(0).getId();
		
		VisitEnquiry visitEnquiry = new VisitEnquiry();
		visitEnquiry.setDateSent(new Date());
		visitEnquiry.setSender(deleteAdTest4);
		visitEnquiry.setState(VisitEnquiryState.DECLINED);
		visitEnquiry.setVisit(visitService.getVisitById(visitId));
		
		enquiryService.saveVisitEnquiry(visitEnquiry);
		
		Iterable<Auction> allAds = auctionService.getAllAds();
		ArrayList<Auction> allAdsList = new ArrayList<Auction>();
		for (Auction tempAd: allAds) {
			allAdsList.add(tempAd);
		}
		
		int numberOfAdsBefore = allAdsList.size();
		
		assertEquals(numberOfAds, numberOfAdsBefore-1);
		
		auctionService.delete(adId);
		
		allAds = auctionService.getAllAds();
		ArrayList<Auction> allAdsListAfter = new ArrayList<Auction>();
		for (Auction tempAd: allAds) {
			if(tempAd != null) 
			allAdsListAfter.add(tempAd);
		}
		
		int numberOfAdsAfter = allAdsListAfter.size();
		
		assertEquals(numberOfAdsBefore, numberOfAdsAfter+1);
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

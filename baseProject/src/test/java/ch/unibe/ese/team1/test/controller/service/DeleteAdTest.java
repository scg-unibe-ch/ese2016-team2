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

import ch.unibe.ese.team1.controller.pojos.forms.PlaceAdForm;
import ch.unibe.ese.team1.controller.service.AdService;
import ch.unibe.ese.team1.controller.service.EnquiryService;
import ch.unibe.ese.team1.controller.service.VisitService;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Gender;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.UserRole;
import ch.unibe.ese.team1.model.VisitEnquiry;
import ch.unibe.ese.team1.model.VisitEnquiryState;
import ch.unibe.ese.team1.model.dao.AdDao;
import ch.unibe.ese.team1.model.dao.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"file:src/main/webapp/WEB-INF/config/springMVC.xml",
		"file:src/main/webapp/WEB-INF/config/springData.xml",
		"file:src/main/webapp/WEB-INF/config/springSecurity.xml" })
@WebAppConfiguration
public class DeleteAdTest {
	
	@Autowired
	private AdService adService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private AdDao adDao;
	@Autowired
	private EnquiryService enquiryService;
	@Autowired
	private VisitService visitService;
	
	private PlaceAdForm placeAdForm = new PlaceAdForm();
	private ArrayList<String> filePaths = new ArrayList<String>();
	
	@Before
	public void setUp() {
		placeAdForm.setCity("3000 - Bern");
		placeAdForm.setPreferences("Test preferences");
		placeAdForm.setRoomDescription("Test Room description");
		placeAdForm.setPrize(600);
		placeAdForm.setBalcony(false);
		placeAdForm.setSquareFootage(50);
		placeAdForm.setTitle("title");
		placeAdForm.setStreet("Hauptstrasse 13");
		placeAdForm.setRoomType("Studio");
		placeAdForm.setMoveInDate("27-02-2015");
		placeAdForm.setMoveOutDate("27-04-2015");

		placeAdForm.setSmokers(true);
		placeAdForm.setAnimals(true);
		placeAdForm.setGarden(true);
		placeAdForm.setBalcony(true);
		placeAdForm.setCellar(true);
		placeAdForm.setFurnished(true);
		placeAdForm.setCable(true);
		placeAdForm.setGarage(true);
		placeAdForm.setInternet(false);
		
		filePaths.add("/img/test/ad1_1.jpg");
	}
	
	@Test
	public void deleteAdWithoutVisits() {
		Iterable<Ad> allAdsBefore = adService.getAllAds();
		ArrayList<Ad> allAdsListBefore = new ArrayList<Ad>();
		for (Ad tempAd: allAdsBefore) {
			allAdsListBefore.add(tempAd);
		}
		
		int numberOfAds = allAdsListBefore.size();
		
		User deleteAdTest = createUser("deleteAd@Test1.ch", "password", "deleteAd", "Test1", Gender.MALE, "Normal");
		deleteAdTest.setAboutMe("deleteAdTest1");
		userDao.save(deleteAdTest);

		adService.saveFrom(placeAdForm, filePaths, deleteAdTest);
		
		Ad ad = new Ad();
		Iterable<Ad> ads = adService.getAllAds();
		Iterator<Ad> iterator = ads.iterator();

		while (iterator.hasNext()) {
			ad = iterator.next();
		}
		
		long adId = ad.getId();
		
		Iterable<Ad> allAds = adService.getAllAds();
		ArrayList<Ad> allAdsList = new ArrayList<Ad>();
		for (Ad tempAd: allAds) {
			allAdsList.add(tempAd);
		}
		
		int numberOfAdsBefore = allAdsList.size();
		
		assertEquals(numberOfAds, numberOfAdsBefore-1);
		
		adDao.delete(adId);
		
		allAds = adService.getAllAds();
		ArrayList<Ad> allAdsListAfter = new ArrayList<Ad>();
		for (Ad tempAd: allAds) {
			if(tempAd != null) 
			allAdsListAfter.add(tempAd);
		}
		
		int numberOfAdsAfter = allAdsListAfter.size();
		
		assertEquals(numberOfAdsBefore, numberOfAdsAfter+1);
	}
	
	@Test
	public void deleteAdsWithVisits() {
		Iterable<Ad> allAdsBefore = adService.getAllAds();
		ArrayList<Ad> allAdsListBefore = new ArrayList<Ad>();
		for (Ad tempAd: allAdsBefore) {
			allAdsListBefore.add(tempAd);
		}
		
		int numberOfAds = allAdsListBefore.size();
		
		List<String> visits = new ArrayList<String>();
		visits.add("28-02-2014;10:02;13:14");
		placeAdForm.setVisits(visits);
		
		User deleteAdTest2 = createUser("deleteAd@Test2.ch", "password", "deleteAd", "Test2", Gender.MALE, "Normal");
		deleteAdTest2.setAboutMe("deleteAdTest2");
		userDao.save(deleteAdTest2);

		adService.saveFrom(placeAdForm, filePaths, deleteAdTest2);
		
		Ad ad = new Ad();
		Iterable<Ad> ads = adService.getAllAds();
		Iterator<Ad> iterator = ads.iterator();

		while (iterator.hasNext()) {
			ad = iterator.next();
		}
		
		long adId = ad.getId();
		
		Iterable<Ad> allAds = adService.getAllAds();
		ArrayList<Ad> allAdsList = new ArrayList<Ad>();
		for (Ad tempAd: allAds) {
			allAdsList.add(tempAd);
		}
		
		int numberOfAdsBefore = allAdsList.size();
		
		assertEquals(numberOfAds, numberOfAdsBefore-1);
		
		adDao.delete(adId);
		
		allAds = adService.getAllAds();
		ArrayList<Ad> allAdsListAfter = new ArrayList<Ad>();
		for (Ad tempAd: allAds) {
			if(tempAd != null) 
			allAdsListAfter.add(tempAd);
		}
		
		int numberOfAdsAfter = allAdsListAfter.size();
		
		assertEquals(numberOfAdsBefore, numberOfAdsAfter+1);
		
	}
	
	@Test
	public void deleteAdWithEnquiry() {
		Iterable<Ad> allAdsBefore = adService.getAllAds();
		ArrayList<Ad> allAdsListBefore = new ArrayList<Ad>();
		for (Ad tempAd: allAdsBefore) {
			allAdsListBefore.add(tempAd);
		}
		
		int numberOfAds = allAdsListBefore.size();
		
		List<String> visits = new ArrayList<String>();
		visits.add("28-02-2014;10:02;13:14");
		placeAdForm.setVisits(visits);
		
		User deleteAdTest3 = createUser("deleteAd@Test3.ch", "password", "deleteAd", "Test3", Gender.MALE, "Normal");
		deleteAdTest3.setAboutMe("deleteAdTest3");
		userDao.save(deleteAdTest3);
		User deleteAdTest4 = createUser("deleteAd@Test4.ch", "password", "deleteAd", "Test4", Gender.MALE, "Normal");
		deleteAdTest4.setAboutMe("deleteAdTest4");
		userDao.save(deleteAdTest4);

		adService.saveFrom(placeAdForm, filePaths, deleteAdTest3);
		
		Ad ad = new Ad();
		Iterable<Ad> ads = adService.getAllAds();
		Iterator<Ad> iterator = ads.iterator();

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
		
		Iterable<Ad> allAds = adService.getAllAds();
		ArrayList<Ad> allAdsList = new ArrayList<Ad>();
		for (Ad tempAd: allAds) {
			allAdsList.add(tempAd);
		}
		
		int numberOfAdsBefore = allAdsList.size();
		
		assertEquals(numberOfAds, numberOfAdsBefore-1);
		
		adService.delete(adId);
		
		allAds = adService.getAllAds();
		ArrayList<Ad> allAdsListAfter = new ArrayList<Ad>();
		for (Ad tempAd: allAds) {
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

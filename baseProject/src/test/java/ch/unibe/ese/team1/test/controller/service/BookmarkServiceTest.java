package ch.unibe.ese.team1.test.controller.service;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
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
import ch.unibe.ese.team1.controller.service.BookmarkService;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Auction;
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
public class BookmarkServiceTest {

	@Autowired
	private BookmarkService bookmarkService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AdService adService;
	
	@Autowired
	private AuctionService auctionService;
	
	private ArrayList<String> filePaths = new ArrayList<>();
	private LinkedList<Ad> bookmarkedAds = new LinkedList<>();
	private LinkedList<Auction> bookmarkedAuctions = new LinkedList<>();
	private User user = new User();
	private Ad ad = new Ad();
	private Auction auction = new Auction();
	private Boolean bookmarked = null;

	@Test
	public void checkBookmarkStatus() throws ParseException {
		//Preparation
		PlaceAdForm placeAdForm = new PlaceAdForm();
		placeAdForm.setCity("3018 - Bern");
		placeAdForm.setPreferences("Test preferences");
		placeAdForm.setRoomDescription("Test Room description");
		placeAdForm.setPrize(600);
		placeAdForm.setSquareFootage(50);
		placeAdForm.setTitle("title");
		placeAdForm.setStreet("Hauptstrasse 13");
		placeAdForm.setRoomType("Studio");
		placeAdForm.setMoveInDate("27-02-2015");
		placeAdForm.setMoveOutDate("27-04-2015");
				
		placeAdForm.setSmokers(true);
		placeAdForm.setAnimals(false);
		placeAdForm.setGarden(true);
		placeAdForm.setBalcony(false);
		placeAdForm.setCellar(true);
		placeAdForm.setFurnished(false);
		placeAdForm.setCable(false);
		placeAdForm.setGarage(true);
		placeAdForm.setInternet(false);
				
		filePaths.add("/img/test/ad1_1.jpg");
				
		user = createUser("hans@muster.ch", "password", "Hans", "Muser",
				Gender.MALE, "Premium");
		user.setAboutMe("Hans Muster");
		userDao.save(user);
				
		adService.saveFrom(placeAdForm, filePaths, user);
		Iterable<Ad> ads = adService.getAllAds();
		Iterator<Ad> iterator = ads.iterator();
		
		while (iterator.hasNext()) {
			ad = iterator.next();
		}
	
		bookmarkedAds.add(ad);	
		user.setBookmarkedAds(bookmarkedAds);
		userDao.save(user);

		bookmarked = true;
		assertEquals(2, bookmarkService.getBookmarkStatus(ad, bookmarked, user));
		
		bookmarked = false;
		assertEquals(3, bookmarkService.getBookmarkStatus(ad, bookmarked, user));
		
		bookmarkService.deleteAd(ad);
		User user2 = userDao.findByUsername("hans@muster.ch");
		
		assertEquals(0, user2.getBookmarkedAuctions().size());
	}
	
	@Test
	public void checkBookmarkStatusAuction() throws ParseException {
		//Preparation
		PlaceAuctionForm placeAdForm = new PlaceAuctionForm();
		placeAdForm.setCity("3018 - Bern");
		placeAdForm.setPreferences("Test preferences");
		placeAdForm.setRoomDescription("Test Room description");
		placeAdForm.setPrize(600);
		placeAdForm.setSquareFootage(50);
		placeAdForm.setTitle("title");
		placeAdForm.setStreet("Hauptstrasse 13");
		placeAdForm.setRoomType("Studio");
		placeAdForm.setMoveInDate("27-02-2015");
		placeAdForm.setEndDate("12.12.2017");
		placeAdForm.setEndTime("12:12");
				
		placeAdForm.setSmokers(true);
		placeAdForm.setAnimals(false);
		placeAdForm.setGarden(true);
		placeAdForm.setBalcony(false);
		placeAdForm.setCellar(true);
		placeAdForm.setFurnished(false);
		placeAdForm.setCable(false);
		placeAdForm.setGarage(true);
		placeAdForm.setInternet(false);
				
		filePaths.add("/img/test/ad1_1.jpg");
				
		user = createUser("fritz@muster.ch", "password", "Fritz", "Muser",
				Gender.MALE, "Premium");
		user.setAboutMe("Fritz Muster");
		userDao.save(user);
				
		auctionService.saveFrom(placeAdForm, filePaths, user);
		Iterable<Auction> auctions = auctionService.getAllAds();
		Iterator<Auction> iterator = auctions.iterator();
		
		while (iterator.hasNext()) {
			auction = iterator.next();
		}
	
		bookmarkedAuctions.add(auction);	
		user.setBookmarkedAuctions(bookmarkedAuctions);
		userDao.save(user);

		bookmarked = true;
		assertEquals(2, bookmarkService.getBookmarkStatusAuction(auction, bookmarked, user));
		
		bookmarked = false;
		assertEquals(3, bookmarkService.getBookmarkStatusAuction(auction, bookmarked, user));
		
		bookmarkService.deleteAuction(auction);
		User user2 = userDao.findByUsername("fritz@muster.ch");
		
		assertEquals(0, user2.getBookmarkedAuctions().size());
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

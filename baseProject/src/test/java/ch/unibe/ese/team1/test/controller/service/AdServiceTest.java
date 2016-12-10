package ch.unibe.ese.team1.test.controller.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import ch.unibe.ese.team1.controller.pojos.forms.SearchForm;
import ch.unibe.ese.team1.controller.service.AdService;
import ch.unibe.ese.team1.controller.service.EditAdService;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.AdPicture;
import ch.unibe.ese.team1.model.Advertisement;
import ch.unibe.ese.team1.model.Gender;
import ch.unibe.ese.team1.model.Picture;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.UserRole;
import ch.unibe.ese.team1.model.dao.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"file:src/main/webapp/WEB-INF/config/springMVC.xml",
		"file:src/main/webapp/WEB-INF/config/springData.xml",
		"file:src/main/webapp/WEB-INF/config/springSecurity.xml" })
@WebAppConfiguration
public class AdServiceTest {

	@Autowired
	private AdService adService;
	
	@Autowired
	private EditAdService editAdService;

	@Autowired
	private UserDao userDao;

	private PlaceAdForm placeAdForm = new PlaceAdForm();
	private ArrayList<String> filePaths = new ArrayList<String>();
	private ArrayList<Advertisement> results = new ArrayList<Advertisement>();
	private SearchForm searchForm = new SearchForm();

	@Before
	public void setUp() {
		// Preparation
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

		searchForm.setAnimals(false);
		searchForm.setBuyable(false);
		searchForm.setBalcony(false);
		searchForm.setCable(false);
		searchForm.setCellar(false);
		searchForm.setCity("3000 - Bern");
		searchForm.setFurnished(false);
		searchForm.setGarage(false);
		searchForm.setGarden(false);
		searchForm.setHouse(true);
		searchForm.setInternet(false);
		searchForm.setPrize(1000);
		searchForm.setRadius(10);
		searchForm.setRoom(true);
		searchForm.setSmokers(false);
		searchForm.setStudio(true);
	}

	/**
	 * In order to test the saved ad, I need to get it back from the DB again,
	 * so these two methods need to be tested together, normally we want to test
	 * things isolated of course. Testing just the returned ad from saveFrom()
	 * wouldn't answer the question whether the ad has been saved correctly to
	 * the db.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void saveFromAndGetById() throws ParseException {
		User hans = createUser("adService@Test1.ch", "password", "AdService", "Test1", Gender.MALE, "Normal");
		hans.setAboutMe("AdServiceTest1");
		userDao.save(hans);

		adService.saveFrom(placeAdForm, filePaths, hans);
		
		Ad ad = new Ad();
		Iterable<Ad> ads = adService.getAllAds();
		Iterator<Ad> iterator = ads.iterator();

		while (iterator.hasNext()) {
			ad = iterator.next();
		}

		// Testing
		assertTrue(ad.getSmokers());
		assertEquals("Bern", ad.getCity());
		assertEquals(3000, ad.getZipcode());
		assertEquals("Test preferences", ad.getPreferences());
		assertEquals("Test Room description", ad.getRoomDescription());
		assertEquals(600, ad.getPrize());
		assertEquals(50, ad.getSquareFootage());
		assertEquals("title", ad.getTitle());
		assertEquals("Hauptstrasse 13", ad.getStreet());
		assertEquals("Studio", ad.getRoomType());

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date result = df.parse("2015-02-27");

		assertEquals(0, result.compareTo(ad.getMoveInDate()));
	}

	@Test
	public void testQueryResults() {
		User hans = createUser("adService@Test2.ch", "password", "AdService", "Test2", Gender.MALE, "Normal");
		hans.setAboutMe("AdServiceTest2");
		userDao.save(hans);

		adService.saveFrom(placeAdForm, filePaths, hans);
		
		Ad ad2 = new Ad();
		Iterable<Ad> ads = adService.getAllAds();
		Iterator<Ad> iterator = ads.iterator();

		while (iterator.hasNext()) {
			ad2 = iterator.next();
		}

		results = toList(searchForm, false);
		assertTrue(results.contains(ad2));

		results = toList(searchForm, true);
		assertFalse(results.contains(ad2));
		
		searchForm.setBuyable(true);
		results = toList(searchForm, true);
		assertFalse(results.contains(ad2));

		searchForm.setBuyable(false);
		searchForm.setCity("3315 - Bätterkinden");
		results = toList(searchForm, false);
		assertFalse(results.contains(ad2));

		searchForm.setAnimals(true);
		searchForm.setBalcony(true);
		searchForm.setCable(true);
		searchForm.setCellar(true);
		searchForm.setCity("3000 - Bern");
		searchForm.setFurnished(true);
		searchForm.setGarage(true);
		searchForm.setGarden(true);
		searchForm.setSmokers(true);
		results = toList(searchForm, false);
		assertTrue(results.contains(ad2));

		searchForm.setInternet(true);
		results = toList(searchForm, false);
		assertFalse(results.contains(ad2));

		searchForm.setInternet(false);
		searchForm.setRoom(false);
		results = toList(searchForm, false);
		assertTrue(results.contains(ad2));

		searchForm.setRoom(true);
		searchForm.setHouse(false);
		results = toList(searchForm, false);
		assertTrue(results.contains(ad2));

		searchForm.setRoom(false);
		results = toList(searchForm, false);
		assertTrue(results.contains(ad2));

		searchForm.setRoom(true);
		searchForm.setHouse(true);
		searchForm.setStudio(false);
		results = toList(searchForm, false);
		assertFalse(results.contains(ad2));

		searchForm.setRoom(false);
		results = toList(searchForm, false);
		assertFalse(results.contains(ad2));

		searchForm.setRoom(true);
		searchForm.setHouse(false);
		results = toList(searchForm, false);
		assertFalse(results.contains(ad2));
	}
	
	@Test
	public void testEditAd() {
		User hans = createUser("adService@Test3.ch", "password", "AdService", "Test3", Gender.MALE, "Normal");
		hans.setAboutMe("AdServiceTest3");
		userDao.save(hans);

		adService.saveFrom(placeAdForm, filePaths, hans);
		
		Ad ad2 = new Ad();
		Iterable<Ad> ads = adService.getAllAds();
		Iterator<Ad> iterator = ads.iterator();

		while (iterator.hasNext()) {
			ad2 = iterator.next();
		}
		
		List<AdPicture> pics = ad2.getPictures();
		assertEquals(1, pics.size());
		
		editAdService.deletePictureFromAd(ad2.getId(), pics.get(0).getId());
		
		ads = adService.getAllAds();
		iterator = ads.iterator();

		while (iterator.hasNext()) {
			ad2 = iterator.next();
		}
		assertEquals(0, ad2.getPictures().size());
		
		PlaceAdForm editAdForm = editAdService.fillForm(ad2);
		assertEquals("Test preferences", editAdForm.getPreferences());
		editAdForm.setCity("3000 - Bern");
		editAdForm.setPreferences("Test preferences");
		editAdForm.setRoomDescription("Test Room description");
		editAdForm.setPrize(600);
		editAdForm.setBalcony(false);
		editAdForm.setSquareFootage(50);
		editAdForm.setTitle("new title");
		editAdForm.setStreet("Hauptstrasse 13");
		editAdForm.setRoomType("Studio");
		editAdForm.setMoveInDate("27-02-2015");
		editAdForm.setMoveOutDate("27-04-2015");

		editAdForm.setSmokers(true);
		editAdForm.setAnimals(true);
		editAdForm.setGarden(true);
		editAdForm.setBalcony(true);
		editAdForm.setCellar(true);
		editAdForm.setFurnished(true);
		editAdForm.setCable(true);
		editAdForm.setGarage(true);
		editAdForm.setInternet(false);
		List<String> visits = new ArrayList<String>();
		visits.add("28-02-2014;10:02;13:14");
		visits.add("27-02-2014;10:02;13:14");
		editAdForm.setVisits(visits);
		
		editAdService.saveFrom(editAdForm, filePaths, hans, ad2.getId());
		
		ads = adService.getAllAds();
		iterator = ads.iterator();

		while (iterator.hasNext()) {
			ad2 = iterator.next();
		}
		
		assertEquals("new title", ad2.getTitle());
		
	}

	private ArrayList<Advertisement> toList(SearchForm searchForm, boolean premium) {
		ArrayList<Advertisement> temp = new ArrayList<Advertisement>();

		Iterable<Ad> ads = adService.queryResults(searchForm, premium);

		for (Ad ad : ads) {
			temp.add(ad);
		}

		return temp;
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

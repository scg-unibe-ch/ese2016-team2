package ch.unibe.ese.team1.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Advertisement;
import ch.unibe.ese.team1.model.User;

public class AdvertisementTest {

	private Advertisement ad = new Ad();
	
	@Test
	public void testId() {
		long id = 1;
		ad.setId(id);
		
		assertEquals(id, ad.getId());
	}
	
	@Test
	public void testTitle() {
		String title = "Test title";
		ad.setTitle(title);
		
		assertEquals(title, ad.getTitle());
	}
	
	@Test
	public void testStreet() {
		String street = "Test street";
		ad.setStreet(street);
		
		assertEquals(street, ad.getStreet());
	}
	
	@Test
	public void testZipcode() {
		int zipcode = 3000;
		ad.setZipcode(zipcode);
		
		assertEquals(zipcode, ad.getZipcode());
	}
	
	@Test 
	public void testCity() {
		String city = "Test city";
		ad.setCity(city);
		
		assertEquals(city, ad.getCity());
	}
	
	@Test
	public void testDates() {
		Date dateNow = new Date();
		ad.setCreationDate(dateNow);
		ad.setMoveInDate(dateNow);
		ad.setMoveOutDate(dateNow);
		
		assertEquals(dateNow, ad.getCreationDate());
		assertEquals(dateNow, ad.getMoveInDate());
		assertEquals(dateNow, ad.getMoveOutDate());
	}
	
	@Test
	public void testPrize() {
		int prize = 500;
		ad.setPrize(prize);
		
		assertEquals(prize, ad.getPrize());
	}
	
	@Test
	public void testSqaureFootage() {
		int squareFootage = 50;
		ad.setSquareFootage(squareFootage);
		
		assertEquals(squareFootage, ad.getSquareFootage());
	}
	
	@Test
	public void testRoomDescription() {
		String roomDescription = "Test Description";
		ad.setRoomDescription(roomDescription);
		
		assertEquals(roomDescription, ad.getRoomDescription());
	}
	
	@Test
	public void testPreferences() {
		String preferences = "Test preferences";
		ad.setPreferences(preferences);
		
		assertEquals(preferences, ad.getPreferences());
	}
	
	@Test
	public void testDescriptions() {
		boolean description = false;
		ad.setAnimals(description);
		ad.setBalcony(description);
		ad.setCable(description);
		ad.setCellar(description);
		ad.setFurnished(description);
		ad.setGarage(description);
		ad.setGarden(description);
		ad.setInternet(description);
		ad.setSmokers(description);
		
		assertEquals(description, ad.getAnimals());
		assertEquals(description, ad.getBalcony());
		assertEquals(description, ad.getCable());
		assertEquals(description, ad.getCellar());
		assertEquals(description, ad.getFurnished());
		assertEquals(description, ad.getGarage());
		assertEquals(description, ad.getGarden());
		assertEquals(description, ad.getInternet());
		assertEquals(description, ad.getSmokers());
		
		description = true;
		ad.setAnimals(description);
		ad.setBalcony(description);
		ad.setCable(description);
		ad.setCellar(description);
		ad.setFurnished(description);
		ad.setGarage(description);
		ad.setGarden(description);
		ad.setInternet(description);
		ad.setSmokers(description);
		
		assertEquals(description, ad.getAnimals());
		assertEquals(description, ad.getBalcony());
		assertEquals(description, ad.getCable());
		assertEquals(description, ad.getCellar());
		assertEquals(description, ad.getFurnished());
		assertEquals(description, ad.getGarage());
		assertEquals(description, ad.getGarden());
		assertEquals(description, ad.getInternet());
		assertEquals(description, ad.getSmokers());
	}
	
	@Test
	public void testRoomType() {
		String roomType = "Test type";
		ad.setRoomType(roomType);
		
		assertEquals(roomType, ad.getRoomType());
	}
	
	@Test
	public void testUser() {
		User user = new User();
		ad.setUser(user);
		
		assertEquals(user, ad.getUser());
	}
	
	@Test 
	public void testAuction() {
		boolean auction = false;
		ad.setAuction(auction);
		
		assertFalse(ad.getAuction());
		
		auction = true;
		ad.setAuction(auction);
		
		assertTrue(ad.getAuction());
	}

	@Test
	public void testBuyable() {
		boolean buyable = false;
		ad.setBuyable(buyable);
		
		assertFalse(ad.getBuyable());
		
		buyable = true;
		ad.setBuyable(buyable);
		
		assertTrue(ad.getBuyable());
	}
}

package ch.unibe.ese.team1.test.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import ch.unibe.ese.team1.controller.pojos.forms.PlaceAuctionForm;

public class PlaceAuctionFormTest {

	PlaceAuctionForm placeAuctionForm = new PlaceAuctionForm();
	
	@Test
	public void testTitle() {
		String title = "Test Auction";
		placeAuctionForm.setTitle(title);
		
		assertEquals(title, placeAuctionForm.getTitle());
	}
	
	@Test
	public void testStreet() {
		String street = "Test street";
		placeAuctionForm.setStreet(street);
		
		assertEquals(street, placeAuctionForm.getStreet());
	}
	
	@Test
	public void testCity() {
		String city = "Test city";
		placeAuctionForm.setCity(city);
		
		assertEquals(city, placeAuctionForm.getCity());
	}
	
	@Test
	public void testMoveInDate() {
		String moveInDate = "12-12-2012";
		placeAuctionForm.setMoveInDate(moveInDate);
		
		assertEquals(moveInDate, placeAuctionForm.getMoveInDate());
	}
	
	@Test
	public void testBuyable() {
		placeAuctionForm.setBuyable(true);
		assertTrue(placeAuctionForm.getBuyable());
		
		placeAuctionForm.setBuyable(false);
		assertFalse(placeAuctionForm.getBuyable());
	}
	
	@Test
	public void testPrize() {
		int prize = 500;
		placeAuctionForm.setPrize(prize);
		
		assertEquals(prize, placeAuctionForm.getPrize());
	}
	
	@Test
	public void testSquareFootage() {
		int squareFootage = 50;
		placeAuctionForm.setSquareFootage(squareFootage);
		
		assertEquals(squareFootage, placeAuctionForm.getSquareFootage());
	}
	
	@Test
	public void testRoomDescriptionAndPreferences() {
		String roomDescription = "Test Description";
		String preferences = "Test Preferences";
		placeAuctionForm.setRoomDescription(roomDescription);
		placeAuctionForm.setPreferences(preferences);
		
		assertEquals(roomDescription, placeAuctionForm.getRoomDescription());
		assertEquals(preferences, placeAuctionForm.getPreferences());
	}
	
	@Test
	public void testRoomType() {
		String roomType = "House";
		placeAuctionForm.setRoomType(roomType);
		
		assertEquals(roomType, placeAuctionForm.getRoomType());
	}
	
	@Test
	public void testDescriptions() {
		boolean description = false;
		
		placeAuctionForm.setAnimals(description);
		placeAuctionForm.setSmokers(description);
		placeAuctionForm.setGarden(description);
		placeAuctionForm.setBalcony(description);
		placeAuctionForm.setCellar(description);
		placeAuctionForm.setFurnished(description);
		placeAuctionForm.setCable(description);
		placeAuctionForm.setGarage(description);
		placeAuctionForm.setInternet(description);
		
		assertFalse(placeAuctionForm.isAnimals());
		assertFalse(placeAuctionForm.isSmokers());
		assertFalse(placeAuctionForm.getGarden());
		assertFalse(placeAuctionForm.getBalcony());
		assertFalse(placeAuctionForm.getCellar());
		assertFalse(placeAuctionForm.isFurnished());
		assertFalse(placeAuctionForm.getCable());
		assertFalse(placeAuctionForm.getGarage());
		assertFalse(placeAuctionForm.getInternet());
		
		description = true;
		
		placeAuctionForm.setAnimals(description);
		placeAuctionForm.setSmokers(description);
		placeAuctionForm.setGarden(description);
		placeAuctionForm.setBalcony(description);
		placeAuctionForm.setCellar(description);
		placeAuctionForm.setFurnished(description);
		placeAuctionForm.setCable(description);
		placeAuctionForm.setGarage(description);
		placeAuctionForm.setInternet(description);
		
		assertTrue(placeAuctionForm.isAnimals());
		assertTrue(placeAuctionForm.isSmokers());
		assertTrue(placeAuctionForm.getGarden());
		assertTrue(placeAuctionForm.getBalcony());
		assertTrue(placeAuctionForm.getCellar());
		assertTrue(placeAuctionForm.isFurnished());
		assertTrue(placeAuctionForm.getCable());
		assertTrue(placeAuctionForm.getGarage());
		assertTrue(placeAuctionForm.getInternet());
	}
	
	@Test
	public void testVisits() {
		List<String> visits = new ArrayList<String>();
		String testVisit1 = "Test visit 1";
		String testVisit2 = "Test visit 2";
		visits.add(testVisit1);
		visits.add(testVisit2);
		
		placeAuctionForm.setVisits(visits);
		
		assertTrue(placeAuctionForm.getVisits().contains(testVisit1));
		assertTrue(placeAuctionForm.getVisits().contains(testVisit2));
	}
	
	@Test
	public void testAuction() {
		boolean auction = false;
		
		placeAuctionForm.setAuction(auction);
		
		assertFalse(placeAuctionForm.isAuction());
		
		auction = true;
		
		placeAuctionForm.setAuction(auction);
		
		assertTrue(placeAuctionForm.isAuction());
	}
	
	@Test
	public void testEndDateAndEndTime() {
		String endDate = "12.12.2012";
		String endTime = "12:12";
		
		placeAuctionForm.setEndDate(endDate);
		placeAuctionForm.setEndTime(endTime);
		
		assertEquals(endDate, placeAuctionForm.getEndDate());
		assertEquals(endTime, placeAuctionForm.getEndTime());
	}
}

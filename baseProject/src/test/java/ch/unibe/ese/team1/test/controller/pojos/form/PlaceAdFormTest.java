package ch.unibe.ese.team1.test.controller.pojos.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.unibe.ese.team1.controller.pojos.forms.PlaceAdForm;

public class PlaceAdFormTest {

	PlaceAdForm placeAdForm = new PlaceAdForm();
	
	@Test
	public void testTitle() {
		String title = "Test title";
		placeAdForm.setTitle(title);
		
		assertEquals(title, placeAdForm.getTitle());
	}
	
	@Test
	public void testStreet() {
		String street = "Test street";
		placeAdForm.setStreet(street);
		
		assertEquals(street, placeAdForm.getStreet());
	}
	
	@Test
	public void testCity() {
		String city = "Test city";
		placeAdForm.setCity(city);
		
		assertEquals(city, placeAdForm.getCity());
	}
	
	@Test
	public void testDates() {
		String dateNow = "12-12-1212";
		placeAdForm.setMoveInDate(dateNow);
		placeAdForm.setMoveOutDate(dateNow);
		
		assertEquals(dateNow, placeAdForm.getMoveInDate());
		assertEquals(dateNow, placeAdForm.getMoveOutDate());
	}
	
	@Test
	public void testPrize() {
		int prize = 1000;
		placeAdForm.setPrize(prize);
		
		assertEquals(prize, placeAdForm.getPrize());
	}
	
	@Test
	public void testSquareFootage() {
		int squareFootage = 100;
		placeAdForm.setSquareFootage(squareFootage);
		
		assertEquals(squareFootage, placeAdForm.getSquareFootage());
	}
	
	@Test
	public void testRoomDescription() {
		String roomDescription = "Test description";
		placeAdForm.setRoomDescription(roomDescription);
		
		assertEquals(roomDescription, placeAdForm.getRoomDescription());
	}
	
	@Test
	public void testPreferences() {
		String preferences = "Test preferences";
		placeAdForm.setPreferences(preferences);
		
		assertEquals(preferences, placeAdForm.getPreferences());
	}
	
	@Test
	public void testRoomType() {
		String roomType = "Test Type";
		placeAdForm.setRoomType(roomType);
		
		assertEquals(roomType, placeAdForm.getRoomType());
	}
	
	@Test
	public void testBuyable() {
		boolean buyable = false;
		placeAdForm.setBuyable(buyable);
		
		assertFalse(placeAdForm.getBuyable());
		
		buyable = true;
		placeAdForm.setBuyable(buyable);
		
		assertTrue(placeAdForm.getBuyable());
	}
	
	@Test
	public void testDescriptions() {
		boolean description = false;
		placeAdForm.setAnimals(description);
		placeAdForm.setBalcony(description);
		placeAdForm.setCable(description);
		placeAdForm.setCellar(description);
		placeAdForm.setFurnished(description);
		placeAdForm.setGarage(description);
		placeAdForm.setGarden(description);
		placeAdForm.setInternet(description);
		placeAdForm.setSmokers(description);
		
		assertEquals(description, placeAdForm.isAnimals());
		assertEquals(description, placeAdForm.getBalcony());
		assertEquals(description, placeAdForm.getCable());
		assertEquals(description, placeAdForm.getCellar());
		assertEquals(description, placeAdForm.isFurnished());
		assertEquals(description, placeAdForm.getGarage());
		assertEquals(description, placeAdForm.getGarden());
		assertEquals(description, placeAdForm.getInternet());
		assertEquals(description, placeAdForm.isSmokers());
		
		description = true;
		placeAdForm.setAnimals(description);
		placeAdForm.setBalcony(description);
		placeAdForm.setCable(description);
		placeAdForm.setCellar(description);
		placeAdForm.setFurnished(description);
		placeAdForm.setGarage(description);
		placeAdForm.setGarden(description);
		placeAdForm.setInternet(description);
		placeAdForm.setSmokers(description);
		
		assertEquals(description, placeAdForm.isAnimals());
		assertEquals(description, placeAdForm.getBalcony());
		assertEquals(description, placeAdForm.getCable());
		assertEquals(description, placeAdForm.getCellar());
		assertEquals(description, placeAdForm.isFurnished());
		assertEquals(description, placeAdForm.getGarage());
		assertEquals(description, placeAdForm.getGarden());
		assertEquals(description, placeAdForm.getInternet());
		assertEquals(description, placeAdForm.isSmokers());
	}
	
	@Test
	public void testVisits() {
		List<String> visits = new ArrayList<String>();
		String visit1 = "Visit1";
		String visit2 = "Visit2";
		visits.add(visit1);
		visits.add(visit2);
		placeAdForm.setVisits(visits);
		
		assertEquals(2, placeAdForm.getVisits().size());
		assertTrue(placeAdForm.getVisits().contains(visit1));
		assertTrue(placeAdForm.getVisits().contains(visit2));
	}
	
	@Test
	public void testAuction() {
		boolean auction = false;
		placeAdForm.setAuction(auction);
		
		assertFalse(placeAdForm.getAuction());
		
		auction = true;
		placeAdForm.setAuction(auction);
		
		assertTrue(placeAdForm.getAuction());
	}
}
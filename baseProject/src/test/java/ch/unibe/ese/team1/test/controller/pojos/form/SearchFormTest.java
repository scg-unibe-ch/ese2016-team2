package ch.unibe.ese.team1.test.controller.pojos.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import ch.unibe.ese.team1.controller.pojos.forms.SearchForm;

public class SearchFormTest {

	SearchForm searchForm = new SearchForm();
	
	@Test
	public void testBuyable() {
		Boolean buyable = true;
		searchForm.setBuyable(buyable);
	
		assertTrue(searchForm.getBuyable());
		
		buyable = false;
		searchForm.setBuyable(buyable);
	
		assertFalse(searchForm.getBuyable());
	}
	
	@Test
	public void testStudio() {
		Boolean studio = true;
		searchForm.setStudio(studio);
	
		assertTrue(searchForm.getStudio());
		
		studio = false;
		searchForm.setStudio(studio);
	
		assertFalse(searchForm.getStudio());
	}
	
	@Test
	public void testRoom() {
		Boolean room = true;
		searchForm.setRoom(room);
	
		assertTrue(searchForm.getRoom());
		
		room = false;
		searchForm.setRoom(room);
	
		assertFalse(searchForm.getRoom());
	}
	
	@Test
	public void testHouse() {
		Boolean house = true;
		searchForm.setHouse(house);
	
		assertTrue(searchForm.getHouse());
		
		house = false;
		searchForm.setHouse(house);
	
		assertFalse(searchForm.getHouse());
	}
	
	@Test
	public void testCity() {
		String city = "3000 - Bern";
		searchForm.setCity(city);
	
		assertEquals(city, searchForm.getCity());
	}
	
	@Test
	public void testPrize() {
		Integer prize = 5000;
		searchForm.setPrize(prize);
	
		assertEquals(prize, searchForm.getPrize());
	}
	
	@Test
	public void testRadius() {
		Integer radius = 5;
		searchForm.setRadius(radius);
	
		assertEquals(radius, searchForm.getRadius());
	}
	
	@Test
	public void testNeither() {
		Boolean neither = true;
		searchForm.setNeither(neither);
	
		assertTrue(searchForm.getNeither());
		
		neither = false;
		searchForm.setNeither(neither);
	
		assertFalse(searchForm.getNeither());
	}
	
	@Test
	public void testBuyableNotFilled() {
		Boolean buyableNotFilled = true;
		searchForm.setBuyableNotFilled(buyableNotFilled);
	
		assertTrue(searchForm.getBuyableNotFilled());
		
		buyableNotFilled = false;
		searchForm.setBuyableNotFilled(buyableNotFilled);
	
		assertFalse(searchForm.getBuyableNotFilled());
	}
	
	@Test
	public void testSmokers() {
		Boolean smokers = true;
		searchForm.setSmokers(smokers);
	
		assertTrue(searchForm.getSmokers());
		
		smokers = false;
		searchForm.setSmokers(smokers);
	
		assertFalse(searchForm.getSmokers());
	}
	
	@Test
	public void testAnimals() {
		Boolean animals = true;
		searchForm.setAnimals(animals);
	
		assertTrue(searchForm.getAnimals());
		
		animals = false;
		searchForm.setAnimals(animals);
	
		assertFalse(searchForm.getAnimals());
	}
	
	@Test
	public void testGarden() {
		Boolean garden = true;
		searchForm.setGarden(garden);
	
		assertTrue(searchForm.getGarden());
		
		garden = false;
		searchForm.setGarden(garden);
	
		assertFalse(searchForm.getGarden());
	}
	
	@Test
	public void testBalcony() {
		Boolean balcony = true;
		searchForm.setBalcony(balcony);
	
		assertTrue(searchForm.getBalcony());
		
		balcony = false;
		searchForm.setBalcony(balcony);
	
		assertFalse(searchForm.getBalcony());
	}
	
	@Test
	public void testCellar() {
		Boolean cellar = true;
		searchForm.setCellar(cellar);
	
		assertTrue(searchForm.getCellar());
		
		cellar = false;
		searchForm.setCellar(cellar);
	
		assertFalse(searchForm.getCellar());
	}
	
	@Test
	public void testFurnished() {
		Boolean furnished = true;
		searchForm.setFurnished(furnished);
	
		assertTrue(searchForm.getFurnished());
		
		furnished = false;
		searchForm.setFurnished(furnished);
	
		assertFalse(searchForm.getFurnished());
	}
	
	@Test
	public void testCable() {
		Boolean cable = true;
		searchForm.setCable(cable);
	
		assertTrue(searchForm.getCable());
		
		cable = false;
		searchForm.setCable(cable);
	
		assertFalse(searchForm.getCable());
	}
	
	@Test
	public void testGarage() {
		Boolean garage = true;
		searchForm.setGarage(garage);
	
		assertTrue(searchForm.getGarage());
		
		garage = false;
		searchForm.setGarage(garage);
	
		assertFalse(searchForm.getGarage());
	}
	
	@Test
	public void testInternet() {
		Boolean internet = true;
		searchForm.setInternet(internet);
	
		assertTrue(searchForm.getInternet());
		
		internet = false;
		searchForm.setInternet(internet);
	
		assertFalse(searchForm.getInternet());
	}
	
	@Test
	public void testEarliestMoveInDate() {
		String earliestMoveInDate = "12-12-2012";
		searchForm.setEarliestMoveInDate(earliestMoveInDate);
		
		assertEquals(earliestMoveInDate, searchForm.getEarliestMoveInDate());
	}
	
	@Test
	public void testLatestMoveInDate() {
		String latestMoveInDate = "12-10-2014";
		searchForm.setLatestMoveInDate(latestMoveInDate);
		
		assertEquals(latestMoveInDate, searchForm.getLatestMoveInDate());
	}
	
	@Test
	public void testEarliestMoveOutDate() {
		String earliestMoveOutDate = "12-12-2014";
		searchForm.setEarliestMoveOutDate(earliestMoveOutDate);
		
		assertEquals(earliestMoveOutDate, searchForm.getEarliestMoveOutDate());
	}
	
	@Test
	public void testLatestMoveOutDate() {
		String latestMoveOutDate = "12-10-2016";
		searchForm.setLatestMoveOutDate(latestMoveOutDate);
		
		assertEquals(latestMoveOutDate, searchForm.getLatestMoveOutDate());
	}
}

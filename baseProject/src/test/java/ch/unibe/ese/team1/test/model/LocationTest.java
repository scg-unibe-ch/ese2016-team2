package ch.unibe.ese.team1.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.unibe.ese.team1.model.Location;

public class LocationTest {

	Location location = new Location();
	
	@Test
	public void testZip() {
		int zip = 3000;
		location.setZip(zip);
		
		assertEquals(zip, location.getZip());
	}
	
	@Test
	public void testCity() {
		String city = "Test City";
		location.setCity(city);
		
		assertEquals(city, location.getCity());
	}
	
	@Test
	public void testLatitiude() {
		double latitude = 100;
		location.setLatitude(latitude);
		
		assertTrue(Math.abs(latitude-location.getLatitude()) == 0.0);
	}
	
	@Test
	public void testLongitude() {
		double longitude = 100;
		location.setLongitude(longitude);
		
		assertTrue(Math.abs(longitude-location.getLongitude()) == 0.0);
	}
}

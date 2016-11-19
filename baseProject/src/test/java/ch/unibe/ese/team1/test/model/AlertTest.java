package ch.unibe.ese.team1.test.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.unibe.ese.team1.model.Alert;
import ch.unibe.ese.team1.model.User;

public class AlertTest {

	private Alert alert = new Alert();
	
	@Test
	public void testId() {
		long id = 1;
		alert.setId(id);
		
		assertEquals(id, alert.getId());
	}
	
	@Test
	public void testUser() {
		User user = new User();
		alert.setUser(user);
		
		assertEquals(user, alert.getUser());
	}
	
	@Test
	public void testZipcode() {
		int zipcode = 3000;
		alert.setZipcode(zipcode);
		
		assertEquals(zipcode, alert.getZipcode());
	}
	
	@Test
	public void testCity() {
		String city = "Test city";
		alert.setCity(city);
		
		assertEquals(city, alert.getCity());
	}
	
	@Test
	public void testPrice() {
		int price = 100;
		alert.setPrice(price);
		
		assertEquals(price, alert.getPrice());
	}
	
	@Test
	public void testRadius() {
		int radius = 100;
		alert.setRadius(radius);
		
		assertEquals(radius, alert.getRadius());
	}
	
	@Test
	public void testAlertType() {
		String alertType = "Test Type";
		alert.setAlertType(alertType);
		
		assertEquals(alertType, alert.getAlertType());
	}
}

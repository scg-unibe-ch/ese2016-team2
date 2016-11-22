package ch.unibe.ese.team1.test.controller.pojos.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.unibe.ese.team1.controller.pojos.forms.AlertForm;
import ch.unibe.ese.team1.model.User;

public class AlertFormTest {

	AlertForm alertForm = new AlertForm();
	
	@Test
	public void testUser() {
		User user = new User();
		alertForm.setUser(user);
		
		assertEquals(user, alertForm.getUser());
	}
	
	@Test
	public void testAlertType() {
		boolean type = false;
		alertForm.setRoom(type);
		alertForm.setStudio(type);
		alertForm.setHouse(type);
		
		assertFalse(alertForm.getRoom());
		assertFalse(alertForm.getStudio());
		assertFalse(alertForm.getHouse());
		
		type = true;
		alertForm.setRoom(type);
		alertForm.setStudio(type);
		alertForm.setHouse(type);
		
		assertTrue(alertForm.getRoom());
		assertTrue(alertForm.getStudio());
		assertTrue(alertForm.getHouse());
		
		String alertType = "Test type";
		alertForm.setAlertType(alertType);
		
		assertEquals(alertType, alertForm.getAlertType());
	}
	
	@Test
	public void testCity() {
		String city = "Test city";
		alertForm.setCity(city);
		
		assertEquals(city, alertForm.getCity());
	}
	
	@Test
	public void testPrice() {
		Integer price = 1000;
		alertForm.setPrice(price);
		
		assertEquals((int) price, (int) alertForm.getPrice());
	}
	
	@Test
	public void testRadius() {
		Integer radius = 100;
		alertForm.setRadius(radius);
		
		assertEquals((int) radius, (int) alertForm.getRadius());
	}
	
	@Test 
	public void testZipcode() {
		int zipcode = 3000;
		alertForm.setZipCode(zipcode);
		
		assertEquals(zipcode, alertForm.getZipCode());
	}
	
	@Test
	public void testNoRoomNoStudio() {
		boolean test = false;
		alertForm.setNoRoomNoStudio(test);
		
		assertFalse(alertForm.getNoRoomNoStudio());
		
		test = true;
		alertForm.setNoRoomNoStudio(test);
		
		assertTrue(alertForm.getNoRoomNoStudio());
	}
}

package ch.unibe.ese.team1.test.controller.pojos.form;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.unibe.ese.team1.controller.pojos.forms.RegisterForm;

public class RegisterFormTest {
	
	private RegisterForm registerForm = new RegisterForm();
	
	@Test
	public void testUsername() {
		String username = "Test name";
		registerForm.setUsername(username);
		
		assertEquals(username, registerForm.getUsername());
	}
	
	@Test
	public void testStreet() {
		String street = "Test street";
		registerForm.setStreet(street);
		
		assertEquals(street, registerForm.getStreet());
	}
	
	@Test
	public void testCity() {
		String city = "Test city";
		registerForm.setCity(city);
		
		assertEquals(city, registerForm.getCity());
	}
	
	@Test
	public void testAccount() {
		//Account will always be "Premium"
		registerForm.setAccount("Test");
		
		assertEquals("Premium", registerForm.getAccount());
	}

}

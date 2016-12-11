package ch.unibe.ese.team1.test.controller.pojos.form;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.unibe.ese.team1.controller.pojos.forms.SignupForm;
import ch.unibe.ese.team1.model.Gender;

public class SignupFormTest {

	private SignupForm signupForm = new SignupForm();
	
	@Test
	public void testPassword() {
		String password = "Test password";
		signupForm.setPassword(password);
		
		assertEquals(password, signupForm.getPassword());
	}
	
	@Test
	public void testEmail() {
		String email = "Test email";
		signupForm.setEmail(email);
		
		assertEquals(email, signupForm.getEmail());
	}
	
	@Test
	public void testName() {
		String name = "Test name";
		signupForm.setFirstName(name);
		signupForm.setLastName(name);
		
		assertEquals(name, signupForm.getFirstName());
		assertEquals(name, signupForm.getLastName());
	}
	
	@Test
	public void testGender() {
		Gender gender = Gender.MALE;
		signupForm.setGender(gender);
		
		assertEquals(gender, signupForm.getGender());
	}
	
	@Test
	public void testAccount() {
		String account = "Test account";
		signupForm.setAccount(account);
		
		assertEquals(account, signupForm.getAccount());
	}
}

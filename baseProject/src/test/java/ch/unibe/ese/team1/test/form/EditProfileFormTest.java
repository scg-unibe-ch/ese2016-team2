package ch.unibe.ese.team1.test.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import ch.unibe.ese.team1.controller.pojos.forms.EditProfileForm;

public class EditProfileFormTest {

	EditProfileForm searchForm = new EditProfileForm();
	
	@Test
	public void testPassword() {
		String password = "Ab123456";
		searchForm.setPassword(password);
	
		assertEquals(password, searchForm.getPassword());
	}
	
	@Test
	public void testFirstName() {
		String firstName = "Hans";
		searchForm.setFirstName(firstName);
	
		assertEquals(firstName, searchForm.getFirstName());
	}
	
	@Test
	public void testLastName() {
		String lastName = "Mueller";
		searchForm.setLastName(lastName);
	
		assertEquals(lastName, searchForm.getLastName());
	}
	
	@Test
	public void testAboutMe() {
		String aboutMe = "Test about me.";
		searchForm.setAboutMe(aboutMe);
	
		assertEquals(aboutMe, searchForm.getAboutMe());
	}
	
	@Test
	public void testUsername() {
		String username = "username@test.ch";
		searchForm.setUsername(username);
	
		assertEquals(username, searchForm.getUsername());
	}
}

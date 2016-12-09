package ch.unibe.ese.team1.test.controller.pojos.form;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.unibe.ese.team1.controller.pojos.forms.EditProfileForm;

public class EditProfileFormTest {

	EditProfileForm editProfileForm = new EditProfileForm();
	
	@Test
	public void testPassword() {
		String password = "Ab123456";
		editProfileForm.setPassword(password);
	
		assertEquals(password, editProfileForm.getPassword());
	}
	
	@Test
	public void testFirstName() {
		String firstName = "Hans";
		editProfileForm.setFirstName(firstName);
	
		assertEquals(firstName, editProfileForm.getFirstName());
	}
	
	@Test
	public void testLastName() {
		String lastName = "Mueller";
		editProfileForm.setLastName(lastName);
	
		assertEquals(lastName, editProfileForm.getLastName());
	}
	
	@Test
	public void testAboutMe() {
		String aboutMe = "Test about me.";
		editProfileForm.setAboutMe(aboutMe);
	
		assertEquals(aboutMe, editProfileForm.getAboutMe());
	}
	
	@Test
	public void testUsername() {
		String username = "username@test.ch";
		editProfileForm.setUsername(username);
	
		assertEquals(username, editProfileForm.getUsername());
	}
	
	@Test
	public void testImagePath() {
		String imagePath = "Test path";
		editProfileForm.setImagePath(imagePath);
		
		assertEquals(imagePath, editProfileForm.getImagePath());
	}
}

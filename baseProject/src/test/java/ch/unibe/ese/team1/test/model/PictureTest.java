package ch.unibe.ese.team1.test.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.unibe.ese.team1.model.AdPicture;
import ch.unibe.ese.team1.model.UserPicture;
import ch.unibe.ese.team1.model.Picture;
import ch.unibe.ese.team1.model.User;

public class PictureTest {

	private Picture picture = new AdPicture();
	
	@Test
	public void testId() {
		long id = 1;
		picture.setId(id);
		
		assertEquals(id, picture.getId());
	}
	
	@Test
	public void testFilePath() {
		String filePath = "Test path";
		picture.setFilePath(filePath);
		
		assertEquals(filePath, picture.getFilePath());
	}
	
	//Test for UserPicutre
	@Test
	public void testUserPicture() {
		UserPicture pic = new UserPicture();
		User user = new User();
		
		pic.setUser(user);
		
		assertEquals(user, pic.getUser());
	}
}

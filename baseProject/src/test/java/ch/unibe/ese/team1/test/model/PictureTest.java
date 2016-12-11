package ch.unibe.ese.team1.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.unibe.ese.team1.model.AdPicture;
import ch.unibe.ese.team1.model.AuctionPicture;
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
	
	@Test
	public void testEquals() {
		Picture pic1 = new AdPicture();
		Picture pic2 = new AdPicture();
		pic1.setId(1);
		pic2.setId(2);
		
		assertTrue(pic1.equals(pic1));
		assertFalse(pic1.equals(null));
		assertFalse(pic1.equals(new AuctionPicture()));
		assertFalse(pic1.equals(pic2));
		
		pic2.setId(1);
		assertTrue(pic1.equals(pic2));
	}
}

package ch.unibe.ese.team1.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.Gender;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.UserPicture;
import ch.unibe.ese.team1.model.UserRole;

public class UserTest {
	
	User user = new User();
	
	@Test
	public void testId() {
		long id = 1;
		user.setId(id);
		
		assertEquals(id, user.getId());
	}
	
	@Test
	public void testStringAttributes() {
		String text = "Test Text";
		user.setUsername(text);
		user.setEmail(text);
		user.setPassword(text);
		user.setFirstName(text);
		user.setLastName(text);
		user.setAccount(text);
		user.setStreet(text);
		user.setCity(text);
		user.setAboutMe(text);
		
		assertEquals(text, user.getUsername());
		assertEquals(text, user.getEmail());
		assertEquals(text, user.getPassword());
		assertEquals(text, user.getFirstName());
		assertEquals(text, user.getLastName());
		assertEquals(text, user.getAccount());
		assertEquals(text, user.getStreet());
		assertEquals(text, user.getCity());
		assertEquals(text, user.getAboutMe());
	}
	
	@Test
	public void testGender() {
		Gender gender = Gender.MALE;
		user.setGender(gender);
		
		assertEquals(gender, user.getGender());
	}
	
	@Test
	public void testZipcode() {
		int zipcode = 3000;
		user.setZipcode(zipcode);
		
		assertEquals(zipcode, user.getZipcode());
	}

	@Test
	public void testEnabled() {
		boolean enabled = false;
		user.setEnabled(enabled);
		
		assertFalse(user.isEnabled());
		
		enabled = true;
		
		user.setEnabled(enabled);
		
		assertTrue(user.isEnabled());
	}
	
	@Test
	public void testUserRoles() {
		Set<UserRole> userRoles = new HashSet<>();
		UserRole userRole = new UserRole();
		userRoles.add(userRole);
		
		user.setUserRoles(userRoles);
		
		assertEquals(1, user.getUserRoles().size());
		assertTrue(user.getUserRoles().contains(userRole));
	}
	
	@Test
	public void testUserPicture() {
		UserPicture picture = new UserPicture();
		user.setPicture(picture);
		
		assertEquals(picture, user.getPicture());
	}
	
	@Test
	public void testBookmarkedAds() {
		ArrayList<Ad> bookmarkedAds = new ArrayList<Ad>();
		Ad ad = new Ad();
		bookmarkedAds.add(ad);
		
		user.setBookmarkedAds(bookmarkedAds);
		
		assertEquals(1, user.getBookmarkedAds().size());
		assertTrue(user.getBookmarkedAds().contains(ad));
	}
	
	@Test
	public void testBookmarkedAuctions() {
		ArrayList<Auction> bookmarkedAuctions = new ArrayList<Auction>();
		Auction auction = new Auction();
		bookmarkedAuctions.add(auction);
		
		user.setBookmarkedAuctions(bookmarkedAuctions);
		
		assertEquals(1, user.getBookmarkedAuctions().size());
		assertTrue(user.getBookmarkedAuctions().contains(auction));
	}
	
	@Test
	public void testEquals() {
		User user1 = new User();
		User user2 = new User();
		user1.setId(1);
		user2.setId(2);
		
		assertTrue(user1.equals(user1));
		assertFalse(user1.equals(null));
		assertFalse(user1.equals(new Ad()));
		assertFalse(user1.equals(user2));
		
		user2.setId(1);
		assertTrue(user1.equals(user2));
		
	}
}

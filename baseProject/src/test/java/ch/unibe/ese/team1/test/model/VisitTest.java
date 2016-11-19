package ch.unibe.ese.team1.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.Visit;

public class VisitTest {
	
	Visit visit = new Visit();
	
	@Test
	public void testId() {
		long id = 1;
		visit.setId(id);
		
		assertEquals(id, visit.getId());
	}
	
	@Test
	public void testAdvertisement() {
		Ad ad = new Ad();
		Auction auction = new Auction();
		visit.setAd(ad);
		visit.setAuction(auction);
		
		assertEquals(ad, visit.getAd());
		assertEquals(auction, visit.getAuction());
	}
	
	@Test
	public void testSearchers() {
		ArrayList<User> users = new ArrayList<User>();
		User user1 = new User();
		User user2 = new User();
		users.add(user1);
		users.add(user2);
		visit.setSearchers(users);
		
		assertEquals(2, visit.getSearchers().size());
		assertTrue(visit.getSearchers().contains(user1));
		assertTrue(visit.getSearchers().contains(user2));
	}
	
	@Test 
	public void testDates() {
		Date dateNow = new Date();
		visit.setEndTimestamp(dateNow);
		visit.setStartTimestamp(dateNow);
		
		assertEquals(dateNow, visit.getEndTimestamp());
		assertEquals(dateNow, visit.getStartTimestamp());
	}
}

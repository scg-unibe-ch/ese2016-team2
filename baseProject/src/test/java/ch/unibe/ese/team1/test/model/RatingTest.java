package ch.unibe.ese.team1.test.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.unibe.ese.team1.model.Rating;
import ch.unibe.ese.team1.model.User;

public class RatingTest {

	Rating rating = new Rating();
	
	@Test
	public void testId() {
		long id = 1;
		rating.setId(id);
		
		assertEquals(id, rating.getId());
	}
	
	@Test
	public void testUser() {
		User user = new User();
		rating.setRatee(user);
		rating.setRater(user);
		
		assertEquals(user, rating.getRatee());
		assertEquals(user, rating.getRater());
	}
	
	@Test
	public void testRating() {
		int ratingForUser = 3;
		rating.setRating(ratingForUser);
		
		assertEquals(ratingForUser, rating.getRating());
	}
}

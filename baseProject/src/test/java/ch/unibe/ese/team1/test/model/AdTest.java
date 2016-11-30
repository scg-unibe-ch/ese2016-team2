package ch.unibe.ese.team1.test.model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.AdPicture;
import ch.unibe.ese.team1.model.Visit;

public class AdTest {
	
	private Ad ad = new Ad();

	@Test
	public void testPictures() {
		AdPicture adPicture = new AdPicture();
		adPicture.setFilePath("TestPath");
		adPicture.setId(0);
		
		List<AdPicture> pictures = new ArrayList<AdPicture>();
		pictures.add(adPicture);
		
		ad.setPictures(pictures);
		
		assertTrue(ad.getPictures().contains(adPicture));
	}
	
	@Test
	public void testVisits() {
		Visit visit1 = new Visit();
		Visit visit2 = new Visit();
		ArrayList<Visit> visits = new ArrayList<Visit>();
		visits.add(visit1);
		visits.add(visit2);
		ad.setVisits(visits);
		
		assertTrue(ad.getVisits().contains(visit1));
		assertTrue(ad.getVisits().contains(visit2));
	}
	
}

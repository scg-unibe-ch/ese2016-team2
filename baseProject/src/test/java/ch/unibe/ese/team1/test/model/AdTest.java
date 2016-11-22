package ch.unibe.ese.team1.test.model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.AdPicture;

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
	
}

package ch.unibe.ese.team1.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.AuctionPicture;
import ch.unibe.ese.team1.model.Visit;

public class AuctionTest {
	
	private Auction auction = new Auction();
	
	@Test
	public void testEndTime() {
		String testEndTime = "12:00, 12.13.2017";
		auction.setEndTime(testEndTime);
		assertEquals(auction.getEndTime(), testEndTime);
	}
	
	@Test
	public void testAuctionEnded() {
		boolean auctionEnded = false;
		auction.setAuctionEnded(auctionEnded);
		
		assertFalse(auction.getAuctionEnded());
		
		auctionEnded = true;
		auction.setAuctionEnded(auctionEnded);
		
		assertTrue(auction.getAuctionEnded());
	}
	
	@Test
	public void testBidderName() {
		String testBidderName = "Test name";
		auction.setBidderName(testBidderName);
		
		assertEquals(auction.getBidderName(), testBidderName);
	}
	
	@Test
	public void testPictures() {
		AuctionPicture auctionPicture = new AuctionPicture();
		auctionPicture.setFilePath("TestPath");
		auctionPicture.setId(0);
		
		List<AuctionPicture> pictures = new ArrayList<AuctionPicture>();
		pictures.add(auctionPicture);
		
		auction.setPictures(pictures);
		
		assertTrue(auction.getPictures().contains(auctionPicture));
	}
	
	@Test
	public void testVisits() {
		Visit visit1 = new Visit();
		Visit visit2 = new Visit();
		ArrayList<Visit> visits = new ArrayList<Visit>();
		visits.add(visit1);
		visits.add(visit2);
		auction.setVisits(visits);
		
		assertTrue(auction.getVisits().contains(visit1));
		assertTrue(auction.getVisits().contains(visit2));
	}

}

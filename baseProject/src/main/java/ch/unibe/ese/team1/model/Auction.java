package ch.unibe.ese.team1.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/** Describes an advertisement that users can place and search for. */
@Entity
public class Auction extends Advertisement{
	
	@Column
	private String endTime;
	
	@Column
	private boolean auctionEnded;
	
	@Column
	private String bidderName;

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getBidderName() {
		return bidderName;
	}
	
	public void setBidderName(String bidderName) {
		this.bidderName = bidderName;
	}
	
	public boolean getAuctionEnded() {
		return auctionEnded;
	}
	
	public void setAuctionEnded(boolean auctionEnded) {
		this.auctionEnded = auctionEnded;
	}

}

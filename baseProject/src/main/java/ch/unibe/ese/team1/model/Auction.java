package ch.unibe.ese.team1.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/** Describes an advertisement that users can place and search for. */
@Entity
public class Auction extends Advertisement{
	
	@Column
	private String endTime;
	
	@Column
	private boolean auctionEnded;
	
	@Column
	private String bidderName;
	
	@OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Visit> visits;
	
	@Fetch(FetchMode.SELECT)
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<AuctionPicture> pictures;

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
	
	public List<AuctionPicture> getPictures() {
		return pictures;
	}

	public void setPictures(List<AuctionPicture> pictures) {
		this.pictures = pictures;
	}

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}
	
	

}

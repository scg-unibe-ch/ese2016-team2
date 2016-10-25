package ch.unibe.ese.team1.controller.pojos.forms;

import javax.validation.constraints.Min;

public class PlaceBidForm {
	
	@Min(value = 1, message = "Has to be equal to 1 or more")
	private int prize;
	
	private long id; 
	
	public int getPrize() {
		return prize;
	}

	public void setPrize(int prize) {
		this.prize = prize;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
}

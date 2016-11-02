package ch.unibe.ese.team1.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.User;

public interface AuctionDao extends CrudRepository<Auction, Long> {

	/** this will be used if both rooms AND studios are searched */
	public Iterable<Auction> findByPrizeLessThan (int prize);

	/** this will be used if only rooms or studios are searched */
	public List<Auction> findByRoomTypeAndPrizeLessThan(String roomType,
			int i);

	public Iterable<Auction> findByUser(User user);
	
}

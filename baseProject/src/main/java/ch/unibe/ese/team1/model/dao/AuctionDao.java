package ch.unibe.ese.team1.model.dao;

import org.springframework.data.repository.CrudRepository;

import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.User;

public interface AuctionDao extends CrudRepository<Auction, Long> {

	/** this will be used if both rooms AND studios are searched */
	public Iterable<Auction> findByPrizeLessThan (int prize);

	/** this will be used if only rooms or studios are searched */
	public Iterable<Auction> findByStudioAndPrizeLessThan(boolean studio,
			int i);

	public Iterable<Auction> findByUser(User user);
	
}

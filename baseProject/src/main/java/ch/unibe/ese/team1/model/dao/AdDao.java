package ch.unibe.ese.team1.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.User;

public interface AdDao extends CrudRepository<Ad, Long> {
	
	/** this will be used if all types are searched */
	public Iterable<Ad> findByPrizeLessThan (int prize);
	

	public Iterable<Ad> findByUser(User user);

	/** this will be used if one type is searched */
	public List<Ad> findByRoomTypeAndPrizeLessThan(String roomType, 
			int i);
}		
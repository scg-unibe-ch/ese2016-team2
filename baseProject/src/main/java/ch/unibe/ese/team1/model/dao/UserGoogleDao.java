package ch.unibe.ese.team1.model.dao;

import org.springframework.data.repository.CrudRepository;

import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.UserGoogle;

public interface UserGoogleDao extends CrudRepository<UserGoogle, Long> {
	public User findByUsername(String username);
	
	public User findUserById(long id);
}

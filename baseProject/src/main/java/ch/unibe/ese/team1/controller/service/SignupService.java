package ch.unibe.ese.team1.controller.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.unibe.ese.team1.controller.pojos.forms.SignupForm;
import ch.unibe.ese.team1.controller.pojos.forms.SignupGoogleForm;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.UserGoogle;
import ch.unibe.ese.team1.model.UserRole;
import ch.unibe.ese.team1.model.dao.UserDao;
import ch.unibe.ese.team1.model.dao.UserGoogleDao;

/** Handles the persisting of new users */
@Service
public class SignupService {
	
	private static final String DEFAULT_ROLE = "ROLE_USER";
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserGoogleDao userGoogleDao;

	/** Handles persisting a new user to the database. */
	@Transactional
	public void saveFrom(SignupForm signupForm) {
		User user = new User();
		user.setUsername(signupForm.getEmail());
		user.setEmail(signupForm.getEmail());
		user.setFirstName(signupForm.getFirstName());
		user.setLastName(signupForm.getLastName());
		user.setPassword(signupForm.getPassword());
		user.setEnabled(true);
		user.setGender(signupForm.getGender());
		user.setAccount(signupForm.getAccount());
		
		Set<UserRole> userRoles = new HashSet<>();
		UserRole role = new UserRole();
		role.setRole(DEFAULT_ROLE);
		role.setUser(user);
		userRoles.add(role);
		
		user.setUserRoles(userRoles);
		
		userDao.save(user);
	}
	

	/** Handles persisting a new user to the database. */
	@Transactional
	public void saveFromGoogle(SignupGoogleForm signupGoogleForm) {
		UserGoogle user = new UserGoogle();
		user.setUsername(signupGoogleForm.getEmail());
		user.setEmail(signupGoogleForm.getEmail());
		user.setFirstName(signupGoogleForm.getFirstName());
		user.setLastName(signupGoogleForm.getLastName());
		user.setEnabled(true);
		
		Set<UserRole> userRoles = new HashSet<>();
		UserRole role = new UserRole();
		role.setRole(DEFAULT_ROLE);
		role.setUser(user);
		userRoles.add(role);
		
		user.setUserRoles(userRoles);
		
		userGoogleDao.save(user);
	}
	
	/**
	 * Returns whether a user with the given username already exists.
	 * @param username the username to check for
	 * @return true if the user already exists, false otherwise
	 */
	@Transactional
	public boolean doesUserWithUsernameExist(String username){
		return userDao.findByUsername(username) != null;
	}
}

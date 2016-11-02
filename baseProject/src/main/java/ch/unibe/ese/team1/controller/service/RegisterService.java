package ch.unibe.ese.team1.controller.service;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.unibe.ese.team1.controller.pojos.forms.EditProfileForm;
import ch.unibe.ese.team1.controller.pojos.forms.RegisterForm;
import ch.unibe.ese.team1.controller.pojos.forms.SignupForm;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.UserRole;
import ch.unibe.ese.team1.model.dao.UserDao;

/** Handles the persisting of new users */
@Service
public class RegisterService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private UserService userService;

	/** Handles updating an existing user in the database. */
	@Transactional
	public void updateFrom(RegisterForm registerForm) {
		
		User currentUser = userService.findUserByUsername(registerForm.getUsername());
		
		// take the zipcode - first four digits
		String zip = registerForm.getCity().substring(0, 4);
		currentUser.setZipcode(Integer.parseInt(zip));
		currentUser.setCity(registerForm.getCity().substring(7));
		currentUser.setStreet(registerForm.getStreet());
		currentUser.setAccount(registerForm.getAccount());

		userDao.save(currentUser);
	}

	
	
}

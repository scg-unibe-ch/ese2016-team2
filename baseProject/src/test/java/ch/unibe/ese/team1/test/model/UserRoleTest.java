package ch.unibe.ese.team1.test.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.UserRole;

public class UserRoleTest {

	UserRole userRole = new UserRole();
	
	@Test
	public void testId() {
		long id = 1;
		userRole.setId(id);
		
		assertEquals(id, userRole.getId());
	}
	
	@Test
	public void testUser() {
		User user = new User();
		userRole.setUser(user);
		
		assertEquals(user, userRole.getUser());
	}
	
	@Test
	public void testRole() {
		String role = "Test Role";
		userRole.setRole(role);
		
		assertEquals(role, userRole.getRole());
	}
}

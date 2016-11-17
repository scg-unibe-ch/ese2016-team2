package ch.unibe.ese.team1.test.controller.pojos.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.ese.team1.controller.pojos.forms.AlertForm;

public class AlertFormValidationTest {
	
	private static Validator validator;
	private AlertForm alertForm = new AlertForm();

	@Before
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

		alertForm = new AlertForm();
		alertForm.setCity("3000 - Bern");
		alertForm.setRadius(100);
		alertForm.setPrice(500);
		alertForm.setNoRoomNoStudio(false);
		
		Set<ConstraintViolation<AlertForm>> constraintViolations = validator.validate(alertForm);
		assertEquals(0, constraintViolations.size());
	}
	
	@Test 
	public void cityIsEmpty() {
		alertForm.setCity("");
		Set<ConstraintViolation<AlertForm>> constraintViolations = validator.validate(alertForm);
		assertEquals(2, constraintViolations.size());
		String errorMessage = constraintViolations.iterator().next().getMessage();
		assertTrue("Required".equals(errorMessage) || "Please pick a city from the list".equals(errorMessage));
	}
	
	@Test
	public void cityIsFalse() {
		alertForm.setCity("asdf");
		Set<ConstraintViolation<AlertForm>> constraintViolations = validator.validate(alertForm);
		assertEquals(1, constraintViolations.size());
		assertEquals("Please pick a city from the list", constraintViolations.iterator().next().getMessage());
		
	}
	
	@Test
	public void prizeToLow() {
		alertForm.setPrice(-5);
		Set<ConstraintViolation<AlertForm>> constraintViolations = validator.validate(alertForm);
		assertEquals(1, constraintViolations.size());
		assertEquals("In your dreams.", constraintViolations.iterator().next().getMessage());	
	}
	
	@Test
	public void distanceToLow() {
		alertForm.setRadius(-5);
		Set<ConstraintViolation<AlertForm>> constraintViolations = validator.validate(alertForm);
		assertEquals(1, constraintViolations.size());
		assertEquals("Please enter a positive distance", constraintViolations.iterator().next().getMessage());	
	}
	
	@Test
	public void noRoomNoStudioTrue() {
		alertForm.setNoRoomNoStudio(true);
		Set<ConstraintViolation<AlertForm>> constraintViolations = validator.validate(alertForm);
		assertEquals(1, constraintViolations.size());
		assertEquals("Please select at least one type", constraintViolations.iterator().next().getMessage());
	}

}

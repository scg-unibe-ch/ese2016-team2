package ch.unibe.ese.team1.test.controller.pojos.form;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.ese.team1.controller.pojos.forms.PlaceAdForm;

public class PlaceAdFormValidationTest {

	private static Validator validator;
	private PlaceAdForm placeAdForm = new PlaceAdForm();

	@Before
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

		placeAdForm.setTitle("Test Title");
		placeAdForm.setStreet("Test streest");
		placeAdForm.setCity("3000 - Bern");
		placeAdForm.setMoveInDate("12-12-2012");
		placeAdForm.setPrize(500);
		placeAdForm.setSquareFootage(50);
		placeAdForm.setRoomDescription("Test description");

		Set<ConstraintViolation<PlaceAdForm>> constraintViolations = validator.validate(placeAdForm);
		assertEquals(0, constraintViolations.size());
	}

	@Test
	public void emptyTitle() {
		placeAdForm.setTitle("");
		Set<ConstraintViolation<PlaceAdForm>> constraintViolations = validator.validate(placeAdForm);
		assertEquals(1, constraintViolations.size());
		assertEquals("Please add a title.", constraintViolations.iterator().next().getMessage());
	}

	@Test
	public void emptyStreet() {
		placeAdForm.setStreet("");
		Set<ConstraintViolation<PlaceAdForm>> constraintViolations = validator.validate(placeAdForm);
		assertEquals(1, constraintViolations.size());
		assertEquals("Please add an address.", constraintViolations.iterator().next().getMessage());
	}

	@Test
	public void emptyCity() {
		placeAdForm.setCity("");
		Set<ConstraintViolation<PlaceAdForm>> constraintViolations = validator.validate(placeAdForm);
		assertEquals(1, constraintViolations.size());
		assertEquals("Please pick a city from the list", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void emptyMoveInDate() {
		placeAdForm.setMoveInDate("");
		Set<ConstraintViolation<PlaceAdForm>> constraintViolations = validator.validate(placeAdForm);
		assertEquals(1, constraintViolations.size());
		assertEquals("Please set a move-in date.", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void prizeToLow() {
		placeAdForm.setPrize(-5);
		Set<ConstraintViolation<PlaceAdForm>> constraintViolations = validator.validate(placeAdForm);
		assertEquals(1, constraintViolations.size());
		assertEquals("Price should be at least one lousy buck and not exceed 2147483647", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void squareFootageToLow() {
		placeAdForm.setSquareFootage(-5);
		Set<ConstraintViolation<PlaceAdForm>> constraintViolations = validator.validate(placeAdForm);
		assertEquals(1, constraintViolations.size());
		assertEquals("Square footage should be at least 1 and not exceed 2147483647", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void emptyRoomDescription() {
		placeAdForm.setRoomDescription("");
		Set<ConstraintViolation<PlaceAdForm>> constraintViolations = validator.validate(placeAdForm);
		assertEquals(1, constraintViolations.size());
		assertEquals("Please add a description.", constraintViolations.iterator().next().getMessage());
	}
}

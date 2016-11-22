package ch.unibe.ese.team1.test.controller.pojos.form;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.ese.team1.controller.pojos.forms.PlaceAuctionForm;

public class PlaceAuctionFormValidationTest {
	
	 private static Validator validator;
	 private PlaceAuctionForm placeAuctionForm = new PlaceAuctionForm();
	 
	 @Before
	 public void setUp() {
		 ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	     validator = factory.getValidator();
	     
	     placeAuctionForm.setTitle("Test Title");
	     placeAuctionForm.setStreet("Test streest");
	     placeAuctionForm.setCity("3000 - Bern");
	     placeAuctionForm.setMoveInDate("12-12-2012");
	     placeAuctionForm.setPrize(500);
	     placeAuctionForm.setSquareFootage(50);
	     placeAuctionForm.setRoomDescription("Test description");
	     placeAuctionForm.setEndDate("21-12-2012");
	     placeAuctionForm.setEndTime("12:12");
	 }
	 
	 @Test
	 public void titleIsEmpty() {
		 placeAuctionForm.setTitle("");
		 Set<ConstraintViolation<PlaceAuctionForm>> constraintViolations = validator.validate(placeAuctionForm);
		 assertEquals(1, constraintViolations.size());
		 assertEquals("Required", constraintViolations.iterator().next().getMessage());
	 }
	 
	 @Test
	 public void streetIsEmpty() {
		 placeAuctionForm.setStreet("");
		 Set<ConstraintViolation<PlaceAuctionForm>> constraintViolations = validator.validate(placeAuctionForm);
		 assertEquals(1, constraintViolations.size());
		 assertEquals("Required", constraintViolations.iterator().next().getMessage());
	 }
	 
	 @Test
	 public void cityIsEmpty() {
		 placeAuctionForm.setCity("");
		 Set<ConstraintViolation<PlaceAuctionForm>> constraintViolations = validator.validate(placeAuctionForm);
		 assertEquals(1, constraintViolations.size());
		 assertEquals("Please pick a city from the list", constraintViolations.iterator().next().getMessage());
	 }
	 
	 @Test
	 public void moveInDateIsEmpty() {
		 placeAuctionForm.setMoveInDate("");
		 Set<ConstraintViolation<PlaceAuctionForm>> constraintViolations = validator.validate(placeAuctionForm);
		 assertEquals(1, constraintViolations.size());
		 assertEquals("Required", constraintViolations.iterator().next().getMessage());
	 }
	 
	 @Test
	 public void prizeIsToSmall() {
		 placeAuctionForm.setPrize(-5);
		 Set<ConstraintViolation<PlaceAuctionForm>> constraintViolations = validator.validate(placeAuctionForm);
		 assertEquals(1, constraintViolations.size());
		 assertEquals("Has to be equal to 1 or more", constraintViolations.iterator().next().getMessage());
	 }
	 
	 @Test
	 public void squareFootageIsToSmall() {
		 placeAuctionForm.setSquareFootage(-5);
		 Set<ConstraintViolation<PlaceAuctionForm>> constraintViolations = validator.validate(placeAuctionForm);
		 assertEquals(1, constraintViolations.size());
		 assertEquals("Has to be equal to 1 or more", constraintViolations.iterator().next().getMessage());
	 }
	 
	 @Test
	 public void roomDescriptionIsEmpty() {
		 placeAuctionForm.setRoomDescription("");
		 Set<ConstraintViolation<PlaceAuctionForm>> constraintViolations = validator.validate(placeAuctionForm);
		 assertEquals(1, constraintViolations.size());
		 assertEquals("Required", constraintViolations.iterator().next().getMessage());
	 }
	 
	 @Test
	 public void endDateIsEmpty() {
		 placeAuctionForm.setEndDate("");
		 Set<ConstraintViolation<PlaceAuctionForm>> constraintViolations = validator.validate(placeAuctionForm);
		 assertEquals(1, constraintViolations.size());
		 assertEquals("Required", constraintViolations.iterator().next().getMessage());
	 }
	 
	 @Test
	 public void endTimeIsEmpty() {
		 placeAuctionForm.setEndTime("");
		 Set<ConstraintViolation<PlaceAuctionForm>> constraintViolations = validator.validate(placeAuctionForm);
		 assertEquals(1, constraintViolations.size());
		 assertEquals("Required, time format is: HH:mm", constraintViolations.iterator().next().getMessage());
	 }
}

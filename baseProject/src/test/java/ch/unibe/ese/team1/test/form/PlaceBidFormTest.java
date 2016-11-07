package ch.unibe.ese.team1.test.form;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.unibe.ese.team1.controller.pojos.forms.PlaceBidForm;

public class PlaceBidFormTest {

	PlaceBidForm placeBidForm = new PlaceBidForm();
	
	@Test
	public void testPrize() {
		int prize = 500;
		placeBidForm.setPrize(prize);
		
		assertEquals(prize, placeBidForm.getPrize());
	}
	
	@Test
	public void testId() {
		long id = 1;
		placeBidForm.setId(id);
		
		assertEquals(id, placeBidForm.getId());
	}
	
}

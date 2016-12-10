package ch.unibe.ese.team1.test.model;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.Visit;
import ch.unibe.ese.team1.model.VisitEnquiry;
import ch.unibe.ese.team1.model.VisitEnquiryState;

public class VisitEnquiryTest {

	VisitEnquiry visitEnquiry = new VisitEnquiry();
	
	@Test
	public void testId() {
		long id = 1;
		visitEnquiry.setId(id);
		
		assertEquals(id, visitEnquiry.getId());
	}
	
	@Test
	public void testSender() {
		User sender = new User();
		visitEnquiry.setSender(sender);
		
		assertEquals(sender, visitEnquiry.getSender());
	}
	
	@Test
	public void testDateSent() {
		Date dateSent = new Date();
		visitEnquiry.setDateSent(dateSent);
		
		assertEquals(dateSent, visitEnquiry.getDateSent());
	}
	
	@Test
	public void testVisitEnquiryState() {
		VisitEnquiryState visitEnquiryState = VisitEnquiryState.OPEN;
		visitEnquiry.setState(visitEnquiryState);
		
		assertEquals(VisitEnquiryState.OPEN, visitEnquiry.getState());
		
		visitEnquiryState = VisitEnquiryState.DECLINED;
		visitEnquiry.setState(visitEnquiryState);
		
		assertEquals(VisitEnquiryState.DECLINED, visitEnquiry.getState());
		
		visitEnquiryState = VisitEnquiryState.ACCEPTED;
		visitEnquiry.setState(visitEnquiryState);
		
		assertEquals(VisitEnquiryState.ACCEPTED, visitEnquiry.getState());
	}
	
	@Test
	public void testVisit() {
		Visit visit = new Visit();
		visitEnquiry.setVisit(visit);
		
		assertEquals(visit, visitEnquiry.getVisit());
	}
}

package ch.unibe.ese.team1.test.model;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import ch.unibe.ese.team1.model.Message;
import ch.unibe.ese.team1.model.MessageState;
import ch.unibe.ese.team1.model.User;

public class MessageTest {
	
	Message message = new Message();
	
	@Test
	public void testId() {
		long id = 1;
		message.setId(id);
		
		assertEquals(id, message.getId());
	}
	
	@Test
	public void testMessageState() {
		MessageState state = MessageState.UNREAD;
		message.setState(state);
		
		assertEquals(state, message.getState());
	}
	
	@Test
	public void testSubjectAndText() {
		String text = "Test Text";
		message.setSubject(text);
		message.setText(text);
		
		assertEquals(text, message.getSubject());
		assertEquals(text, message.getText());
	}

	@Test
	public void testDate() {
		Date dateSent = new Date();
		message.setDateSent(dateSent);
		
		assertEquals(dateSent, message.getDateSent());
	}
	
	@Test
	public void testUsers() {
		User user = new User();
		message.setSender(user);
		message.setRecipient(user);
		
		assertEquals(user, message.getSender());
		assertEquals(user, message.getRecipient());
	}
}

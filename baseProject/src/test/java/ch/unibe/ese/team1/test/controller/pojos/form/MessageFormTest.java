package ch.unibe.ese.team1.test.controller.pojos.form;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.unibe.ese.team1.controller.pojos.forms.MessageForm;

public class MessageFormTest {

	MessageForm messageForm = new MessageForm();
	
	@Test
	public void testRecipient() {
		String recipient = "Hans Mueller";
		messageForm.setRecipient(recipient);
	
		assertEquals(recipient, messageForm.getRecipient());
	}
	
	@Test
	public void testSubject() {
		String subject = "Test";
		messageForm.setSubject(subject);
	
		assertEquals(subject, messageForm.getSubject());
	}
	
	@Test
	public void testText() {
		String text = "Test text.";
		messageForm.setText(text);
	
		assertEquals(text, messageForm.getText());
	}
}

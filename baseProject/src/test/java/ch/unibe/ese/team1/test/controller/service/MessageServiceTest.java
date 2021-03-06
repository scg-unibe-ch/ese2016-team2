package ch.unibe.ese.team1.test.controller.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ch.unibe.ese.team1.controller.service.MessageService;
import ch.unibe.ese.team1.model.Gender;
import ch.unibe.ese.team1.model.Message;
import ch.unibe.ese.team1.model.MessageState;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.UserRole;
import ch.unibe.ese.team1.model.dao.MessageDao;
import ch.unibe.ese.team1.model.dao.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"file:src/main/webapp/WEB-INF/config/springMVC.xml",
		"file:src/main/webapp/WEB-INF/config/springData.xml",
		"file:src/main/webapp/WEB-INF/config/springSecurity.xml" })
@WebAppConfiguration
public class MessageServiceTest {
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired 
	private UserDao userDao;
	
	private User receiver = new User();
	private User sender = new User();
	
	@Test
	public void testSaveMessageAndReading() {
		receiver = createUser("receiver@Test1.ch", "password", "Receiver", "Test1", Gender.MALE, "Normal");
		receiver.setAboutMe("ReceiverTest1");
		userDao.save(receiver);
		sender = createUser("sender@Test1.ch", "password", "Sender", "Test1", Gender.MALE, "Normal");
		sender.setAboutMe("SenderTest1");
		userDao.save(sender);
		
		long receiverId = userDao.findByUsername(receiver.getUsername()).getId();
		
		String subject = "Test subject";
		String text = "Test text";
		messageService.sendMessage(sender, receiver, subject, text);
		String subject2 = "Test subject2";
		String text2 = "Test text2";
		messageService.sendMessage(sender, receiver, subject2, text2);
		
		Iterable<Message> messages = messageDao.findAll();
		List<Message> message = new ArrayList<Message>();
		for (Message tempMessage: messages) {
			message.add(tempMessage);
		}
		
		int size = message.size();
		
		long id1 = message.get(size-2).getId();
		long id2 = message.get(size-1).getId();
		
		assertEquals(subject, message.get(size-2).getSubject());
		assertEquals(receiver.getUsername(), userDao.findOne(message.get(size-2).getRecipient().getId()).getUsername());
		assertEquals(sender.getUsername(), userDao.findOne(message.get(size-2).getSender().getId()).getUsername());
		assertEquals(text, message.get(size-2).getText());
		assertEquals(MessageState.UNREAD, message.get(size-2).getState());
		assertEquals(2, messageService.unread(receiverId));
		assertEquals(message.get(size-2).getId(), messageService.getMessage(id1).getId());
		assertEquals(message.get(size-1).getId(), messageService.getMessage(id2).getId());
		
		messageService.readMessage(message.get(size-2).getId());
		
		Message message1 = messageService.getMessage(id1);
		
		assertEquals(MessageState.READ, message1.getState());
		assertEquals(1, messageService.unread(receiverId));
		
		Iterable<Message> inboxOfUser = messageService.getInboxForUser(receiver);
		ArrayList<Message> messagesOfInbox = new ArrayList<Message>();
		for (Message tempMessage: inboxOfUser) {
			messagesOfInbox.add(tempMessage);
		}
		
		assertEquals(messagesOfInbox.get(0).getId(), message.get(size-2).getId());
		
		Iterable<Message> messagesSentOfSender = messageService.getSentForUser(sender);
		ArrayList<Message> messagesSentOfSenderList = new ArrayList<Message>();
		for (Message tempMessage: messagesSentOfSender) {
			messagesSentOfSenderList.add(tempMessage);
		}
		
		assertEquals(messagesSentOfSenderList.get(0).getId(), message.get(size-2).getId());		
	}
	
	private User createUser(String email, String password, String firstName, String lastName, Gender gender,
			String account) {
		User user = new User();
		user.setUsername(email);
		user.setPassword(password);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEnabled(true);
		user.setGender(gender);
		user.setAccount(account);
		Set<UserRole> userRoles = new HashSet<>();
		UserRole role = new UserRole();
		role.setRole("ROLE_USER");
		role.setUser(user);
		userRoles.add(role);
		user.setUserRoles(userRoles);
		return user;
	}

}

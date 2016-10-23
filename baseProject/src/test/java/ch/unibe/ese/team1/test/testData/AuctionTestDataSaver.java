package ch.unibe.ese.team1.test.testData;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.dao.AuctionDao;
import ch.unibe.ese.team1.model.dao.UserDao;

@Service
public class AuctionTestDataSaver {

	@Autowired
	private AuctionDao auctionDao;
	@Autowired
	private UserDao userDao;
	
	@Transactional
	public void saveTestData() throws Exception {
		User bernerBaer = userDao.findByUsername("user@bern.com");
		User ese = userDao.findByUsername("ese@unibe.ch");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		
		Date creationDate1 = formatter.parse("03.10.2015");
		Date creationDate2 = formatter.parse("30.11.2014");
		
		Date moveInDate1 = formatter.parse("15.12.2017");
		Date moveInDate2 = formatter.parse("21.12.2017");
		
		Date moveOutDate1 = formatter.parse("31.03.2018");
		Date moveOutDate2 = formatter.parse("30.04.2018");
		
		SimpleDateFormat timeFormatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
	
		Date endTime1 = timeFormatter.parse("14.12.2017, 12:00:00");
		Date endTime2 = timeFormatter.parse("20.12.2017, 12:00:00");
		
		String roomDescription1 = "The room is a part of 3.5 rooms apartment completely renovated"
				+ "in 2010 at Kramgasse, Bern. The apartment is about 50 m2 on 1st floor."
				+ "Apt is equipped modern kitchen, hall and balcony. Near to shops and public"
				+ "transportation. Monthly rent is 500 CHF including charges. Internet + TV + landline"
				+ "charges are separate. If you are interested, feel free to drop me a message"
				+ "to have an appointment for a visit or can write me for any further information";
		String preferences1 = "Uncomplicated, open minded and easy going person (m / w),"
				+ "non-smoker, can speak English, which of course fits in the WG, and who likes dogs."
				+ "Cleanliness is must. Apart from personal life, sometimes glass of wine,"
				+ "eat and cook together and go out in the evenings.";

		Auction adBern = new Auction();
		adBern.setZipcode(3011);
		adBern.setMoveInDate(moveInDate1);
		adBern.setCreationDate(creationDate1);
		adBern.setMoveOutDate(moveOutDate1);
		adBern.setPrize(400);
		adBern.setSquareFootage(50);
		adBern.setRoomType("Room");
		adBern.setStudio(false);
		adBern.setSmokers(false);
		adBern.setAnimals(true);
		adBern.setRoomDescription(roomDescription1);
		adBern.setPreferences(preferences1);
		adBern.setRoommates("One roommate");
		adBern.setUser(ese);
		adBern.setTitle("Roommate wanted in Bern");
		adBern.setStreet("Kramgasse 22");
		adBern.setCity("Bern");
		adBern.setGarden(true);
		adBern.setBalcony(true);
		adBern.setCellar(true);
		adBern.setFurnished(true);
		adBern.setCable(true);
		adBern.setGarage(true);
		adBern.setInternet(true);
		adBern.setAuction(true);
		adBern.setEndTime(endTime1);
		auctionDao.save(adBern);

		String studioDescription2 = "It is small studio close to the"
				+ "university and the bahnhof. The lovely neighbourhood"
				+ "Langgasse makes it an easy place to feel comfortable."
				+ "The studio is close to a Migross, Denner and the Coop."
				+ "The studio is 60m2. It has it own Badroom and kitchen."
				+ "Nothing is shared. The studio is fully furnished. The"
				+ "studio is also provided with a balcony. So if you want to"
				+ "have a privat space this could totally be good place for you."
				+ "Be aware it is only till the end of March. The price is from"
				+ "550- 700 CHF, But there is always room to talk about it.";
		String roomPreferences2 = "I would like to have an easy going person who"
				+ "is trustworthy and can take care of the flat. No animals please."
				+ "Non smoker preferred.";
		
		Auction adBern2 = new Auction();
		adBern2.setZipcode(3012);
		adBern2.setMoveInDate(moveInDate2);
		adBern2.setCreationDate(creationDate2);
		adBern2.setMoveOutDate(moveOutDate2);
		adBern2.setPrize(700);
		adBern2.setSquareFootage(60);
		adBern2.setRoomType("Studio");
		adBern2.setStudio(true);
		adBern2.setSmokers(false);
		adBern2.setAnimals(true);
		adBern2.setRoomDescription(studioDescription2);
		adBern2.setPreferences(roomPreferences2);
		adBern2.setRoommates("None");
		adBern2.setUser(bernerBaer);
		adBern2.setTitle("Cheap studio in Bern!");
		adBern2.setStreet("Längassstr. 40");
		adBern2.setCity("Bern");
		adBern2.setGarden(false);
		adBern2.setBalcony(false);
		adBern2.setCellar(false);
		adBern2.setFurnished(false);
		adBern2.setCable(false);
		adBern2.setGarage(false);
		adBern2.setInternet(true);
		adBern2.setAuction(true);
		adBern2.setEndTime(endTime2);
		auctionDao.save(adBern2);
	}
}

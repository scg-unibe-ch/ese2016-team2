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
		
		SimpleDateFormat timeFormatter = new SimpleDateFormat("dd.MM.yyyy");
	
		Date endTime1 = timeFormatter.parse("14.12.2017");
		Date endTime2 = timeFormatter.parse("20.12.2017");
		
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

		Auction auctionBern = new Auction();
		auctionBern.setZipcode(3011);
		auctionBern.setMoveInDate(moveInDate1);
		auctionBern.setCreationDate(creationDate1);
		auctionBern.setMoveOutDate(moveOutDate1);
		auctionBern.setPrize(400);
		auctionBern.setSquareFootage(50);
		auctionBern.setRoomType("Room");
		auctionBern.setStudio(false);
		auctionBern.setSmokers(false);
		auctionBern.setAnimals(true);
		auctionBern.setBuyable(true);
		auctionBern.setRoomDescription(roomDescription1);
		auctionBern.setPreferences(preferences1);
		auctionBern.setUser(ese);
		auctionBern.setTitle("Roommate wanted in Bern");
		auctionBern.setStreet("Kramgasse 22");
		auctionBern.setCity("Bern");
		auctionBern.setGarden(true);
		auctionBern.setBalcony(true);
		auctionBern.setCellar(true);
		auctionBern.setFurnished(true);
		auctionBern.setCable(true);
		auctionBern.setGarage(true);
		auctionBern.setInternet(true);
		auctionBern.setAuction(true);
		auctionBern.setEndTime(endTime1);
		auctionDao.save(auctionBern);

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
		
		Auction auctionBern2 = new Auction();
		auctionBern2.setZipcode(3012);
		auctionBern2.setMoveInDate(moveInDate2);
		auctionBern2.setCreationDate(creationDate2);
		auctionBern2.setMoveOutDate(moveOutDate2);
		auctionBern2.setPrize(700);
		auctionBern2.setSquareFootage(60);
		auctionBern2.setRoomType("Studio");
		auctionBern2.setStudio(true);
		auctionBern2.setSmokers(false);
		auctionBern2.setAnimals(true);
		auctionBern2.setBuyable(true);
		auctionBern2.setRoomDescription(studioDescription2);
		auctionBern2.setPreferences(roomPreferences2);
		auctionBern2.setUser(bernerBaer);
		auctionBern2.setTitle("Cheap studio in Bern!");
		auctionBern2.setStreet("Längassstr. 40");
		auctionBern2.setCity("Bern");
		auctionBern2.setGarden(false);
		auctionBern2.setBalcony(false);
		auctionBern2.setCellar(false);
		auctionBern2.setFurnished(false);
		auctionBern2.setCable(false);
		auctionBern2.setGarage(false);
		auctionBern2.setInternet(true);
		auctionBern2.setAuction(true);
		auctionBern2.setEndTime(endTime2);
		auctionDao.save(auctionBern2);
	}
}

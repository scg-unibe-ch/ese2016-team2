package ch.unibe.ese.team1.test.testData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.AuctionPicture;
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

		Date creationDate1 = formatter.parse("03.10.2016");
		Date creationDate2 = formatter.parse("30.11.2015");

		Date moveInDate1 = formatter.parse("15.12.2017");
		Date moveInDate2 = formatter.parse("21.12.2017");

		Date moveOutDate1 = formatter.parse("31.03.2018");
		Date moveOutDate2 = formatter.parse("30.04.2018");

		SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm, dd.MM.yyyy");

		Date endTime1 = timeFormatter.parse("11:39, 25.12.2016");
		Date endTime2 = timeFormatter.parse("12:00, 20.12.2017");

		String houseDescription1 = "The house is completely furnished. It has a huge cellar,"
				+ "a garden about 300 m2 and 3 balconies. The house itself is about 200 m2."
				+ "If you are interested, feel free to drop me a message"
				+ "to have an appointment for a visit or can write me for any further information";
		String preferences1 = "Uncomplicated, open minded and easy going person (m / w),"
				+ "non-smoker, can speak English, which can pay 20% of the prize as a down payment.";

		Auction auctionKerzers = new Auction();
		auctionKerzers.setZipcode(3210);
		auctionKerzers.setMoveInDate(moveInDate1);
		auctionKerzers.setCreationDate(creationDate1);
		auctionKerzers.setMoveOutDate(moveOutDate1);
		auctionKerzers.setPrize(500000);
		auctionKerzers.setSquareFootage(200);
		auctionKerzers.setRoomType("House");
		auctionKerzers.setSmokers(false);
		auctionKerzers.setAnimals(true);
		auctionKerzers.setBuyable(true);
		auctionKerzers.setRoomDescription(houseDescription1);
		auctionKerzers.setPreferences(preferences1);
		auctionKerzers.setUser(ese);
		auctionKerzers.setTitle("House for sale");
		auctionKerzers.setStreet("Mühlegasse 53");
		auctionKerzers.setCity("Kerzers");
		auctionKerzers.setLatitude("46.9662051");
		auctionKerzers.setLongitude("7.1958472999999685");
		auctionKerzers.setGarden(true);
		auctionKerzers.setBalcony(true);
		auctionKerzers.setCellar(true);
		auctionKerzers.setFurnished(true);
		auctionKerzers.setCable(true);
		auctionKerzers.setGarage(true);
		auctionKerzers.setInternet(true);
		List<AuctionPicture> pictures = new ArrayList<>();
		pictures.add(createPicture(auctionKerzers, "/img/test/ad1_1.jpg"));
		pictures.add(createPicture(auctionKerzers, "/img/test/ad1_2.jpg"));
		pictures.add(createPicture(auctionKerzers, "/img/test/ad1_3.jpg"));
		auctionKerzers.setPictures(pictures);
		auctionKerzers.setAuction(true);
		auctionKerzers.setEndTime(timeFormatter.format(endTime1));
		auctionDao.save(auctionKerzers);

		String studioDescription2 = "The house is completely furnished"
				+ "The house itself is about 165 m2. Internet included."
				+ "If you are interested, feel free to drop me a message"
				+ "to have an appointment for a visit or can write me for any further information";
		String preferences2 = "Uncomplicated, open minded and easy going person (m / w), with a family"
				+ "non-smoker, which can pay 10% of the prize as a down payment.";

		Auction auctionBern2 = new Auction();
		auctionBern2.setZipcode(3011);
		auctionBern2.setMoveInDate(moveInDate2);
		auctionBern2.setCreationDate(creationDate2);
		auctionBern2.setMoveOutDate(moveOutDate2);
		auctionBern2.setPrize(120000);
		auctionBern2.setSquareFootage(165);
		auctionBern2.setRoomType("Studio");
		auctionBern2.setSmokers(false);
		auctionBern2.setAnimals(true);
		auctionBern2.setBuyable(true);
		auctionBern2.setRoomDescription(studioDescription2);
		auctionBern2.setPreferences(preferences2);
		auctionBern2.setUser(bernerBaer);
		auctionBern2.setTitle("Studio for sale in Bern!");
		auctionBern2.setStreet("Zeughausgasse 22");
		auctionBern2.setCity("Bern");
		auctionBern2.setLatitude("46.9491937");
		auctionBern2.setLongitude("7.445016399999986");
		auctionBern2.setGarden(false);
		auctionBern2.setBalcony(false);
		auctionBern2.setCellar(false);
		auctionBern2.setFurnished(false);
		auctionBern2.setCable(false);
		auctionBern2.setGarage(false);
		auctionBern2.setInternet(true);
		List<AuctionPicture> picturesBern2 = new ArrayList<>();
		picturesBern2.add(createPicture(auctionBern2, "/img/test/ad7_1.jpg"));
		picturesBern2.add(createPicture(auctionBern2, "/img/test/ad7_2.jpg"));
		picturesBern2.add(createPicture(auctionBern2, "/img/test/ad7_3.jpg"));
		auctionBern2.setPictures(picturesBern2);
		auctionBern2.setAuction(true);
		auctionBern2.setEndTime(timeFormatter.format(endTime2));
		auctionDao.save(auctionBern2);
	}

	private AuctionPicture createPicture(Auction auction, String filePath) {
		AuctionPicture picture = new AuctionPicture();
		picture.setFilePath(filePath);
		return picture;
	}
}

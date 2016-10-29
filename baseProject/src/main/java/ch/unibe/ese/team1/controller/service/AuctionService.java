package ch.unibe.ese.team1.controller.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.unibe.ese.team1.controller.pojos.forms.PlaceAuctionForm;
import ch.unibe.ese.team1.controller.pojos.forms.PlaceBidForm;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.AdPicture;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.Visit;
import ch.unibe.ese.team1.model.dao.AlertDao;
import ch.unibe.ese.team1.model.dao.AuctionDao;
import ch.unibe.ese.team1.model.dao.MessageDao;
import ch.unibe.ese.team1.model.dao.UserDao;

@Service
public class AuctionService {

	@Autowired
	private AuctionDao auctionDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private AlertDao alertDao;

	@Autowired
	private MessageDao messageDao;

	@Autowired
	private UserService userService;

	@Autowired
	private GeoDataService geoDataService;

	/**
	 * Handles persisting a new auction to the database.
	 * 
	 * @param placeAuctionForm
	 *            the form to take the data from
	 * @param the
	 *            currently logged in user
	 */
	@Transactional
	public Auction saveFrom(PlaceAuctionForm placeAuctionForm, User user) {
		
		Auction auction = new Auction();

		Date now = new Date();
		auction.setCreationDate(now);

		auction.setTitle(placeAuctionForm.getTitle());

		auction.setStreet(placeAuctionForm.getStreet());

		auction.setStudio(placeAuctionForm.getStudio());
		
		auction.setRoomType(placeAuctionForm.getRoomType());

		// take the zipcode - first four digits
		String zip = placeAuctionForm.getCity().substring(0, 4);
		auction.setZipcode(Integer.parseInt(zip));
		auction.setCity(placeAuctionForm.getCity().substring(7));
		
		Calendar calendar = Calendar.getInstance();
		// java.util.Calendar uses a month range of 0-11 instead of the
		// XMLGregorianCalendar which uses 1-12
		try {
			if (placeAuctionForm.getMoveInDate().length() >= 1) {
				int dayMoveIn = Integer.parseInt(placeAuctionForm.getMoveInDate()
						.substring(0, 2));
				int monthMoveIn = Integer.parseInt(placeAuctionForm.getMoveInDate()
						.substring(3, 5));
				int yearMoveIn = Integer.parseInt(placeAuctionForm.getMoveInDate()
						.substring(6, 10));
				calendar.set(yearMoveIn, monthMoveIn - 1, dayMoveIn);
				auction.setMoveInDate(calendar.getTime());
			}
			if (placeAuctionForm.getEndTime().length() >= 1) {
				int dayMoveIn = Integer.parseInt(placeAuctionForm.getEndTime()
						.substring(0, 2));
				int monthMoveIn = Integer.parseInt(placeAuctionForm.getEndTime()
						.substring(3, 5));
				int yearMoveIn = Integer.parseInt(placeAuctionForm.getEndTime()
						.substring(6, 10));
				calendar.set(yearMoveIn, monthMoveIn - 1, dayMoveIn);
				auction.setEndTime(calendar.getTime());
			}
		} catch (NumberFormatException e) {
		}

		auction.setPrize(placeAuctionForm.getPrize());
		auction.setSquareFootage(placeAuctionForm.getSquareFootage());

		auction.setRoomDescription(placeAuctionForm.getRoomDescription());
		auction.setPreferences(placeAuctionForm.getPreferences());

		// auction description values
		auction.setSmokers(placeAuctionForm.isSmokers());
		auction.setAnimals(placeAuctionForm.isAnimals());
		auction.setGarden(placeAuctionForm.getGarden());
		auction.setBalcony(placeAuctionForm.getBalcony());
		auction.setCellar(placeAuctionForm.getCellar());
		auction.setFurnished(placeAuctionForm.isFurnished());
		auction.setCable(placeAuctionForm.getCable());
		auction.setGarage(placeAuctionForm.getGarage());
		auction.setInternet(placeAuctionForm.getInternet());
		
		auction.setAuction(true);
		auction.setBuyable(true);
		auction.setUser(user);
		
		auctionDao.save(auction);

		return auction;
	}
	
	@Transactional
	public Auction saveBidPrize(PlaceBidForm placeBidForm, long id, String bidderName) {
		Auction auction = getAuctionById(id);
		auction.setBidderName(bidderName);
		auction.setPrize(placeBidForm.getPrize());
		
		auctionDao.save(auction);
		
		return auction;
	}

	/**
	 * Gets the auction that has the given id.
	 * 
	 * @param id
	 *            the id that should be searched for
	 * @return the found ad or null, if no ad with this id exists
	 */
	@Transactional
	public Auction getAuctionById(long id) {
		return auctionDao.findOne(id);
	}

	/** Returns all auctions in the database */
	@Transactional
	public Iterable<Auction> getAllAds() {
		return auctionDao.findAll();
	}

	/**
	 * Returns the newest auctions in the database. Parameter 'newest' says how many.
	 */
	@Transactional
	public Iterable<Auction> getNewestAds(int newest) {
		Iterable<Auction> allAuctions = auctionDao.findAll();
		List<Auction> auctions = new ArrayList<Auction>();
		for (Auction auction : allAuctions)
			auctions.add(auction);
		Collections.sort(auctions, new Comparator<Auction>() {
			@Override
			public int compare(Auction auction1, Auction auction2) {
				return auction2.getCreationDate().compareTo(auction1.getCreationDate());
			}
		});
		List<Auction> fourNewest = new ArrayList<Auction>();
		for (int i = 0; i < newest; i++)
			fourNewest.add(auctions.get(i));
		return fourNewest;
	}
	
	/** Returns all ads that were placed by the given user. */
	public Iterable<Auction> getAuctionsByUser(User user) {
		return auctionDao.findByUser(user);
	}
	
	/**
	 * Checks if the email of a user is already contained in the given string.
	 * 
	 * @param email
	 *            the email string to search for
	 * @param alreadyAdded
	 *            the string of already added emails, which should be searched
	 *            in
	 * 
	 * @return true if the email has been added already, false otherwise
	 */
	public Boolean checkIfAlreadyAdded(String email, String alreadyAdded) {
		email = email.toLowerCase();
		alreadyAdded = alreadyAdded.replaceAll("\\s+", "").toLowerCase();
		String delimiter = "[:;]+";
		String[] toBeTested = alreadyAdded.split(delimiter);
		for (int i = 0; i < toBeTested.length; i++) {
			if (email.equals(toBeTested[i])) {
				return true;
			}
		}
		return false;
	}
}

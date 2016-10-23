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
	 * @param a
	 *            list of the file paths the pictures are saved under
	 * @param the
	 *            currently logged in user
	 */
	@Transactional
	public Auction saveFrom(PlaceAuctionForm placeAuctionForm, List<String> filePaths,
			User user) {
		
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

			if (placeAuctionForm.getMoveOutDate().length() >= 1) {
				int dayMoveOut = Integer.parseInt(placeAuctionForm.getMoveOutDate()
						.substring(0, 2));
				int monthMoveOut = Integer.parseInt(placeAuctionForm
						.getMoveOutDate().substring(3, 5));
				int yearMoveOut = Integer.parseInt(placeAuctionForm.getMoveOutDate()
						.substring(6, 10));
				calendar.set(yearMoveOut, monthMoveOut - 1, dayMoveOut);
				auction.setMoveOutDate(calendar.getTime());
			}
		} catch (NumberFormatException e) {
		}

		auction.setPrize(placeAuctionForm.getPrize());
		auction.setSquareFootage(placeAuctionForm.getSquareFootage());

		auction.setRoomDescription(placeAuctionForm.getRoomDescription());
		auction.setPreferences(placeAuctionForm.getPreferences());
		auction.setRoommates(placeAuctionForm.getRoommates());

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
		
		/*
		 * Save the paths to the picture files, the pictures are assumed to be
		 * uploaded at this point!
		 */
		List<AdPicture> pictures = new ArrayList<>();
		for (String filePath : filePaths) {
			AdPicture picture = new AdPicture();
			picture.setFilePath(filePath);
			pictures.add(picture);
		}
		auction.setPictures(pictures);

		/*
		 * Roommates are saved in the form as strings. They need to be converted
		 * into Users and saved as a List which will be accessible through the
		 * auction object itself.
		 */
		List<User> registeredUserRommates = new LinkedList<>();
		if (placeAuctionForm.getRegisteredRoommateEmails() != null) {
			for (String userEmail : placeAuctionForm.getRegisteredRoommateEmails()) {
				User roommateUser = userService.findUserByUsername(userEmail);
				registeredUserRommates.add(roommateUser);
			}
		}
		auction.setRegisteredRoommates(registeredUserRommates);

		// visits
		List<Visit> visits = new LinkedList<>();
		List<String> visitStrings = placeAuctionForm.getVisits();
		if (visitStrings != null) {
			for (String visitString : visitStrings) {
				Visit visit = new Visit();
				// format is 28-02-2014;10:02;13:14
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
				String[] parts = visitString.split(";");
				String startTime = parts[0] + " " + parts[1];
				String endTime = parts[0] + " " + parts[2];
				Date startDate = null;
				Date endDate = null;
				try {
					startDate = dateFormat.parse(startTime);
					endDate = dateFormat.parse(endTime);
				} catch (ParseException ex) {
					ex.printStackTrace();
				}

				visit.setStartTimestamp(startDate);
				visit.setEndTimestamp(endDate);
				visit.setAd(auction);
				visits.add(visit);
			}
			auction.setVisits(visits);
			
			auction.setAuction(true);
			auction.setEndTime(placeAuctionForm.getEndTime());
		}

		auction.setUser(user);
		
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
	public Auction getAdById(long id) {
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
}

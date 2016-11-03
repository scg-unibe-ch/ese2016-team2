package ch.unibe.ese.team1.controller.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.unibe.ese.team1.controller.pojos.forms.PlaceAdForm;
import ch.unibe.ese.team1.controller.pojos.forms.PlaceAuctionForm;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.AdPicture;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.AuctionPicture;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.Visit;
import ch.unibe.ese.team1.model.dao.AdDao;
import ch.unibe.ese.team1.model.dao.AdPictureDao;
import ch.unibe.ese.team1.model.dao.AuctionDao;

@Service
public class EditAuctionService {

	@Autowired
	private AuctionService auctionService;

	@Autowired
	private AuctionDao auctionDao;

	@Autowired
	private AdPictureDao adPictureDao;

	@Autowired
	private UserService userService;

	/**
	 * Handles persisting an edited auction to the database.
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
			User user, long auctionId) {

		Auction auction = auctionService.getAuctionById(auctionId);

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
			//dd.MM.yyyy - endDate, HH:mm - endTime
			if (placeAuctionForm.getEndDate().length() >= 1 && placeAuctionForm.getEndTime().length() >= 1) {
				int dayAuctionEnd = Integer.parseInt(placeAuctionForm.getEndDate()
						.substring(0, 2));
				int monthAuctionEnd = Integer.parseInt(placeAuctionForm.getEndDate()
						.substring(3, 5));
				int yearAuctionEnd = Integer.parseInt(placeAuctionForm.getEndDate()
						.substring(6, 10));
				int hourAuctionEnd = Integer.parseInt(placeAuctionForm.getEndTime()
						.substring(0, 2));
				int minAuctionEnd = Integer.parseInt(placeAuctionForm.getEndTime()
						.substring(3, 5));
				calendar.set(yearAuctionEnd, monthAuctionEnd - 1, dayAuctionEnd, hourAuctionEnd, minAuctionEnd);
				auction.setEndTime(new SimpleDateFormat("HH:mm, dd.MM.yyyy").format(calendar.getTime()));
			}
		} catch (NumberFormatException e) {
		}

		auction.setPrize(placeAuctionForm.getPrize());
		auction.setSquareFootage(placeAuctionForm.getSquareFootage());

		auction.setRoomDescription(placeAuctionForm.getRoomDescription());
		auction.setPreferences(placeAuctionForm.getPreferences());

		// ad description values
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
		List<AuctionPicture> pictures = new ArrayList<>();
		for (String filePath : filePaths) {
			AuctionPicture picture = new AuctionPicture();
			picture.setFilePath(filePath);
			pictures.add(picture);
		}
		// add existing pictures
		for (AuctionPicture picture : auction.getPictures()) {
			pictures.add(picture);
		}
		auction.setPictures(pictures);

		/*// visits
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

			// add existing visit
			for (Visit visit : auction.getVisits()) {
				visits.add(visit);
			}
			auction.setVisits(visits);
		} */

		auction.setUser(user);

		auctionDao.save(auction);

		return auction;
	}

	/**
	 * Removes the picture with the given id from the list of pictures in the auction
	 * with the given id.
	 */
	@Transactional
	public void deletePictureFromAuction(long auctionId, long pictureId) {
		Auction auction = auctionService.getAuctionById(auctionId);
		List<AuctionPicture> pictures = auction.getPictures();
		AdPicture picture = adPictureDao.findOne(pictureId);
		pictures.remove(picture);
		auction.setPictures(pictures);
		auctionDao.save(auction);
	}

	/**
	 * Fills a Form with the data of an auction.
	 */
	public PlaceAuctionForm fillForm(Auction auction) {
		PlaceAuctionForm auctionForm = new PlaceAuctionForm();

		auctionForm.setRoomDescription(auction.getRoomDescription());
		auctionForm.setPreferences(auction.getPreferences());

		return auctionForm;
	}
	
}

package ch.unibe.ese.team1.controller.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.unibe.ese.team1.controller.pojos.forms.PlaceAuctionForm;
import ch.unibe.ese.team1.controller.pojos.forms.PlaceBidForm;
import ch.unibe.ese.team1.controller.pojos.forms.SearchForm;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.AuctionPicture;
import ch.unibe.ese.team1.model.Location;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.Visit;
import ch.unibe.ese.team1.model.dao.AuctionDao;

@Service
public class AuctionService {

	@Autowired
	private AuctionDao auctionDao;

	@Autowired
	private GeoDataService geoDataService;
	
	@Autowired
	private VisitService visitService;
	
	@Autowired
	BookmarkService bookmarkService;

	/**
	 * Handles persisting a new auction to the database.
	 * 
	 * @param placeAuctionForm
	 *            the form to take the data from
	 * @param the
	 *            currently logged in user
	 */
	@Transactional
	public Auction saveFrom(PlaceAuctionForm placeAuctionForm, List<String> filePaths, User user) {

		Auction auction = new Auction();

		Date now = new Date();
		auction.setCreationDate(now);

		auction.setTitle(placeAuctionForm.getTitle());

		auction.setStreet(placeAuctionForm.getStreet());

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
				int dayMoveIn = Integer.parseInt(placeAuctionForm.getMoveInDate().substring(0, 2));
				int monthMoveIn = Integer.parseInt(placeAuctionForm.getMoveInDate().substring(3, 5));
				int yearMoveIn = Integer.parseInt(placeAuctionForm.getMoveInDate().substring(6, 10));
				calendar.set(yearMoveIn, monthMoveIn - 1, dayMoveIn);
				auction.setMoveInDate(calendar.getTime());
			}
			// dd.MM.yyyy - endDate, HH:mm - endTime
			if (placeAuctionForm.getEndDate().length() >= 1 && placeAuctionForm.getEndTime().length() >= 1) {
				int dayAuctionEnd = Integer.parseInt(placeAuctionForm.getEndDate().substring(0, 2));
				int monthAuctionEnd = Integer.parseInt(placeAuctionForm.getEndDate().substring(3, 5));
				int yearAuctionEnd = Integer.parseInt(placeAuctionForm.getEndDate().substring(6, 10));
				int hourAuctionEnd = Integer.parseInt(placeAuctionForm.getEndTime().substring(0, 2));
				int minAuctionEnd = Integer.parseInt(placeAuctionForm.getEndTime().substring(3, 5));
				calendar.set(yearAuctionEnd, monthAuctionEnd - 1, dayAuctionEnd, hourAuctionEnd, minAuctionEnd);
				auction.setEndTime(new SimpleDateFormat("HH:mm, dd.MM.yyyy").format(calendar.getTime()));
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

		List<AuctionPicture> pictures = new ArrayList<>();
		for (String filePath : filePaths) {
			AuctionPicture picture = new AuctionPicture();
			picture.setFilePath(filePath);
			pictures.add(picture);
		}
		auction.setPictures(pictures);

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
				visit.setAuction(auction);
				visits.add(visit);
			}
			auction.setVisits(visits);
		}

		auction.setAuction(true);
		auction.setBuyable(true);
		auction.setUser(user);
		auction.setAuctionEnded(false);

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

	/** Returns all ads that were placed by the given user. */
	public Iterable<Auction> getAuctionsByUser(User user) {
		return auctionDao.findByUser(user);
	}

	/**
	 * Returns all ads that match the parameters given by the form. This list
	 * can possibly be empty.
	 * 
	 * @param searchForm
	 *            the form to take the search parameters from
	 * @return an Iterable of all search results
	 */
	@Transactional
	public Iterable<Auction> queryResults(SearchForm searchForm, boolean premium) {
		Iterable<Auction> results = null;
		// we use this method if we are looking for rooms AND studios AND houses
		if (searchForm.getRoom() && searchForm.getStudio() && searchForm.getHouse()) {
			results = auctionDao.findByPrizeLessThan(searchForm.getPrize() + 1);
		} else if (searchForm.getRoom() && searchForm.getHouse()) {
			List<Auction> temp = auctionDao.findByRoomTypeAndPrizeLessThan("Room", searchForm.getPrize() + 1);
			temp.addAll(auctionDao.findByRoomTypeAndPrizeLessThan("House", searchForm.getPrize() + 1));
			results = temp;
		} else if (searchForm.getRoom() && searchForm.getStudio()) {
			List<Auction> temp = auctionDao.findByRoomTypeAndPrizeLessThan("Room", searchForm.getPrize() + 1);
			temp.addAll(auctionDao.findByRoomTypeAndPrizeLessThan("Studio", searchForm.getPrize() + 1));
			results = temp;
		} else if (searchForm.getStudio() && searchForm.getHouse()) {
			List<Auction> temp = auctionDao.findByRoomTypeAndPrizeLessThan("Studio", searchForm.getPrize() + 1);
			temp.addAll(auctionDao.findByRoomTypeAndPrizeLessThan("House", searchForm.getPrize() + 1));
			results = temp;
		} else if (searchForm.getRoom()) {
			results = auctionDao.findByRoomTypeAndPrizeLessThan("Room", searchForm.getPrize() + 1);
		} else if (searchForm.getStudio()) {
			results = auctionDao.findByRoomTypeAndPrizeLessThan("Studio", searchForm.getPrize() + 1);
		} else {
			results = auctionDao.findByRoomTypeAndPrizeLessThan("House", searchForm.getPrize() + 1);
		}

		// filter out zipcodez
		String city = searchForm.getCity().substring(7);

		// get the location that the user searched for and take the one with the
		// lowest zip code
		Location searchedLocation = geoDataService.getLocationsByCity(city).get(0);

		// create a list of the results and of their locations
		List<Auction> locatedResults = new ArrayList<>();
		for (Auction ad : results) {
			locatedResults.add(ad);
		}

		final int earthRadiusKm = 6380;
		List<Location> locations = geoDataService.getAllLocations();
		double radSinLat = Math.sin(Math.toRadians(searchedLocation.getLatitude()));
		double radCosLat = Math.cos(Math.toRadians(searchedLocation.getLatitude()));
		double radLong = Math.toRadians(searchedLocation.getLongitude());

		/*
		 * calculate the distances (Java 8) and collect all matching zipcodes.
		 * The distance is calculated using the law of cosines.
		 * http://www.movable-type.co.uk/scripts/latlong.html
		 */
		List<Integer> zipcodes = locations.parallelStream().filter(location -> {
			double radLongitude = Math.toRadians(location.getLongitude());
			double radLatitude = Math.toRadians(location.getLatitude());
			double distance = Math.acos(radSinLat * Math.sin(radLatitude)
					+ radCosLat * Math.cos(radLatitude) * Math.cos(radLong - radLongitude)) * earthRadiusKm;
			return distance < searchForm.getRadius();
		}).map(location -> location.getZip()).collect(Collectors.toList());

		locatedResults = locatedResults.stream().filter(ad -> zipcodes.contains(ad.getZipcode()))
				.collect(Collectors.toList());

		// filter for additional criteria
		// prepare date filtering - by far the most difficult filter
		Date earliestInDate = null;
		Date latestInDate = null;

		// parse move-in and move-out dates
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			earliestInDate = formatter.parse(searchForm.getEarliestMoveInDate());
		} catch (Exception e) {
		}
		try {
			latestInDate = formatter.parse(searchForm.getLatestMoveInDate());
		} catch (Exception e) {
		}

		// filtering by dates
		locatedResults = validateDate(locatedResults, true, earliestInDate, latestInDate);

		// filtering for the rest
		// smokers
		if (searchForm.getSmokers()) {
			Iterator<Auction> iterator = locatedResults.iterator();
			while (iterator.hasNext()) {
				Auction ad = iterator.next();
				if (!ad.getSmokers())
					iterator.remove();
			}
		}

		// animals
		if (searchForm.getAnimals()) {
			Iterator<Auction> iterator = locatedResults.iterator();
			while (iterator.hasNext()) {
				Auction ad = iterator.next();
				if (!ad.getAnimals())
					iterator.remove();
			}
		}

		// garden
		if (searchForm.getGarden()) {
			Iterator<Auction> iterator = locatedResults.iterator();
			while (iterator.hasNext()) {
				Auction ad = iterator.next();
				if (!ad.getGarden())
					iterator.remove();
			}
		}

		// balcony
		if (searchForm.getBalcony()) {
			Iterator<Auction> iterator = locatedResults.iterator();
			while (iterator.hasNext()) {
				Auction ad = iterator.next();
				if (!ad.getBalcony())
					iterator.remove();
			}
		}

		// cellar
		if (searchForm.getCellar()) {
			Iterator<Auction> iterator = locatedResults.iterator();
			while (iterator.hasNext()) {
				Auction ad = iterator.next();
				if (!ad.getCellar())
					iterator.remove();
			}
		}

		// furnished
		if (searchForm.getFurnished()) {
			Iterator<Auction> iterator = locatedResults.iterator();
			while (iterator.hasNext()) {
				Auction ad = iterator.next();
				if (!ad.getFurnished())
					iterator.remove();
			}
		}

		// cable
		if (searchForm.getCable()) {
			Iterator<Auction> iterator = locatedResults.iterator();
			while (iterator.hasNext()) {
				Auction ad = iterator.next();
				if (!ad.getCable())
					iterator.remove();
			}
		}

		// garage
		if (searchForm.getGarage()) {
			Iterator<Auction> iterator = locatedResults.iterator();
			while (iterator.hasNext()) {
				Auction ad = iterator.next();
				if (!ad.getGarage())
					iterator.remove();
			}
		}

		// internet
		if (searchForm.getInternet()) {
			Iterator<Auction> iterator = locatedResults.iterator();
			while (iterator.hasNext()) {
				Auction ad = iterator.next();
				if (!ad.getInternet())
					iterator.remove();
			}
		}
		
		Iterator<Auction> iterator = locatedResults.iterator();
		while (iterator.hasNext()) {
			Auction ad = iterator.next();
			User user = ad.getUser();
			if (user.getAccount().equals("Premium") != premium){
				iterator.remove(); 
			}
		}
		
		return locatedResults;
	}

	private List<Auction> validateDate(List<Auction> ads, boolean inOrOut, Date earliestDate, Date latestDate) {
		if (ads.size() > 0) {
			// Move-in dates
			// Both an earliest AND a latest date to compare to
			if (earliestDate != null) {
				if (latestDate != null) {
					Iterator<Auction> iterator = ads.iterator();
					while (iterator.hasNext()) {
						Auction ad = iterator.next();
						if (ad.getDate(inOrOut) == null || ad.getDate(inOrOut).compareTo(earliestDate) < 0
								|| ad.getDate(inOrOut).compareTo(latestDate) > 0) {
							iterator.remove();
						}
					}
				}
				// only an earliest date
				else {
					Iterator<Auction> iterator = ads.iterator();
					while (iterator.hasNext()) {
						Auction ad = iterator.next();
						if (ad.getDate(inOrOut) == null || ad.getDate(inOrOut).compareTo(earliestDate) < 0)
							iterator.remove();
					}
				}
			}
			// only a latest date
			else if (latestDate != null && earliestDate == null) {
				Iterator<Auction> iterator = ads.iterator();
				while (iterator.hasNext()) {
					Auction ad = iterator.next();
					if (ad.getDate(inOrOut) == null || ad.getDate(inOrOut).compareTo(latestDate) > 0)
						iterator.remove();
				}
			} else {
			}
		}
		return ads;
	}

	public void delete(long auctionId) {
		Auction auction = auctionDao.findOne(auctionId);
		visitService.delete(auction);
		bookmarkService.deleteAuction(auction);
		auctionDao.delete(auction);
	}
}

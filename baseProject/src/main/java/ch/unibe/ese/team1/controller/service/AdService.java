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

import ch.unibe.ese.team1.controller.pojos.forms.PlaceAdForm;
import ch.unibe.ese.team1.controller.pojos.forms.SearchForm;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.AdPicture;
import ch.unibe.ese.team1.model.Advertisement;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.Location;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.Visit;
import ch.unibe.ese.team1.model.dao.AdDao;
import ch.unibe.ese.team1.model.dao.AuctionDao;

/** Handles all persistence operations concerning ad placement and retrieval. */
@Service
public class AdService {

	@Autowired
	private AdDao adDao;
	
	@Autowired
	private AuctionDao auctionDao;

	@Autowired
	private GeoDataService geoDataService;

	/**
	 * Handles persisting a new ad to the database.
	 * 
	 * @param 	placeAdForm		the form to take the data from
	 * @param 	filepaths		list of the file paths the pictures are saved under
	 * @param 	user			the currently logged in user
	 * @return	ad				new ad reference
	 */
	@Transactional
	public Ad saveFrom(PlaceAdForm placeAdForm, List<String> filePaths, User user) {
		
		Ad ad = new Ad();
		Date now = new Date();	
		ad.setCreationDate(now);
		
		/** general info values */
		ad.setTitle(placeAdForm.getTitle());
		ad.setStreet(placeAdForm.getStreet());
		ad.setStudio(placeAdForm.getStudio());
		ad.setRoomType(placeAdForm.getRoomType());
		ad.setPrize(placeAdForm.getPrize());
		ad.setSquareFootage(placeAdForm.getSquareFootage());

		// take the zipcode - first four digits
		String zip = placeAdForm.getCity().substring(0, 4);
		ad.setZipcode(Integer.parseInt(zip));
		ad.setCity(placeAdForm.getCity().substring(7));
		
		// java.util.Calendar uses a month range of 0-11 instead of the
		// XMLGregorianCalendar which uses 1-12
		Calendar calendar = Calendar.getInstance();
		try {
			if (placeAdForm.getMoveInDate().length() >= 1) {
				int dayMoveIn = Integer.parseInt(placeAdForm.getMoveInDate()
						.substring(0, 2));
				int monthMoveIn = Integer.parseInt(placeAdForm.getMoveInDate()
						.substring(3, 5));
				int yearMoveIn = Integer.parseInt(placeAdForm.getMoveInDate()
						.substring(6, 10));
				calendar.set(yearMoveIn, monthMoveIn - 1, dayMoveIn);
				ad.setMoveInDate(calendar.getTime());
			}

			if (placeAdForm.getMoveOutDate().length() >= 1) {
				int dayMoveOut = Integer.parseInt(placeAdForm.getMoveOutDate()
						.substring(0, 2));
				int monthMoveOut = Integer.parseInt(placeAdForm
						.getMoveOutDate().substring(3, 5));
				int yearMoveOut = Integer.parseInt(placeAdForm.getMoveOutDate()
						.substring(6, 10));
				calendar.set(yearMoveOut, monthMoveOut - 1, dayMoveOut);
				ad.setMoveOutDate(calendar.getTime());
			}
		} catch (NumberFormatException e) {
		}

		/** ad description values */
		ad.setSmokers(placeAdForm.isSmokers());
		ad.setAnimals(placeAdForm.isAnimals());
		ad.setGarden(placeAdForm.getGarden());
		ad.setBalcony(placeAdForm.getBalcony());
		ad.setCellar(placeAdForm.getCellar());
		ad.setFurnished(placeAdForm.isFurnished());
		ad.setCable(placeAdForm.getCable());
		ad.setGarage(placeAdForm.getGarage());
		ad.setInternet(placeAdForm.getInternet());
		ad.setRoomDescription(placeAdForm.getRoomDescription());
		
		/** preferences value */
		ad.setPreferences(placeAdForm.getPreferences());
		
		/**
		 *  Save the paths to the picture files, the pictures are assumed to be
		 *  uploaded at this point!
		 */
		List<AdPicture> pictures = new ArrayList<>();
		for (String filePath : filePaths) {
			AdPicture picture = new AdPicture();
			picture.setFilePath(filePath);
			pictures.add(picture);
		}
		ad.setPictures(pictures);

		/** visits list */
		List<Visit> visits = new LinkedList<>();
		List<String> visitStrings = placeAdForm.getVisits();
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
				visit.setAd(ad);
				visits.add(visit);
			}
			ad.setVisits(visits);
		}
		
		ad.setAuction(false);
		ad.setBuyable(false);
		ad.setUser(user);
		adDao.save(ad);

		return ad;
	}

	/**
	 * Gets the ad that has the given id.
	 * 
	 * @param 	id		the id that should be searched for
	 * @return 	ad		the found ad or null, if no ad with this id exists
	 */
	@Transactional
	public Ad getAdById(long id) {
		return adDao.findOne(id);
	}

	/** Returns all ads in the database */
	@Transactional
	public Iterable<Ad> getAllAds() {
		return adDao.findAll();
	}

	/**
	 * Returns the newest ads in the database. Parameter 'newest' says how many.
	 */
	@Transactional
	public Iterable<Advertisement> getNewestAds(int newest) {
		Iterable<Ad> allAds = adDao.findAll();
		Iterable<Auction> allAuctions = auctionDao.findAll();
		List<Advertisement> advertisements = new ArrayList<Advertisement>();
		for (Ad ad : allAds)
			advertisements.add(ad);
		for (Auction auction: allAuctions)
			advertisements.add(auction);
		Collections.sort(advertisements, new Comparator<Advertisement>() {
			@Override
			public int compare(Advertisement ad1, Advertisement ad2) {
				return ad2.getCreationDate().compareTo(ad1.getCreationDate());
			}
		});
		List<Advertisement> fourNewest = new ArrayList<Advertisement>();
		for (int i = 0; i < newest; i++)
			fourNewest.add(advertisements.get(i));
		return fourNewest;
	}

	/**
	 * Returns all ads that match the parameters given by the form. This list
	 * can possibly be empty.
	 * 
	 * @param 	searchForm		the form to take the search parameters from
	 * @return 	locatedResults	an Iterable of all search results
	 */
	@Transactional
	public Iterable<Ad> queryResults(SearchForm searchForm) {
		Iterable<Ad> results = null;	
		// we use this method if we are looking for rooms AND studios AND houses
		if (searchForm.getRoom() && searchForm.getStudio() && searchForm.getHouse()) {
			results = adDao.findByPrizeLessThan(searchForm.getPrize() + 1);
		} else if (searchForm.getRoom() && searchForm.getHouse()) {
			List<Ad> temp = adDao.findByRoomTypeAndPrizeLessThan("Room", searchForm.getPrize() + 1);
			temp.addAll(adDao.findByRoomTypeAndPrizeLessThan("House", searchForm.getPrize() + 1));
			results = temp;
		} else if (searchForm.getRoom() && searchForm.getStudio()) {
			List<Ad> temp = adDao.findByRoomTypeAndPrizeLessThan("Room", searchForm.getPrize() + 1);
			temp.addAll(adDao.findByRoomTypeAndPrizeLessThan("Studio", searchForm.getPrize() + 1));
			results = temp;
		} else if (searchForm.getStudio() && searchForm.getHouse()) {
			List<Ad> temp = adDao.findByRoomTypeAndPrizeLessThan("Studio", searchForm.getPrize() + 1);
			temp.addAll(adDao.findByRoomTypeAndPrizeLessThan("House", searchForm.getPrize() + 1));
			results = temp;
		} else if (searchForm.getRoom()) {
			results = adDao.findByRoomTypeAndPrizeLessThan("Room", searchForm.getPrize() + 1);
		} else if (searchForm.getStudio()) {
			results = adDao.findByRoomTypeAndPrizeLessThan("Studio", searchForm.getPrize() + 1);
		} else {
			results = adDao.findByRoomTypeAndPrizeLessThan("House", searchForm.getPrize() + 1);
		}

		// filter out zipcodez
		String city = searchForm.getCity().substring(7);

		// get the location that the user searched for and take the one with the
		// lowest zip code
		Location searchedLocation = geoDataService.getLocationsByCity(city)
				.get(0);

		// create a list of the results and of their locations
		List<Ad> locatedResults = new ArrayList<>();
		for (Ad ad : results) {
			locatedResults.add(ad);
		}

		final int earthRadiusKm = 6380;
		List<Location> locations = geoDataService.getAllLocations();
		double radSinLat = Math.sin(Math.toRadians(searchedLocation
				.getLatitude()));
		double radCosLat = Math.cos(Math.toRadians(searchedLocation
				.getLatitude()));
		double radLong = Math.toRadians(searchedLocation.getLongitude());

		/**
		 *   calculate the distances (Java 8) and collect all matching zipcodes.
		 *   The distance is calculated using the law of cosines.
		 *   http://www.movable-type.co.uk/scripts/latlong.html
		 */
		List<Integer> zipcodes = locations
				.parallelStream()
				.filter(location -> {
					double radLongitude = Math.toRadians(location
							.getLongitude());
					double radLatitude = Math.toRadians(location.getLatitude());
					double distance = Math.acos(radSinLat
							* Math.sin(radLatitude) + radCosLat
							* Math.cos(radLatitude)
							* Math.cos(radLong - radLongitude))
							* earthRadiusKm;
					return distance < searchForm.getRadius();
				}).map(location -> location.getZip())
				.collect(Collectors.toList());

		locatedResults = locatedResults.stream()
				.filter(ad -> zipcodes.contains(ad.getZipcode()))
				.collect(Collectors.toList());

		// filter for additional criteria
		if (searchForm.getFiltered()) {
			// prepare date filtering - by far the most difficult filter
			Date earliestInDate = null;
			Date latestInDate = null;
			Date earliestOutDate = null;
			Date latestOutDate = null;

			// parse move-in and move-out dates
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			try {
				earliestInDate = formatter.parse(searchForm
						.getEarliestMoveInDate());
			} catch (Exception e) {
			}
			try {
				latestInDate = formatter
						.parse(searchForm.getLatestMoveInDate());
			} catch (Exception e) {
			}
			try {
				earliestOutDate = formatter.parse(searchForm
						.getEarliestMoveOutDate());
			} catch (Exception e) {
			}
			try {
				latestOutDate = formatter.parse(searchForm
						.getLatestMoveOutDate());
			} catch (Exception e) {
			}

			// filtering by dates
			locatedResults = validateDate(locatedResults, true, earliestInDate,
					latestInDate);
			locatedResults = validateDate(locatedResults, false,
					earliestOutDate, latestOutDate);

			// filtering for the rest
			// smokers
			if (searchForm.getSmokers()) {
				Iterator<Ad> iterator = locatedResults.iterator();
				while (iterator.hasNext()) {
					Ad ad = iterator.next();
					if (!ad.getSmokers())
						iterator.remove();
				}
			}

			// animals
			if (searchForm.getAnimals()) {
				Iterator<Ad> iterator = locatedResults.iterator();
				while (iterator.hasNext()) {
					Ad ad = iterator.next();
					if (!ad.getAnimals())
						iterator.remove();
				}
			}

			// garden
			if (searchForm.getGarden()) {
				Iterator<Ad> iterator = locatedResults.iterator();
				while (iterator.hasNext()) {
					Ad ad = iterator.next();
					if (!ad.getGarden())
						iterator.remove();
				}
			}

			// balcony
			if (searchForm.getBalcony()) {
				Iterator<Ad> iterator = locatedResults.iterator();
				while (iterator.hasNext()) {
					Ad ad = iterator.next();
					if (!ad.getBalcony())
						iterator.remove();
				}
			}

			// cellar
			if (searchForm.getCellar()) {
				Iterator<Ad> iterator = locatedResults.iterator();
				while (iterator.hasNext()) {
					Ad ad = iterator.next();
					if (!ad.getCellar())
						iterator.remove();
				}
			}

			// furnished
			if (searchForm.getFurnished()) {
				Iterator<Ad> iterator = locatedResults.iterator();
				while (iterator.hasNext()) {
					Ad ad = iterator.next();
					if (!ad.getFurnished())
						iterator.remove();
				}
			}

			// cable
			if (searchForm.getCable()) {
				Iterator<Ad> iterator = locatedResults.iterator();
				while (iterator.hasNext()) {
					Ad ad = iterator.next();
					if (!ad.getCable())
						iterator.remove();
				}
			}

			// garage
			if (searchForm.getGarage()) {
				Iterator<Ad> iterator = locatedResults.iterator();
				while (iterator.hasNext()) {
					Ad ad = iterator.next();
					if (!ad.getGarage())
						iterator.remove();
				}
			}

			// internet
			if (searchForm.getInternet()) {
				Iterator<Ad> iterator = locatedResults.iterator();
				while (iterator.hasNext()) {
					Ad ad = iterator.next();
					if (!ad.getInternet())
						iterator.remove();
				}
			}
		}
		return locatedResults;
	}
	
	/**
	 * Validates given dates for search.
	 * 
	 * @param 	ads				list of ads for validation
	 * @param 	inOrOut			in or out date of current ad
	 * @param	earliestDate	given earliest date in search
	 * @param	latestDate		given latest date in search
	 * @return 	ads				true if the email has been added already, false otherwise
	 */
	private List<Ad> validateDate(List<Ad> ads, boolean inOrOut,
			Date earliestDate, Date latestDate) {
		if (ads.size() > 0) {
			// Move-in dates
			// Both an earliest AND a latest date to compare to
			if (earliestDate != null) {
				if (latestDate != null) {
					Iterator<Ad> iterator = ads.iterator();
					while (iterator.hasNext()) {
						Ad ad = iterator.next();
						if (ad.getDate(inOrOut).compareTo(earliestDate) < 0
								|| ad.getDate(inOrOut).compareTo(latestDate) > 0) {
							iterator.remove();
						}
					}
				}
				// only an earliest date
				else {
					Iterator<Ad> iterator = ads.iterator();
					while (iterator.hasNext()) {
						Ad ad = iterator.next();
						if (ad.getDate(inOrOut).compareTo(earliestDate) < 0)
							iterator.remove();
					}
				}
			}
			// only a latest date
			else if (latestDate != null && earliestDate == null) {
				Iterator<Ad> iterator = ads.iterator();
				while (iterator.hasNext()) {
					Ad ad = iterator.next();
					if (ad.getDate(inOrOut).compareTo(latestDate) > 0)
						iterator.remove();
				}
			} else {
			}
		}
		return ads;
	}

	/** Returns all ads that were placed by the given user. */
	public Iterable<Ad> getAdsByUser(User user) {
		return adDao.findByUser(user);
	}

	/**
	 * Checks if the email of a user is already contained in the given string.
	 * 
	 * @param 	email			the email string to search for
	 * @param 	alreadyAdded	the string of already added emails,
	 * 							which should be searched in
	 * @return 	[boolean]		true if the email has been added already, false otherwise
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
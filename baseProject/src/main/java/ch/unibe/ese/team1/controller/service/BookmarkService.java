package ch.unibe.ese.team1.controller.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Advertisement;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.dao.UserDao;

/**
 * Adds or removes bookmarked ads from the user and updates the user accordingly
 */
@Service
public class BookmarkService {

	@Autowired
	private UserDao userDao;

	/**
	 * This method adds or removes ads from the ArrayList.
	 * 
	 * @param id
	 *            it's the current ads' id
	 * @param bookmarked
	 *            tells the function the state of of the ad regarding bookmarks
	 * @param bookmarkedAds
	 *            users current list of bookmarked ads
	 * @param user
	 *            current user
	 * 
	 * @return returns an integer 3 bookmark it 2 undo the bookmark
	 * 
	 */
	public int getBookmarkStatus(Ad ad, boolean bookmarked, User user) {
		List<Ad> tempAdList = user.getBookmarkedAds();
		if (bookmarked) {
			tempAdList.remove(ad);
			updateUserAd(tempAdList, user);
			return 2;
		}

		if (!bookmarked) {
			tempAdList.add(ad);
			updateUserAd(tempAdList, user);
			return 3;
		}

		return 1;
	}

	public int getBookmarkStatusAuction(Auction auction, boolean bookmarked, User user) {
		List<Auction> tempAdList = user.getBookmarkedAuctions();
		if (bookmarked) {
			tempAdList.remove(auction);
			updateUserAuction(tempAdList, user);
			return 2;
		}

		if (!bookmarked) {
			tempAdList.add(auction);
			updateUserAuction(tempAdList, user);
			return 3;
		}
		
		return 1;
	}

	// updates effectively the new List into DB
	private void updateUserAd(List<Ad> bookmarkedAds, User user) {
		user.setBookmarkedAds(bookmarkedAds);
		userDao.save(user);

	}

	// updates effectively the new List into DB
	private void updateUserAuction(List<Auction> bookmarkedAuctions, User user) {
		user.setBookmarkedAuctions(bookmarkedAuctions);
		userDao.save(user);

	}

	public void deleteAd(Ad ad) {
		Iterable<User> userList = userDao.findAll();
		for (User user : userList) {
			if (user.getBookmarkedAds().contains(ad)) {
				getBookmarkStatus(ad, true, user);
			}
		}
	}

	public void deleteAuction(Auction auction) {
		Iterable<User> userList = userDao.findAll();
		for (User user : userList) {
			if (user.getBookmarkedAuctions().contains(auction)) {
				getBookmarkStatusAuction(auction, true, user);
			}
		}
	}
}

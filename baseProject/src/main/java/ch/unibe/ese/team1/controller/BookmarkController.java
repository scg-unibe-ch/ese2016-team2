package ch.unibe.ese.team1.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ch.unibe.ese.team1.controller.pojos.forms.SearchForm;
import ch.unibe.ese.team1.controller.service.AdService;
import ch.unibe.ese.team1.controller.service.AuctionService;
import ch.unibe.ese.team1.controller.service.BookmarkService;
import ch.unibe.ese.team1.controller.service.UserService;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.User;

@Controller
public class BookmarkController {

	@Autowired
	UserService userService;

	@Autowired
	AdService adService;

	@Autowired
	AuctionService auctionService;

	@Autowired
	BookmarkService bookmarkService;
	
	/**
	 * Add these properties for basic search functionality.
	 */
	private SearchForm searchForm;

	/**
	 * Fetches information about bookmarked ads and attaches this information to the
	 * bookmarkes page in order to be displayed.
	 */
	@RequestMapping(value = "/profile/bookmarks", method = RequestMethod.GET)
	public ModelAndView bookmarkes(Principal principal) {
		ModelAndView model;
		User user;
		if (principal != null) {
			model = new ModelAndView("bookmarks");
			String username = principal.getName();
			user = userService.findUserByUsername(username);
			
			model.addObject("bookmarkedAdvertisements", user.getBookmarkedAds());
			model.addObject("bookmarkedAuctions", user.getBookmarkedAuctions());
		} else {
			model = new ModelAndView("home");
		}
		
		return model;
	}

	/**
	 * Checks if the adID passed as post parameter is already inside user's List
	 * bookmarkedAds. In case it is present, true is returned changing the
	 * "Bookmark Ad" button to "Bookmarked". If it is not present it is added to
	 * the List bookmarkedAds.
	 *
	 * @return 0 and 1 for errors; 3 to update the button to bookmarked 3 and 2
	 *         for bookmarking or undo bookmarking respectively 4 for removing
	 *         button completly (because its the users ad)
	 */
	@RequestMapping(value = "/bookmark", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public int isBookmarked(@RequestParam("id") long id, @RequestParam("screening") boolean screening,
			@RequestParam("bookmarked") boolean bookmarked, Principal principal) {
		// should never happen since no bookmark button when not logged in
		if (principal == null) {
			return 0;
		}
		String username = principal.getName();
		User user = userService.findUserByUsername(username);
		if (user == null) {
			// that should not happen...
			return 1;
		}
		List<Ad> bookmarkedAdsIterable = user.getBookmarkedAds();
		if (screening) {
			for (Ad ownAdIterable : adService.getAdsByUser(user)) {
				if (ownAdIterable.getId() == id) {
					return 4;
				}
			}
			for (Ad adIterable : bookmarkedAdsIterable) {
				if (adIterable.getId() == id) {
					return 3;
				}
			}
			return 2;
		}

		Ad ad = adService.getAdById(id);

		return bookmarkService.getBookmarkStatus(ad, bookmarked, user);
	}

	/**
	 * Checks if the auctionID passed as post parameter is already inside user's
	 * List bookmarkedAuctions. In case it is present, true is returned changing
	 * the "Bookmark Auction" button to "Bookmarked". If it is not present it is
	 * added to the List bookmarkedAuctions.
	 *
	 * @return 0 and 1 for errors; 3 to update the button to bookmarked 3 and 2
	 *         for bookmarking or undo bookmarking respectively 4 for removing
	 *         button completly (because its the users ad)
	 */
	@RequestMapping(value = "/bookmarkAuction", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public int isBookmarkedAuction(@RequestParam("id") long id, @RequestParam("screening") boolean screening,
			@RequestParam("bookmarked") boolean bookmarked, Principal principal) {
		// should never happen since no bookmark button when not logged in
		if (principal == null) {
			return 0;
		}
		String username = principal.getName();
		User user = userService.findUserByUsername(username);
		if (user == null) {
			// that should not happen...
			return 1;
		}
		List<Auction> bookmarkedAuctionsIterable = user.getBookmarkedAuctions();
		if (screening) {
			for (Auction ownAdIterable : auctionService.getAuctionsByUser(user)) {
				if (ownAdIterable.getId() == id) {
					return 4;
				}
			}
			for (Auction adIterable : bookmarkedAuctionsIterable) {
				if (adIterable.getId() == id) {
					return 3;
				}
			}
			return 2;
		}

		Auction auction = auctionService.getAuctionById(id);

		return bookmarkService.getBookmarkStatusAuction(auction, bookmarked, user);
	}
	 
	/**
	 * Add this attribute for basic search functionality.
	 */
	@ModelAttribute
	public SearchForm getSearchForm() {
		if (searchForm == null) {
			searchForm = new SearchForm();
		}
		return searchForm;
	}

}

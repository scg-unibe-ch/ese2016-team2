package ch.unibe.ese.team1.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ch.unibe.ese.team1.controller.service.AdService;
import ch.unibe.ese.team1.controller.service.AuctionService;
import ch.unibe.ese.team1.controller.service.UserService;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.User;


/**
 * @Jerome
 * Add these imports for basic search functionality.
 */
import org.springframework.web.bind.annotation.ModelAttribute;
import ch.unibe.ese.team1.controller.pojos.forms.SearchForm;



@Controller
public class MyRoomController {

	@Autowired
	private AdService adService;

	@Autowired
	private AuctionService auctionService;

	@Autowired
	private UserService userService;



	/**
	 * @Jerome
	 * Add these properties for basic search functionality.
	 */
	private SearchForm searchForm;

	/**
	 * Fetches information about own ads and attaches this
	 * information to the myRooms page in order to be displayed.
	 */
	@RequestMapping(value = "/profile/myRooms", method = RequestMethod.GET)
	public ModelAndView myRooms(Principal principal) {
		ModelAndView model;
		User user;
		if (principal != null) {
			model = new ModelAndView("myRooms");
			String username = principal.getName();
			user = userService.findUserByUsername(username);

			Iterable<Ad> ownAds = adService.getAdsByUser(user);
			Iterable<Auction> ownAuctions = auctionService.getAuctionsByUser(user);

			model.addObject("bookmarkedAdvertisements", user.getBookmarkedAds());
			model.addObject("bookmarkedAuctions", user.getBookmarkedAuctions());
			model.addObject("ownAds", ownAds);
			model.addObject("ownAuctions", ownAuctions);
			return model;
		} else {
			model = new ModelAndView("home");
		}

		return model;
	}


	/**
	 * @Jerome
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

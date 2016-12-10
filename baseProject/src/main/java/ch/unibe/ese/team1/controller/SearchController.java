package ch.unibe.ese.team1.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.unibe.ese.team1.controller.pojos.forms.AlertForm;
import ch.unibe.ese.team1.controller.pojos.forms.SearchForm;
import ch.unibe.ese.team1.controller.service.AdService;
import ch.unibe.ese.team1.controller.service.AlertService;
import ch.unibe.ese.team1.controller.service.AuctionService;
import ch.unibe.ese.team1.controller.service.UserService;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Advertisement;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.User;

/** Handles all requests concerning the search for ads. */
@Controller
public class SearchController {

	@Autowired
	private AdService adService;

	@Autowired
	private AuctionService auctionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AlertService alertService;

	/**
	 * The search form that is used for searching. It is saved between request
	 * so that users don't have to enter their search parameters multiple times.
	 */
	private SearchForm searchForm;

	/** Shows the search ad page. */
	@RequestMapping(value = "/searchAd", method = RequestMethod.GET)
	public ModelAndView searchAd() {
		ModelAndView model = new ModelAndView("searchAd");
		return model;
	}

	/**
	 * Gets the results when filtering the ads in the database by the parameters
	 * in the search form.
	 */
	@RequestMapping(value = "/results", params = "results", method = RequestMethod.POST)
	public ModelAndView results(@Valid SearchForm searchForm, BindingResult result) {
		if (!result.hasErrors()) {
			ModelAndView model = new ModelAndView("results");

			List<Advertisement> results = new ArrayList<Advertisement>();
			List<Advertisement> premiumResults = new ArrayList<Advertisement>();
			Iterable<Ad> matchingAds;
			Iterable<Auction> matchingAuctions;
			if (searchForm.getAuction() == searchForm.getAd()) {
				if (searchForm.getBuyable()) {
					matchingAuctions = auctionService.queryResults(searchForm, false);
					for (Auction auction : matchingAuctions) {
						results.add(auction);
					}
					matchingAuctions = auctionService.queryResults(searchForm, true);
					for (Auction auction : matchingAuctions) {
						premiumResults.add(auction);
					}
				}
				matchingAds = adService.queryResults(searchForm, false);
				for (Ad ad : matchingAds) {
					results.add(ad);
				}
				matchingAds = adService.queryResults(searchForm, true);
				for (Ad ad : matchingAds) {
					premiumResults.add(ad);
				}
				model.addObject("results", results);
				model.addObject("premiumResults", premiumResults);
				return model;
			} else {
				if (searchForm.getBuyable() && searchForm.getAuction()) {
					matchingAuctions = auctionService.queryResults(searchForm, false);
					for (Auction auction : matchingAuctions) {
						results.add(auction);
					}
					matchingAuctions = auctionService.queryResults(searchForm, true);
					for (Auction auction : matchingAuctions) {
						premiumResults.add(auction);
					}
				}
				if (searchForm.getAd()) {
					matchingAds = adService.queryResults(searchForm, false);
					for (Ad ad : matchingAds) {
						results.add(ad);
					}
					matchingAds = adService.queryResults(searchForm, true);
					for (Ad ad : matchingAds) {
						premiumResults.add(ad);
					}
				}
				model.addObject("results", results);
				model.addObject("premiumResults", premiumResults);
				return model;
			}

		} else

		{
			// go back
			return searchAd();
		}
	}

	@RequestMapping(value = "/results", params = "createAlert", method = RequestMethod.POST)
	public ModelAndView createAlert(@Valid SearchForm searchForm, BindingResult results, Principal principal,
			RedirectAttributes redirectAttr) {
		if (!results.hasErrors() && principal != null) {
			String username = principal.getName();
			User user = userService.findUserByUsername(username);
			
			AlertForm alert = new AlertForm();
			alert.setCity(searchForm.getCity());
			alert.setPrice(searchForm.getPrize());
			alert.setRadius(searchForm.getRadius());
			if (searchForm.getRoom() && searchForm.getStudio() && searchForm.getHouse())
				alert.setAlertType("All");
			else if (!searchForm.getRoom() && searchForm.getStudio() && searchForm.getHouse())
				alert.setAlertType("Stuio and House");
			else if (searchForm.getRoom() && !searchForm.getStudio() && searchForm.getHouse())
				alert.setAlertType("Room and House");
			else if (searchForm.getRoom() && searchForm.getStudio() && !searchForm.getHouse())
				alert.setAlertType("Room and Studio");
			else if (searchForm.getRoom())
				alert.setAlertType("Room");
			else if (searchForm.getStudio())
				alert.setAlertType("Studio");
			else 
				alert.setAlertType("House");
			
			alertService.saveFrom(alert, user);
			
			return new ModelAndView("redirect:/profile/alerts");
		} else {
			return searchAd();
		}
	}

	@ModelAttribute
	public SearchForm getSearchForm() {
		if (searchForm == null) {
			searchForm = new SearchForm();
		}
		return searchForm;
	}
}

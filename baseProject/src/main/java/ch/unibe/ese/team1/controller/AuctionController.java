package ch.unibe.ese.team1.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ch.unibe.ese.team1.controller.pojos.forms.MessageForm;
import ch.unibe.ese.team1.controller.service.AuctionService;
import ch.unibe.ese.team1.controller.service.MessageService;
import ch.unibe.ese.team1.controller.service.VisitService;
import ch.unibe.ese.team1.model.Auction;

/**
 * @Jerome
 * Add these imports for basic search functionality.
 */
import org.springframework.web.bind.annotation.ModelAttribute;
import ch.unibe.ese.team1.controller.pojos.forms.SearchForm;


@Controller
public class AuctionController {

	/**
	 * The place ad form that is shared between several requests, so that the
	 * user only has to enter the data once. If an ad is placed, this form is
	 * reset.
	 */

	@Autowired
	private AuctionService auctionService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private VisitService visitService;


	/**
	 * @Jerome Add these properties for basic search functionality.
	 */
	private SearchForm searchForm;

	

	/** Gets the auction description page for the auction with the given id. */
	@RequestMapping(value = "/auction", method = RequestMethod.GET)
	public ModelAndView auction(@RequestParam("id") long id, Principal principal) {
		ModelAndView model = new ModelAndView("auctionDescription");
		Auction auction = auctionService.getAuctionById(id);
		model.addObject("shownAuction", auction);
		model.addObject("messageForm", new MessageForm());

		String loggedInUserEmail = (principal == null) ? "" : principal.getName();
		model.addObject("loggedInUserEmail", loggedInUserEmail);
		model.addObject("visits", visitService.getVisitsByAuction(auction));

		return model;
	}

	/**
	 * Gets the auction description page for the auction with the given id and
	 * also validates and persists the message passed as post data.
	 */
	@RequestMapping(value = "/auction", method = RequestMethod.POST)
	public ModelAndView messageSent(@RequestParam("id") long id, @Valid MessageForm messageForm,
			BindingResult bindingResult) {

		ModelAndView model = new ModelAndView("auctionDescription");
		Auction auction = auctionService.getAuctionById(id);
		model.addObject("shownAuction", auction);
		model.addObject("messageForm", new MessageForm());

		if (!bindingResult.hasErrors()) {
			messageService.saveFrom(messageForm);
		}
		return model;
	}


	/**
	 * @Jerome Add this attribute for basic search functionality.
	 */
	@ModelAttribute
	public SearchForm getSearchForm() {
		if (searchForm == null) {
			searchForm = new SearchForm();
		}
		return searchForm;
	}
}

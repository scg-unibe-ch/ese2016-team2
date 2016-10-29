package ch.unibe.ese.team1.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.unibe.ese.team1.controller.pojos.PictureUploader;
import ch.unibe.ese.team1.controller.pojos.forms.MessageForm;
import ch.unibe.ese.team1.controller.pojos.forms.PlaceAdForm;
import ch.unibe.ese.team1.controller.pojos.forms.PlaceBidForm;
import ch.unibe.ese.team1.controller.service.AdService;
import ch.unibe.ese.team1.controller.service.AuctionService;
import ch.unibe.ese.team1.controller.service.BookmarkService;
import ch.unibe.ese.team1.controller.service.MessageService;
import ch.unibe.ese.team1.controller.service.UserService;
import ch.unibe.ese.team1.controller.service.VisitService;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.User;

@Controller
public class AuctionController {

	/**
	 * The place ad form that is shared between several requests, so that the
	 * user only has to enter the data once. If an ad is placed, this form is
	 * reset.
	 */
	private PlaceBidForm placeBidForm;

	@Autowired
	private AuctionService auctionService;

	@Autowired
	private UserService userService;

	@Autowired
	private BookmarkService bookmarkService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private VisitService visitService;

	/** Gets the auction description page for the auction with the given id. */
	@RequestMapping(value = "/auction", method = RequestMethod.GET)
	public ModelAndView auction(@RequestParam("id") long id, Principal principal) {
		ModelAndView model = new ModelAndView("auctionDescription");
		Auction auction = auctionService.getAuctionById(id);
		model.addObject("shownAuction", auction);
		model.addObject("messageForm", new MessageForm());

		String loggedInUserEmail = (principal == null) ? "" : principal.getName();
		model.addObject("loggedInUserEmail", loggedInUserEmail);

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

	/** Shows the place new bid form. */
	@RequestMapping(value = "/auction/placeBid", method = RequestMethod.GET)
	public ModelAndView placeBid(@RequestParam("id") long id, Principal principal) {
		ModelAndView model = new ModelAndView("placeBid");
		Auction auction = auctionService.getAuctionById(id);
		model.addObject("shownAuction", auction);
		String loggedInUser = (principal == null) ? "" : principal.getName();
		model.addObject("loggedInUser", loggedInUser);
		return model;
	}

	@RequestMapping(value = "/auction/placeBid", method = RequestMethod.POST)
	public ModelAndView create(@Valid PlaceBidForm placeBidForm,
			BindingResult result, RedirectAttributes redirectAttributes,
			Principal principal) {
		if(!result.hasErrors()) {
		ModelAndView model = new ModelAndView("placeBid");
		String bidderName = principal.getName();
		
		Auction auction = auctionService.saveBidPrize(placeBidForm, placeBidForm.getId(), bidderName);

		// reset the place bid form
		this.placeBidForm = null;

		model = new ModelAndView("redirect:/auction?id=" + auction.getId());
		redirectAttributes.addFlashAttribute("confirmationMessage", "Bid placed successfully.");
		return model;
		}
		else {
			return new ModelAndView("auction?id=" + placeBidForm.getId());
		}

	}
	
	@ModelAttribute("placeBidForm")
	public PlaceBidForm placeBidForm() {
		if (placeBidForm == null) {
			placeBidForm = new PlaceBidForm();
		}
		return placeBidForm;
	}
}

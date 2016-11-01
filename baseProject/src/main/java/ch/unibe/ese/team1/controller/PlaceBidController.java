package ch.unibe.ese.team1.controller;

import java.security.Principal;

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

import ch.unibe.ese.team1.controller.pojos.forms.PlaceAuctionForm;
import ch.unibe.ese.team1.controller.pojos.forms.PlaceBidForm;
import ch.unibe.ese.team1.controller.service.AuctionService;
import ch.unibe.ese.team1.controller.service.BookmarkService;
import ch.unibe.ese.team1.controller.service.MessageService;
import ch.unibe.ese.team1.controller.service.UserService;
import ch.unibe.ese.team1.controller.service.VisitService;
import ch.unibe.ese.team1.model.Auction;

@Controller
public class PlaceBidController {

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
	public ModelAndView create(@Valid PlaceBidForm placeBidForm, BindingResult result,
			RedirectAttributes redirectAttributes, Principal principal) {
		if (!result.hasErrors()) {
			ModelAndView model = new ModelAndView("placeBid");
			Auction auction = auctionService.getAuctionById(placeBidForm.getId());
			if (auction.getPrize() >= placeBidForm.getPrize()) {
				model = new ModelAndView("redirect:/auction/placeBid?id=" + auction.getId());
				redirectAttributes.addFlashAttribute("warningMessage",
						"You failed. Bid prize must be higher than current prize");
				return model;
			} else {
				String bidderName = principal.getName();

				auction = auctionService.saveBidPrize(placeBidForm, placeBidForm.getId(), bidderName);

				// reset the place bid form
				this.placeBidForm = null;

				messageService.sendMessage(userService.findUserByUsername("System"), auction.getUser(), "New bid",
						"Someone placed a new bid in your auction for " + auction.getTitle() + ". Bid placed by "
								+ auction.getBidderName());

				model = new ModelAndView("redirect:/auction?id=" + auction.getId());
				redirectAttributes.addFlashAttribute("confirmationMessage", "Bid placed successfully.");
				return model;
			}
		} else {
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

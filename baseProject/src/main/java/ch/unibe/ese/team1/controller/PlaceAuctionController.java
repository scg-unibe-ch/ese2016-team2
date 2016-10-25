package ch.unibe.ese.team1.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.unibe.ese.team1.controller.pojos.PictureUploader;
import ch.unibe.ese.team1.controller.pojos.forms.PlaceAdForm;
import ch.unibe.ese.team1.controller.pojos.forms.PlaceAuctionForm;
import ch.unibe.ese.team1.controller.service.AdService;
import ch.unibe.ese.team1.controller.service.AlertService;
import ch.unibe.ese.team1.controller.service.AuctionService;
import ch.unibe.ese.team1.controller.service.BookmarkService;
import ch.unibe.ese.team1.controller.service.MessageService;
import ch.unibe.ese.team1.controller.service.UserService;
import ch.unibe.ese.team1.controller.service.VisitService;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.User;

@Controller
public class PlaceAuctionController {
	
	private PlaceAuctionForm placeAuctionForm;

	@Autowired
	private AlertService alertService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private MessageService messageService;

	@Autowired
	private VisitService visitService;

	@Autowired
	private BookmarkService bookmarkService;

	@Autowired
	private UserService userService;

	@Autowired
	private AuctionService auctionService;

	/** Shows the place auction form. */
	@RequestMapping(value = "/profile/placeAuction", method = RequestMethod.GET)
	public ModelAndView placeAd() throws IOException {
		ModelAndView model = new ModelAndView("placeAuction");
		
		return model;
	}
	
	@RequestMapping(value = "/profile/placeAuction", method = RequestMethod.POST)
	public ModelAndView create(@Valid PlaceAuctionForm placeAuctionForm,
			BindingResult result, RedirectAttributes redirectAttributes,
			Principal principal) {
		ModelAndView model = new ModelAndView("placeAuction");
		if (!result.hasErrors()) {
			String username = principal.getName();
			User user = userService.findUserByUsername(username);

			Auction auction = auctionService.saveFrom(placeAuctionForm, user);

			// reset the place ad form
			this.placeAuctionForm = null;

			model = new ModelAndView("redirect:/auction?id=" + auction.getId());
			redirectAttributes.addFlashAttribute("confirmationMessage",
					"Auction placed successfully. You can take a look at it below.");
		} else {
			model = new ModelAndView("placeAuction");
		}
		return model;
	}
	
	@RequestMapping(value = "/profile/placeAuction/validateEmail", method = RequestMethod.POST)
	@ResponseBody
	public String validateEmail(@RequestParam String email,
			@RequestParam String alreadyIn) {
		User user = userService.findUserByUsername(email);

		Boolean isAdded = auctionService.checkIfAlreadyAdded(email, alreadyIn);

		if (user == null) {
			return "This user does not exist, did your roommate register?";
		}
		if (isAdded) {
			return "You already added this person.";
		} else {
			return user.getEmail();
		}
	}
	
	@ModelAttribute("placeAuctionForm")
	public PlaceAuctionForm placeAuctionForm() {
		if (placeAuctionForm == null) {
			placeAuctionForm = new PlaceAuctionForm();
		}
		return placeAuctionForm;
	}

}

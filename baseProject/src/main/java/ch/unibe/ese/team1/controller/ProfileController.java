package ch.unibe.ese.team1.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.unibe.ese.team1.controller.pojos.PictureUploader;
import ch.unibe.ese.team1.controller.pojos.forms.EditProfileForm;
import ch.unibe.ese.team1.controller.pojos.forms.MessageForm;
import ch.unibe.ese.team1.controller.pojos.forms.RegisterForm;
import ch.unibe.ese.team1.controller.pojos.forms.SignupForm;
import ch.unibe.ese.team1.controller.service.AdService;
import ch.unibe.ese.team1.controller.service.AuctionService;
import ch.unibe.ese.team1.controller.service.RegisterService;
import ch.unibe.ese.team1.controller.service.SignupService;
import ch.unibe.ese.team1.controller.service.UserService;
import ch.unibe.ese.team1.controller.service.UserUpdateService;
import ch.unibe.ese.team1.controller.service.VisitService;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.PictureMeta;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.Visit;


/**
 * @Jerome
 * Add these imports for basic search functionality.
 */
import org.springframework.web.bind.annotation.ModelAttribute;
import ch.unibe.ese.team1.controller.pojos.forms.SearchForm;

/**
 * Handles all requests concerning user accounts and profiles.
 */
@Controller
public class ProfileController {
	
	public static final String IMAGE_DIRECTORY = "/img/test";

	/** Used for generating a JSON representation of a given object. */
	private ObjectMapper objectMapper;

	/**
	 * Used for uploading ad pictures. As long as the user did not place the ad
	 * completely, the same picture uploader is used. Once the ad was placed,
	 * this uploader is renewed.
	 */
	private PictureUploader pictureUploader;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private SignupService signupService;

	@Autowired
	private RegisterService registerService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserUpdateService userUpdateService;

	@Autowired
	private VisitService visitService;

	@Autowired
	private AdService adService;

	@Autowired
	private AuctionService auctionService;



	/**
	 * @Jerome
	 * Add these properties for basic search functionality.
	 */
	private SearchForm searchForm;


	/** Returns the login page. */
	@RequestMapping(value = "/login")
	public ModelAndView loginPage() {
		ModelAndView model = new ModelAndView("login");
		return model;
	}

	/** Returns the signup page. */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signupPage() {
		ModelAndView model = new ModelAndView("signup");
		model.addObject("signupForm", new SignupForm());
		return model;
	}

	/** Returns the register page. */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerPage(Principal principal) {
		ModelAndView model = new ModelAndView("register");
		model.addObject("registerForm", new RegisterForm());
		String username = principal.getName();
		User user = userService.findUserByUsername(username);
		model.addObject("editProfileForm", new EditProfileForm());
		model.addObject("currentUser", user);
		return model;
	}

	
	/** Validates the signup form and on success persists the new user. */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView signupResultPage(@Valid SignupForm signupForm, BindingResult bindingResult) {
		ModelAndView model;
		if (!bindingResult.hasErrors()) {
			signupService.saveFrom(signupForm);
			model = new ModelAndView("login");
			model.addObject("confirmationMessage", "Signup complete!");
		} else {
			model = new ModelAndView("signup");
			model.addObject("signupForm", signupForm);
		}
		return model;
	}
	
		

	/**
	 * Checks and returns whether a user with the given email already exists.
	 */
	@RequestMapping(value = "/signup/doesEmailExist", method = RequestMethod.POST)
	public @ResponseBody boolean doesEmailExist(@RequestParam String email) {
		return signupService.doesUserWithUsernameExist(email);
	}
	
	
	
	

	/** Shows the edit profile page. */
	@RequestMapping(value = "/profile/editProfile", method = RequestMethod.GET)
	public ModelAndView editProfilePage(Principal principal) {
		ModelAndView model = new ModelAndView("editProfile");
		String username = principal.getName();
		User user = userService.findUserByUsername(username);
		model.addObject("editProfileForm", new EditProfileForm());
		model.addObject("currentUser", user);
		return model;
	}

	/** Handles the request for editing the user profile. */
	@RequestMapping(value = "/profile/editProfile", method = RequestMethod.POST)
	public ModelAndView editProfileResultPage(@Valid EditProfileForm editProfileForm, BindingResult bindingResult,
			Principal principal) {
		ModelAndView model;
		String username = principal.getName();
		User user = userService.findUserByUsername(username);
		if (!bindingResult.hasErrors()) {
			userUpdateService.updateForm(editProfileForm);
			
			
			// reset the picture uploader
			this.pictureUploader = null;
			
			
			model = new ModelAndView("updatedProfile");
			model.addObject("message", "Your Profile has been updated!");
			model.addObject("currentUser", user);
			return model;
		} else {
			model = new ModelAndView("updatedProfile");
			model.addObject("message", "Something went wrong, please contact the WebAdmin if the problem persists!");
			return model;
		}
	}
	
	
	
	
	
	
	/**
	 * Uploads the pictures that are attached as multipart files to the request.
	 * The JSON representation, that is returned, is generated manually because
	 * the jQuery Fileupload plugin requires this special format.
	 * 
	 * @return A JSON representation of the uploaded files
	 */
	@RequestMapping(value = "/profile/editProfile/uploadPictures", method = RequestMethod.POST)
	public @ResponseBody String uploadPictures(
			MultipartHttpServletRequest request) {
		List<MultipartFile> pictures = new LinkedList<>();
		Iterator<String> iter = request.getFileNames();

		while (iter.hasNext()) {
			pictures.add(request.getFile(iter.next()));
		}

		if (pictureUploader == null) {
			String realPath = servletContext.getRealPath(IMAGE_DIRECTORY);
			pictureUploader = new PictureUploader(realPath, IMAGE_DIRECTORY);
		}
		List<PictureMeta> uploadedPicturesMeta = pictureUploader
				.upload(pictures);

		objectMapper = new ObjectMapper();
		String jsonResponse = "{\"files\": ";
		try {
			jsonResponse += objectMapper
					.writeValueAsString(uploadedPicturesMeta);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		jsonResponse += "}";
		return jsonResponse;
	}
	
	
	
	
	
	

	/** Returns the register page for upgrading the user account to a premium account. */
	@RequestMapping(value = "/profile/registerProfile", method = RequestMethod.GET)
	public ModelAndView registerProfilePage() {
		ModelAndView model = new ModelAndView("register");
		return model;
	}

	/** Handles the request for register the user account as premium account. */
	@RequestMapping(value = "/profile/registerProfile", method = RequestMethod.POST)
	public ModelAndView registerProfileResultPage(@Valid RegisterForm registerForm, BindingResult bindingResult,
			Principal principal) {
		ModelAndView model;
		String username = principal.getName();
		User user = userService.findUserByUsername(username);
		if (!bindingResult.hasErrors()) {
			registerService.updateForm(registerForm);
			model = new ModelAndView("updatedProfile");
			model.addObject("message", "Your Profile has been updated!");
			model.addObject("currentUser", user);
			return model;
		} else {
			return registerProfilePage();
		}
	}

	/** Displays the public profile of the user with the given id. */
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView user(@RequestParam("id") long id, Principal principal) {
		ModelAndView model = new ModelAndView("user");
		User user = userService.findUserById(id);
		if (principal != null) {
			String username = principal.getName();
			User user2 = userService.findUserByUsername(username);
			long principalID = user2.getId();
			model.addObject("principalID", principalID);
		}
		model.addObject("user", user);
		model.addObject("messageForm", new MessageForm());
		return model;
	}

	/** Displays the schedule page of the currently logged in user. */
	@RequestMapping(value = "/profile/schedule", method = RequestMethod.GET)
	public ModelAndView schedule(Principal principal) {
		ModelAndView model = new ModelAndView("schedule");
		User user = userService.findUserByUsername(principal.getName());

		// visits, i.e. when the user sees someone else's property
		Iterable<Visit> visits = visitService.getVisitsForUser(user);
		model.addObject("visits", visits);

		// presentations, i.e. when the user presents a property
		Iterable<Ad> usersAds = adService.getAdsByUser(user);
		Iterable<Auction> usersAuctions = auctionService.getAuctionsByUser(user);
		ArrayList<Visit> usersPresentations = new ArrayList<Visit>();

		for (Ad ad : usersAds) {
			try {
				usersPresentations.addAll((ArrayList<Visit>) visitService
						.getVisitsByAd(ad));
			} catch (Exception e) {
			}
		}
		for (Auction auction: usersAuctions) {
			try {
				usersPresentations.addAll((ArrayList<Visit>)
						visitService.getVisitsByAuction(auction));
			} catch (Exception e) {
			}
		}

		model.addObject("presentations", usersPresentations);
		return model;
	}

	/** Returns the visitors page for the visit with the given id. */
	@RequestMapping(value = "/profile/visitors", method = RequestMethod.GET)
	public ModelAndView visitors(@RequestParam("visit") long id) {
		ModelAndView model = new ModelAndView("visitors");
		Visit visit = visitService.getVisitById(id);
		Iterable<User> visitors = visit.getSearchers();

		model.addObject("visitors", visitors);

		Auction auction = visit.getAuction();
		Ad ad = visit.getAd();

		model.addObject("ad", ad);
		model.addObject("auction", auction);
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

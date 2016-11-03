package ch.unibe.ese.team1.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Iterator;
import java.util.LinkedList;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.unibe.ese.team1.controller.pojos.PictureUploader;
import ch.unibe.ese.team1.controller.pojos.forms.PlaceAuctionForm;
import ch.unibe.ese.team1.controller.service.AlertService;
import ch.unibe.ese.team1.controller.service.AuctionService;
import ch.unibe.ese.team1.controller.service.UserService;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.PictureMeta;
import ch.unibe.ese.team1.model.User;

@Controller
public class PlaceAuctionController {
	
	public static final String IMAGE_DIRECTORY = "/img/ads";

	/** Used for generating a JSON representation of a given object. */
	private ObjectMapper objectMapper;

	/**
	 * Used for uploading auction pictures. As long as the user did not place the auction
	 * completely, the same picture uploader is used. Once the auction was placed,
	 * this uploader is renewed.
	 */
	private PictureUploader pictureUploader;
	
	private PlaceAuctionForm placeAuctionForm;

	@Autowired
	private AlertService alertService;

	@Autowired
	private ServletContext servletContext;

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
	
	/**
	 * Uploads the pictures that are attached as multipart files to the request.
	 * The JSON representation, that is returned, is generated manually because
	 * the jQuery Fileupload plugin requires this special format.
	 * 
	 * @return A JSON representation of the uploaded files
	 */
	@RequestMapping(value = "/profile/placeAuction/uploadPictures", method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/profile/placeAuction", method = RequestMethod.POST)
	public ModelAndView create(@Valid PlaceAuctionForm placeAuctionForm,
			BindingResult result, RedirectAttributes redirectAttributes,
			Principal principal) {
		ModelAndView model = new ModelAndView("placeAuction");
		if (!result.hasErrors()) {
			String username = principal.getName();
			User user = userService.findUserByUsername(username);
			
			List<String> fileNames = pictureUploader.getFileNames();
			Auction auction = auctionService.saveFrom(placeAuctionForm, fileNames, user);
			
			// triggers all alerts that match the placed auction
			alertService.triggerAlerts(auction);

			// reset the place ad form
			this.placeAuctionForm = null;
			// reset the picture uploader
			this.pictureUploader = null;

			model = new ModelAndView("redirect:/auction?id=" + auction.getId());
			redirectAttributes.addFlashAttribute("confirmationMessage",
					"Auction placed successfully. You can take a look at it below.");
		} else {
			model = new ModelAndView("placeAuction");
		}
		return model;
	}
	
	/**
	 * Gets the descriptions for the pictures that were uploaded with the
	 * current picture uploader.
	 * 
	 * @return a list of picture descriptions or null if no pictures were
	 *         uploaded
	 */
	@RequestMapping(value = "/profile/placeAuction/getUploadedPictures", method = RequestMethod.POST)
	public @ResponseBody List<PictureMeta> getUploadedPictures() {
		if (pictureUploader == null) {
			return null;
		}
		return pictureUploader.getUploadedPictureMetas();
	}
	
	/**
	 * Deletes the uploaded picture at the given relative url (relative to the
	 * webapp folder).
	 */
	@RequestMapping(value = "/profile/placeAuction/deletePicture", method = RequestMethod.POST)
	public @ResponseBody void deleteUploadedPicture(@RequestParam String url) {
		if (pictureUploader != null) {
			String realPath = servletContext.getRealPath(url);
			pictureUploader.deletePicture(url, realPath);
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

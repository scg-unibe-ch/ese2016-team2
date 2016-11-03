package ch.unibe.ese.team1.controller;

import java.security.Principal;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.unibe.ese.team1.controller.pojos.PictureUploader;
import ch.unibe.ese.team1.controller.pojos.forms.PlaceAdForm;
import ch.unibe.ese.team1.controller.pojos.forms.PlaceAuctionForm;
import ch.unibe.ese.team1.controller.service.AdService;
import ch.unibe.ese.team1.controller.service.AlertService;
import ch.unibe.ese.team1.controller.service.AuctionService;
import ch.unibe.ese.team1.controller.service.EditAdService;
import ch.unibe.ese.team1.controller.service.EditAuctionService;
import ch.unibe.ese.team1.controller.service.UserService;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.PictureMeta;
import ch.unibe.ese.team1.model.User;

@Controller
public class EditAuctionController {

	private final static String IMAGE_DIRECTORY = PlaceAuctionController.IMAGE_DIRECTORY;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private AuctionService auctionService;

	@Autowired
	private EditAuctionService editAuctionService;

	@Autowired
	private UserService userService;

	@Autowired
	private AlertService alertService;

	private PictureUploader pictureUploader;

	private ObjectMapper objectMapper;

	/**
	 * Serves the page that allows the user to edit the auction with the given id.
	 */
	@RequestMapping(value = "/profile/editAuction", method = RequestMethod.GET)
	public ModelAndView editAuctionPage(@RequestParam long id, Principal principal) {
		ModelAndView model = new ModelAndView("editAuction");
		Auction auction = auctionService.getAuctionById(id);
		model.addObject("auction", auction);

		PlaceAuctionForm form = editAuctionService.fillForm(auction);

		model.addObject("placeAuctionForm", form);

		String realPath = servletContext.getRealPath(IMAGE_DIRECTORY);
		if (pictureUploader == null) {
			pictureUploader = new PictureUploader(realPath, IMAGE_DIRECTORY);
		}

		return model;
	}

	/**
	 * Processes the edit auction form and displays the result page to the user.
	 */
	@RequestMapping(value = "/profile/editAuction", method = RequestMethod.POST)
	public ModelAndView editAdPageWithForm(@Valid PlaceAuctionForm placeAuctionForm,
			BindingResult result, Principal principal,
			RedirectAttributes redirectAttributes, @RequestParam long auctionId) {
		ModelAndView model = new ModelAndView("placeAuction");
		if (!result.hasErrors()) {
			String username = principal.getName();
			User user = userService.findUserByUsername(username);

			String realPath = servletContext.getRealPath(IMAGE_DIRECTORY);
			if (pictureUploader == null) {
				pictureUploader = new PictureUploader(realPath, IMAGE_DIRECTORY);
			}
			List<String> fileNames = pictureUploader.getFileNames();
			Auction auction = editAuctionService.saveFrom(placeAuctionForm, fileNames, user, auctionId);

			// triggers all alerts that match the placed ad
			alertService.triggerAlerts(auction);

			// reset the picture uploader
			this.pictureUploader = null;

			model = new ModelAndView("redirect:/auction?id=" + auction.getId());
			redirectAttributes.addFlashAttribute("confirmationMessage",
					"Auction edited successfully. You can take a look at it below.");
		}

		return model;
	}

	/**
	 * Deletes the ad picture with the given id from the list of pictures from
	 * the ad, but not from the server.
	 */
	@RequestMapping(value = "/profile/editAuction/deletePictureFromAuction", method = RequestMethod.POST)
	public @ResponseBody void deletePictureFromAuction(@RequestParam long auctionId,
			@RequestParam long pictureId) {
		editAuctionService.deletePictureFromAuction(auctionId, pictureId);
	}

	/**
	 * Gets the descriptions for the pictures that were uploaded with the
	 * current picture uploader.
	 * 
	 * @return a list of picture descriptions or null if no pictures were
	 *         uploaded
	 */
	@RequestMapping(value = "/profile/editAuction/getUploadedPictures", method = RequestMethod.POST)
	public @ResponseBody List<PictureMeta> getUploadedPictures() {
		if (pictureUploader == null) {
			return null;
		}
		return pictureUploader.getUploadedPictureMetas();
	}

	/**
	 * Uploads the pictures that are attached as multipart files to the request.
	 * The JSON representation, that is returned, is generated manually because
	 * the jQuery Fileupload plugin requires this special format.
	 * 
	 * @return A JSON representation of the uploaded files
	 */
	@RequestMapping(value = "/profile/editAuction/uploadPictures", method = RequestMethod.POST)
	public @ResponseBody String uploadPictures(
			MultipartHttpServletRequest request) {
		List<MultipartFile> pictures = new LinkedList<>();
		Iterator<String> iter = request.getFileNames();

		while (iter.hasNext()) {
			pictures.add(request.getFile(iter.next()));
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

	/**
	 * Deletes the uploaded picture at the given relative url (relative to the
	 * webapp folder).
	 */
	@RequestMapping(value = "/profile/editAuction/deletePicture", method = RequestMethod.POST)
	public @ResponseBody void deleteUploadedPicture(@RequestParam String url) {
		if (pictureUploader != null) {
			String realPath = servletContext.getRealPath(url);
			pictureUploader.deletePicture(url, realPath);
		}
	}
	
}

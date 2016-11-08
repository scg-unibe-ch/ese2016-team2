package ch.unibe.ese.team1.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import ch.unibe.ese.team1.controller.service.AuctionService;
import ch.unibe.ese.team1.controller.service.MessageService;
import ch.unibe.ese.team1.controller.service.UserService;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.dao.AuctionDao;

@Configuration
@EnableScheduling
public class AuctionSchedule {

	@Autowired
	AuctionService auctionService;
	
	@Autowired
	AuctionDao auctionDao;

	@Autowired
	MessageService messageService;

	@Autowired
	UserService userService;

	@Scheduled(initialDelay=60000, fixedRate = 10000)
	public void updateAuctions() {
		Iterable<Auction> auctions = auctionService.getAllAds();
		Date timeNow = new Date();
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm, dd.MM.yyyy");
		for (Auction auction : auctions) {
			try {
				Date dateAuctionEnd = timeFormat.parse(auction.getEndTime());
				if (!auction.getAuctionEnded() && auction.getBidderName() != null && timeNow.after(dateAuctionEnd)) {
					messageService.sendMessage(userService.findUserByUsername("System"), auction.getUser(),
							"Auction ended", "Congratulations! \n" + auction.getBidderName()
									+ " wins the auction and buys your estate for " + auction.getPrize() + " CHF.");
					messageService.sendMessage(userService.findUserByUsername("System"),
							userService.findUserByUsername(auction.getBidderName()), "Auction ended!",
							"Congratulations! \nYou won the auction for the estate " + auction.getTitle()
									+ ". You will have to pay " + auction.getPrize() + " CHF to "
									+ auction.getUser().getUsername() + ".");
					auction.setAuctionEnded(true);
					auctionDao.save(auction);
				} else if (!auction.getAuctionEnded() && timeNow.after(dateAuctionEnd)) {
					messageService.sendMessage(userService.findUserByUsername("System"), auction.getUser(), 
							"Auction ended.", "No one has placed a bid.");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

	}

}

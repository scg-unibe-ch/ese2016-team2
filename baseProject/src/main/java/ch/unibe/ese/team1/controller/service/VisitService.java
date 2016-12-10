package ch.unibe.ese.team1.controller.service;

import java.util.ArrayList;
import java.util.Iterator;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Advertisement;
import ch.unibe.ese.team1.model.Auction;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.Visit;
import ch.unibe.ese.team1.model.VisitEnquiry;
import ch.unibe.ese.team1.model.VisitEnquiryState;
import ch.unibe.ese.team1.model.dao.VisitDao;
import ch.unibe.ese.team1.model.dao.VisitEnquiryDao;

/** Provides operations for getting and saving visits */
@Service
public class VisitService {

	@Autowired
	private VisitDao visitDao;

	@Autowired
	VisitEnquiryDao visitEnquiryDao;

	@Autowired
	EnquiryService enquiryService;

	/**
	 * Returns all possible visits of an advertisement.
	 * 
	 * @return an Iterable of all matching visits
	 */
	@Transactional
	public Iterable<Visit> getVisitsByAd(Ad ad) {
		return visitDao.findByAdOrderByStartTimestampAsc(ad);
	}

	/**
	 * Returns all possible visits of an advertisement.
	 * 
	 * @return an Iterable of all matching visits
	 */
	@Transactional
	public Iterable<Visit> getVisitsByAuction(Auction auction) {
		return visitDao.findByAuctionOrderByStartTimestampAsc(auction);
	}

	/** Returns the visit with the given id. */
	@Transactional
	public Visit getVisitById(long id) {
		return visitDao.findOne(id);
	}

	/**
	 * Returns all visits that a user has applied for and was also accepted to.
	 */
	@Transactional
	public Iterable<Visit> getVisitsForUser(User user) {
		// all enquiries sent by user
		Iterable<VisitEnquiry> usersEnquiries = visitEnquiryDao.findBySender(user);
		// all visits user has been accepted for
		ArrayList<Visit> usersVisits = new ArrayList<Visit>();
		// fill the list
		for (VisitEnquiry enquiry : usersEnquiries) {
			if (enquiry.getState() == VisitEnquiryState.ACCEPTED)
				(usersVisits).add(enquiry.getVisit());
		}
		return usersVisits;
	}

	/** Returns all visitors for the visit with the given id. */
	public Iterable<User> getVisitorsForVisit(long id) {
		Visit visit = visitDao.findOne(id);
		return visit.getSearchers();
	}

	public void delete(Advertisement ad) {
		if (!ad.getAuction()) {
			Iterable<Visit> visitsForAuction = getVisitsByAd((Ad) ad);
			Iterator<Visit> iterator = visitsForAuction.iterator();
			while (iterator.hasNext()) {
				Iterable<VisitEnquiry> visitEnquiryList = visitEnquiryDao.findByVisit(iterator.next());
				Iterable<VisitEnquiry> visitEnquiriesByUser = enquiryService.getEnquiriesByRecipient(ad.getUser());
				for (VisitEnquiry tempVisitEnquiry : visitEnquiriesByUser) {
					for (VisitEnquiry visitEnquiry : visitEnquiryList) {
						if (tempVisitEnquiry.getId() == visitEnquiry.getId()) {
							visitEnquiryDao.delete(visitEnquiry.getId());
						}
					}
				}
			}
		} else if (ad.getAuction()) {
			Iterable<Visit> visitsForAuction = getVisitsByAuction((Auction) ad);
			Iterator<Visit> iterator = visitsForAuction.iterator();
			while (iterator.hasNext()) {
				Iterable<VisitEnquiry> visitEnquiryList = visitEnquiryDao.findByVisit(iterator.next());
				Iterable<VisitEnquiry> visitEnquiriesByUser = enquiryService.getEnquiriesByRecipient(ad.getUser());
				for (VisitEnquiry tempVisitEnquiry : visitEnquiriesByUser) {
					for (VisitEnquiry visitEnquiry : visitEnquiryList) {
						if (tempVisitEnquiry.getId() == visitEnquiry.getId()) {
							visitEnquiryDao.delete(visitEnquiry.getId());
						}
					}
				}
			}
		}
	}
}

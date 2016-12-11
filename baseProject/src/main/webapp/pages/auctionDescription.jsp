<%@page import="ch.unibe.ese.team1.model.Ad"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>


<%-- check if user is logged in --%>
<security:authorize var="loggedIn" url="/profile" />


<c:import url="template/~top.jsp" />
<c:import url="template/~header_details_wo_search.jsp" />

<!-- format the dates -->
<fmt:formatDate value="${shownAuction.moveInDate}"
	var="formattedMoveInDate" type="date" pattern="dd.MM.yyyy" />
<fmt:formatDate value="${shownAuction.creationDate}"
	var="formattedCreationDate" type="date" pattern="dd.MM.yyyy" />


<div class="container sidebar">
	<div class="row">
		<div class="tile tile-full">

			<div class="action action-icon action-sidebar">
				<span id="js-sidebar-icon" class="fa fa-info fa-2x"></span>
			</div>

		</div>
	</div>

	<div class="row">

		<c:choose>
			<c:when test="${loggedIn}">

				<div class="tile tile-half action action-tile">
					<a href="/user?id=${shownAuction.user.id}">Visit profile</a>
				</div>

				<div class="tile tile-half action action-tile">
					<c:if test="${loggedInUserEmail != shownAuction.user.username }">
						<button id="newMsg" type="button">Contact Advertiser</button>
					</c:if>
					<c:if test="${loggedInUserEmail == shownAuction.user.username }">
						<%-- <a href="<c:url value='/profile/editAd?id=${shownAuction.id}' />">Edit auction</a> --%>
					</c:if>
				</div>

			</c:when>
			<c:otherwise>

				<div class="tile tile-half action action-tile action-medium">
					<a href="/login">Sign in to see profile</a>
				</div>

				<div class="tile tile-half action action-tile action-medium">
					<a href="/login">Sign in to reach vendor</a>
				</div>

			</c:otherwise>
		</c:choose>

	</div>

	<div class="row">
		<div class="tile tile-full">
			<form class="form form-message">
				<input type="text" id="msgSubject" placeholder="Subject *" />
				<textarea id="msgTextarea" placeholder="Message"></textarea>

				<div class="row">
					<div class="tile tile-half">
						<button class="submit-state-before" type="button" id="messageSend">
							<span class="submit-before">Send</span>
							<span class="submit-after">Delivered</span>
							<span class="fa fa-circle-o-notch fa-spin fa-fw submitting"></span>
							<span class="sr-only">Sending...</span>
						</button>
					</div>
					<div class="tile tile-half">
						<button type="reset" id="messageCancel">Cancel</button>
					</div>
				</div>
			</form>
		</div>
	</div>

	<%--
	@Jerome
	TODO: Ask if needed.
	<div id="confirmationDialog">
		<form>
		<p>Send enquiry to advertiser?</p>
		<button type="button" id="confirmationDialogSend">Send</button>
		<button type="button" id="confirmationDialogCancel">Cancel</button>
		</form>
	</div> --%>

	<div class="row">
		<div class="tile tile-three-quarter">
			<h2>
				<%--
				@Jerome
				TODO: Has an ad a premium property??
				<c:if test="">
					<span class="fa fa-gavel" aria-hidden="true"></span>
				</c:if> --%>
				<span class="fa fa-gavel" aria-hidden="true"></span>
				&nbsp;&nbsp;${shownAuction.title}</h2>
		</div>
		<div class="tile tile-quarter action action-tile action-icon">
			<c:choose>
				<c:when test="${loggedIn}">
					<c:if test="${loggedInUserEmail != shownAuction.user.username }">
						<a class="right" id="bookmarkButton" title="Bookmark property">
							<span class="fa fa-bookmark fa-2x action-inactive-color"></span>
						</a>
					</c:if>
				</c:when>
			</c:choose>
		</div>
	</div>

	<div class="container-scroll">

		<div class="row">
			<div class="tile tile-full">
				<table>
					<tr>
						<td>Type</td>
						<td>${shownAuction.roomType}</td>
					</tr>

					<tr>
						<td>Address</td>
						<td>
							<a target="_blank" href="http://maps.google.com/?q=${shownAuction.street}, ${shownAuction.zipcode}, ${shownAuction.city}">${shownAuction.street},
									${shownAuction.zipcode} ${shownAuction.city}</a>
						</td>
					</tr>

					<tr>
						<td>Available from</td>
						<td>${formattedMoveInDate}</td>
					</tr>

					<tr>
						<td>Move-out Date</td>
						<td>${formattedMoveOutDate}</td>
					</tr>

					<tr>
						<td>Monthly Rent</td>
						<td>${shownAuction.prize}&#32;CHF</td>
					</tr>

					<tr>
						<td>Square Meters</td>
						<td>${shownAuction.squareFootage}&#32;m²</td>
					</tr>
					<tr>
						<td>Ad created on</td>
						<td>${formattedCreationDate}</td>
					</tr>
				<%-- </table>

				<table> --%>
					<tr>
						<td>Smoking inside allowed</td>
						<td>
							<c:choose>
								<c:when test="${shownAuction.smokers}"><span class="fa fa-plus base-color"></span></c:when>
								<c:otherwise><span class="fa fa-minus base-color-opposite"></span></c:otherwise>
							</c:choose>
						</td>
					</tr>

					<tr>
						<td>Animals allowed</td>
						<td>
							<c:choose>
								<c:when test="${shownAuction.animals}"><span class="fa fa-plus base-color"></span></c:when>
								<c:otherwise><span class="fa fa-minus base-color-opposite"></span></c:otherwise>
							</c:choose>
						</td>
					</tr>

					<tr>
						<td>Furnished Room</td>
						<td>
							<c:choose>
								<c:when test="${shownAuction.furnished}"><span class="fa fa-plus base-color"></span></c:when>
								<c:otherwise><span class="fa fa-minus base-color-opposite"></span></c:otherwise>
							</c:choose>
						</td>
					</tr>

					<tr>
						<td>WiFi available</td>
						<td>
							<c:choose>
								<c:when test="${shownAuction.internet}"><span class="fa fa-plus base-color"></span></c:when>
								<c:otherwise><span class="fa fa-minus base-color-opposite"></span></c:otherwise>
							</c:choose>
						</td>
					</tr>

					<tr>
						<td>Cable TV</td>
						<td>
							<c:choose>
								<c:when test="${shownAuction.cable}"><span class="fa fa-plus base-color"></span></c:when>
								<c:otherwise><span class="fa fa-minus base-color-opposite"></span></c:otherwise>
							</c:choose>
						</td>
					</tr>

					<tr>
						<td>Garage</td>
						<td>
							<c:choose>
								<c:when test="${shownAuction.garage}"><span class="fa fa-plus base-color"></span></c:when>
								<c:otherwise><span class="fa fa-minus base-color-opposite"></span></c:otherwise>
							</c:choose>
						</td>
					</tr>

					<tr>
						<td>Cellar</td>
						<td>
							<c:choose>
								<c:when test="${shownAuction.cellar}"><span class="fa fa-plus base-color"></span></c:when>
								<c:otherwise><span class="fa fa-minus base-color-opposite"></span></c:otherwise>
							</c:choose>
						</td>
					</tr>

					<tr>
						<td>Balcony</td>
						<td>
							<c:choose>
								<c:when test="${shownAuction.balcony}"><span class="fa fa-plus base-color"></span></c:when>
								<c:otherwise><span class="fa fa-minus base-color-opposite"></span></c:otherwise>
							</c:choose>
						</td>
					</tr>

					<tr>
						<td>Garden</td>
						<td>
							<c:choose>
								<c:when test="${shownAuction.garden}"><span class="fa fa-plus base-color"></span></c:when>
								<c:otherwise><span class="fa fa-minus base-color-opposite"></span></c:otherwise>
							</c:choose>
						</td>
					</tr>

				</table>

			</div>
		</div>

		<div class="row">
			<div class="tile tile-full">
					<c:choose>
						<c:when test="${empty shownAuction.bidderName}">
							<table id="adDescTable" class="adDescDiv">
								<tr>
									<td>Minimal bid prize:</td>
									<td>${shownAuction.prize}&#32;CHF</td>
								</tr>
							</table>
							<c:choose>
								<c:when test="${loggedIn}">
									<div class="row">
										<div class="tile tile-full action action-tile">
											<c:if test="${loggedInUserEmail != shownAuction.user.username}">
												<a
													href="<c:url value='/auction/placeBid?id=${shownAuction.id}' />">
													Place new bid
												</a>
											</c:if>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="row">
										<div class="tile tile-full action action-tile">
											<a href="/login">
												Login to place new bid
											</a>
										</div>
									</div>
								</c:otherwise>
							</c:choose>

						</c:when>
						<c:otherwise>
							<table id="adDescTable" class="adDescDiv">
								<tr>
									<td>Highest bid:</td>
									<td>${shownAuction.prize}&#32;CHF</td>
								</tr>
							</table>
							<c:choose>
								<c:when test="${loggedIn}">
									<c:choose>
										<c:when
											test="${loggedInUserEmail != shownAuction.user.username}">
											<div class="row">
												<div class="tile tile-full action action-tile">
													<a
														href="<c:url value='/auction/placeBid?id=${shownAuction.id}' />">
														Place new bid
													</a>
												</div>
											</div>
										</c:when>
										<c:otherwise>
										By: ${shownAuction.bidderName}
									</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<div class="row">
										<div class="tile tile-full action">
											<a href="/login">
												Login to place new bid
											</a>
										</div>
									</div>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</table>
			</div>
		</div>


		<div class="row">
			<div class="tile tile-full">
				<h3>Description</h3>
				<p>${shownAuction.roomDescription}</p>
			</div>
		</div>

		<div class="row">
			<div class="tile tile-full">
				<h3>Preferences</h3>
				<p>${shownAuction.preferences}</p>
			</div>
		</div>

		<div class="row">
			<div class="tile tile-full">

				<c:if test="${loggedInUserEmail != shownAuction.user.username}">
					<h3 class="row-h3">Viewing times</h3>
				</c:if>

				<div class="row viewing-times">
					<div class="tile tile-full action action-tile">

						<c:choose>
							<c:when test="${loggedIn}">
								<c:if test="${loggedInUserEmail != shownAuction.user.username}">
									<c:forEach items="${visits}" var="visit">
										<div class="row">
											<div class="tile tile-full">
												<button class="enquiry" type="button" data-id="${visit.id}">
													<fmt:formatDate value="${visit.startTimestamp}" pattern="dd-MM-yyyy " />
													&nbsp; from
													<fmt:formatDate value="${visit.startTimestamp}" pattern=" HH:mm " />
													until
													<fmt:formatDate value="${visit.endTimestamp}" pattern=" HH:mm" />
												</button>
												<div class="row enquiry-confirm">
													<div class="tile tile-half">
														<button class="action-confirm">Confirm</button>
													</div>
													<div class="tile tile-half">
														<button class="action-cancel">Cancel</button>
													</div>
												</div>
											</div>
										</div>
									</c:forEach>
								</c:if>
							</c:when>
							<c:otherwise>

								<a href="/login">Sign in to see viewing times</a>

							</c:otherwise>
						</c:choose>

					</div>
				</div>

			</div>
		</div>

	</div> <%-- .container-scroll END --%>

</div>

<main role="main">

	<%--
		@Jerome
		For the outer div slider or blender may be chosen as class. It then does
		what it says.
	 --%>
	<div class="slider slider-blender-full">
		<ul class="slides">

			<c:forEach items="${shownAuction.pictures}" var="picture">

				<li class="slide" style="background-image: url(${picture.filePath})"></li>

			</c:forEach>

		</ul> <%-- .slides END --%>
		<div class="row transport">
			<div class="tile tile-half transport-prev"></div>
			<div class="tile tile-half transport-next"></div>
		</div>
	</div> <%-- .[slider|blender] END --%>

</main>

<%-- <c:import url="template/~footer.jsp" /> --%>
<c:import url="template/~bottom_shown_ad.jsp">
	<c:param name="js" value="auctionDescription" />
</c:import>

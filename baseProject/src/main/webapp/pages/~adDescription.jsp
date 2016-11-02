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
<c:import url="template/~header.jsp" />

<!-- format the dates -->
<fmt:formatDate value="${shownAd.moveInDate}" var="formattedMoveInDate"
	type="date" pattern="dd.MM.yyyy" />
<fmt:formatDate value="${shownAd.creationDate}" var="formattedCreationDate"
	type="date" pattern="dd.MM.yyyy" />
<c:choose>
	<c:when test="${empty shownAd.moveOutDate }">
		<c:set var="formattedMoveOutDate" value="unlimited" />
	</c:when>
	<c:otherwise>
		<fmt:formatDate value="${shownAd.moveOutDate}"
			var="formattedMoveOutDate" type="date" pattern="dd.MM.yyyy" />
	</c:otherwise>
</c:choose>


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
					<a href="/user?id=${shownAd.user.id}">Visit profile</a>
				</div>

				<div class="tile tile-half action action-tile">
					<c:if test="${loggedInUserEmail != shownAd.user.username }">
						<button id="newMsg" type="button">Contact Advertiser</button>
					</c:if>
					<c:if test="${loggedInUserEmail == shownAd.user.username }">
						<a href="<c:url value='/profile/editAd?id=${shownAd.id}' />">Edit ad</a>
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
						<button type="button" id="messageSend">Send</button>
					</div>
					<div class="tile tile-half">
						<button type="button" id="messageCancel">Cancel</button>
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
		<div class="tile tile-full">
			<h2>${shownAd.title}</h2>
		</div>
	</div>

	<div class="container-scroll">

		<div class="row">
			<div class="tile tile-full">
				<table id="adDescTable" class="adDescDiv">
					<tr>
						<td>Type</td>
						<td>${shownAd.roomType}</td>
					</tr>

					<tr>
						<td>Address</td>
						<td>
							<a target="_blank" href="http://maps.google.com/?q=${shownAd.street}, ${shownAd.zipcode}, ${shownAd.city}">${shownAd.street},
									${shownAd.zipcode} ${shownAd.city}</a>
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
						<td>${shownAd.prize}&#32;CHF</td>
					</tr>

					<tr>
						<td>Square Meters</td>
						<td>${shownAd.squareFootage}&#32;m²</td>
					</tr>
					<tr>
						<td>Ad created on</td>
						<td>${formattedCreationDate}</td>
					</tr>
				</table>

			</div>
		</div>

		<div class="row">
			<div class="tile tile-full">
				<h3>Description</h3>
				<p>${shownAd.roomDescription}</p>
			</div>
		</div>

		<div class="row">
			<div class="tile tile-full">
				<h3>Preferences</h3>
				<p>${shownAd.preferences}</p>
			</div>
		</div>

		<div class="row">
			<div class="tile tile-full">

				<h3 class="row-h3">Visiting times</h3>

				<c:forEach items="${visits }" var="visit">

					<div class="row">
						<div class="tile tile-full action action-tile">

							<c:choose>
								<c:when test="${loggedIn}">
									<c:if test="${loggedInUserEmail != shownAd.user.username}">

										<button type="button" data-id="${visit.id}">
											<fmt:formatDate value="${visit.startTimestamp}" pattern="dd-MM-yyyy " />
											&nbsp; from
											<fmt:formatDate value="${visit.startTimestamp}" pattern=" HH:mm " />
											until
											<fmt:formatDate value="${visit.endTimestamp}" pattern=" HH:mm" />
										</button>

									</c:if>
								</c:when>
								<c:otherwise>

									<a href="/login" data-id="${visit.id}">Login to send enquiries</a>

								</c:otherwise>
							</c:choose>

						</div>
					</div>

				</c:forEach>
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

			<c:forEach items="${shownAd.pictures}" var="picture">

				<li class="slide" style="background-image: url(${picture.filePath})"></li>

			</c:forEach>

		</ul> <%-- .slides END --%>
	</div> <%-- .[slider|blender] END --%>

</main>

<c:import url="template/~footer.jsp" />
<c:import url="template/~bottom_ad-details.jsp">
	<c:param name="js" value="ad-details" />
</c:import>



<%--



	<c:choose>
		<c:when test="${loggedIn}">
			<a class="right" id="bookmarkButton">Bookmark Ad</a>
		</c:when>
	</c:choose>


<hr class="clearBoth" />

<section>
	<table id="checkBoxTable" class="adDescDiv">
		<tr>
			<td><h2>Smoking inside allowed</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.smokers}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Animals allowed</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.animals}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Furnished Room</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.furnished}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>WiFi available</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.internet}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Cable TV</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.cable}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Garage</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.garage}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Cellar</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.cellar}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Balcony</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.balcony}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Garden</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.garden}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

	</table>
</section>

<div class="clearBoth"></div>
<br>

<table id="advertiserTable" class="adDescDiv">
	<tr>
	<td><h2>Advertiser</h2><br /></td>
	</tr>

	<tr>
		<td><c:choose>
				<c:when test="${shownAd.user.picture.filePath != null}">
					<img src="${shownAd.user.picture.filePath}">
				</c:when>
				<c:otherwise>
					<img src="/img/avatar.png">
				</c:otherwise>
			</c:choose></td>

		<td>${shownAd.user.username}</td>

	</tr>
</table>
--%>

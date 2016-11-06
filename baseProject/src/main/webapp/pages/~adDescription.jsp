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
		<div class="tile tile-three-quarter">
			<h2>${shownAd.title}</h2>
		</div>
		<div class="tile tile-quarter action action-tile action-icon">
			<c:choose>
				<c:when test="${loggedIn}">
					<a class="right" id="bookmarkButton" title="Bookmark property">
						<span class="fa fa-bookmark fa-2x action-inactive-color"></span>
					</a>
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
				<%-- </table>

				<table> --%>
					<tr>
						<td>Smoking inside allowed</td>
						<td>
							<c:choose>
								<c:when test="${shownAd.smokers}"><span class="fa fa-plus base-color"></span></c:when>
								<c:otherwise><span class="fa fa-minus base-color-opposite"></span></c:otherwise>
							</c:choose>
						</td>
					</tr>

					<tr>
						<td>Animals allowed</td>
						<td>
							<c:choose>
								<c:when test="${shownAd.animals}"><span class="fa fa-plus base-color"></span></c:when>
								<c:otherwise><span class="fa fa-minus base-color-opposite"></span></c:otherwise>
							</c:choose>
						</td>
					</tr>

					<tr>
						<td>Furnished Room</td>
						<td>
							<c:choose>
								<c:when test="${shownAd.furnished}"><span class="fa fa-plus base-color"></span></c:when>
								<c:otherwise><span class="fa fa-minus base-color-opposite"></span></c:otherwise>
							</c:choose>
						</td>
					</tr>

					<tr>
						<td>WiFi available</td>
						<td>
							<c:choose>
								<c:when test="${shownAd.internet}"><span class="fa fa-plus base-color"></span></c:when>
								<c:otherwise><span class="fa fa-minus base-color-opposite"></span></c:otherwise>
							</c:choose>
						</td>
					</tr>

					<tr>
						<td>Cable TV</td>
						<td>
							<c:choose>
								<c:when test="${shownAd.cable}"><span class="fa fa-plus base-color"></span></c:when>
								<c:otherwise><span class="fa fa-minus base-color-opposite"></span></c:otherwise>
							</c:choose>
						</td>
					</tr>

					<tr>
						<td>Garage</td>
						<td>
							<c:choose>
								<c:when test="${shownAd.garage}"><span class="fa fa-plus base-color"></span></c:when>
								<c:otherwise><span class="fa fa-minus base-color-opposite"></span></c:otherwise>
							</c:choose>
						</td>
					</tr>

					<tr>
						<td>Cellar</td>
						<td>
							<c:choose>
								<c:when test="${shownAd.cellar}"><span class="fa fa-plus base-color"></span></c:when>
								<c:otherwise><span class="fa fa-minus base-color-opposite"></span></c:otherwise>
							</c:choose>
						</td>
					</tr>

					<tr>
						<td>Balcony</td>
						<td>
							<c:choose>
								<c:when test="${shownAd.balcony}"><span class="fa fa-plus base-color"></span></c:when>
								<c:otherwise><span class="fa fa-minus base-color-opposite"></span></c:otherwise>
							</c:choose>
						</td>
					</tr>

					<tr>
						<td>Garden</td>
						<td>
							<c:choose>
								<c:when test="${shownAd.garden}"><span class="fa fa-plus base-color"></span></c:when>
								<c:otherwise><span class="fa fa-minus base-color-opposite"></span></c:otherwise>
							</c:choose>
						</td>
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

				<h3 class="row-h3">Viewing times</h3>

				<div class="row viewing-times">
					<div class="tile tile-full action action-tile">

						<c:choose>
							<c:when test="${loggedIn}">
								<c:if test="${loggedInUserEmail != shownAd.user.username}">
									<c:forEach items="${visits}" var="visit">
										<button type="button" data-id="${visit.id}">
											<fmt:formatDate value="${visit.startTimestamp}" pattern="dd-MM-yyyy " />
											&nbsp; from
											<fmt:formatDate value="${visit.startTimestamp}" pattern=" HH:mm " />
											until
											<fmt:formatDate value="${visit.endTimestamp}" pattern=" HH:mm" />
										</button>
									</c:forEach>
								</c:if>
							</c:when>
							<c:otherwise>

								<a href="/login" data-id="${visit.id}">Sign in to see viewing times</a>

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

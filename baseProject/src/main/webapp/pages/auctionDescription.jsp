<%@page import="ch.unibe.ese.team1.model.Ad"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<!-- check if user is logged in -->
<security:authorize var="loggedIn" url="/profile" />

<c:import url="template/header.jsp" />

<pre><a href="/">Home</a>   &gt;   <a href="/profile/myRooms">My Rooms</a>   &gt;   Auction Description</pre>

<script src="/js/image_slider.js"></script>
<script src="/js/adDescription.js"></script>

<!-- format the dates -->
<fmt:formatDate value="${shownAuction.moveInDate}"
	var="formattedMoveInDate" type="date" pattern="dd.MM.yyyy" />
<fmt:formatDate value="${shownAuction.creationDate}"
	var="formattedCreationDate" type="date" pattern="dd.MM.yyyy" />


<h1 id="shownAdTitle">${shownAuction.title}</h1>

<section>
	<c:choose>
		<c:when test="${loggedIn}">
			<c:if test="${loggedInUserEmail == shownAuction.user.username }">
				<a href="<c:url value='/profile/editAuction?id=${shownAuction.id}' />">
					<button type="button">Edit Auction</button>
				</a>
			</c:if>
		</c:when>
	</c:choose>
	<br> <br>

	<table id="adDescTable" class="adDescDiv">
		<tr>
			<td><h2>Type</h2></td>
			<td>${shownAuction.roomType}</td>
		</tr>

		<tr>
			<td><h2>Address</h2></td>
			<td><a class="link"
				href="http://maps.google.com/?q=${shownAuction.street}, ${shownAuction.zipcode}, ${shownAuction.city}">${shownAuction.street},
					${shownAuction.zipcode} ${shownAuction.city}</a></td>
		</tr>

		<tr>
			<td><h2>Available from</h2></td>
			<td>${formattedMoveInDate}</td>
		</tr>

		<tr>
			<td><h2>Square Meters</h2></td>
			<td>${shownAuction.squareFootage}&#32;m²</td>
		</tr>
		<tr>
			<td><h2>Auction created on</h2></td>
			<td>${formattedCreationDate}</td>
		</tr>
		<tr>
			<td><h2>Auction end-date</h2></td>
			<td>${shownAuction.endTime}</td>
		</tr>
	</table>
</section>

<div id="image-slider">
	<div id="left-arrow">
		<img src="/img/left-arrow.png" />
	</div>
	<div id="images">
		<c:forEach items="${shownAuction.pictures}" var="picture">
			<img src="${picture.filePath}" />
		</c:forEach>
	</div>
	<div id="right-arrow">
		<img src="/img/right-arrow.png" />
	</div>
</div>

<hr class="clearBoth" />

<section>
	<table id="adDescTable" class="adDescDiv">
		<c:choose>
			<c:when test="${empty shownAuction.bidderName}">
				<tr>
					<td><h2>Minimal bid prize:</h2></td>
					<td>${shownAuction.prize}&#32;CHF</td>
				</tr>
				<td><c:choose>
							<c:when test="${loggedIn}">
								<c:if test="${loggedInUserEmail != shownAuction.user.username}">
									<a
										href="<c:url value='/auction/placeBid?id=${shownAuction.id}' />">
										<button type="button">Place new bid</button>
									</a>
								</c:if>
							</c:when>
							<c:otherwise>
								<a href="/login"><button class="thinInactiveButton"
										type="button">Login to place new bid</button></a>
							</c:otherwise>
						</c:choose></td>
			</c:when>
			<c:otherwise>
				<tr>
					<td><h2>Highest bid:</h2></td>
					<td>${shownAuction.prize}&#32;CHF</td>
				</tr>
				<tr>
					<td><c:choose>
							<c:when test="${loggedIn}">
								<c:choose>
								<c:when test="${loggedInUserEmail != shownAuction.user.username}">
									<a
										href="<c:url value='/auction/placeBid?id=${shownAuction.id}' />">
										<button type="button">Place new bid</button>
									</a>
								</c:when>
								<c:otherwise>
									By: ${shownAuction.bidderName}
								</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<a href="/login"><button class="thinInactiveButton"
										type="button">Login to place new bid</button></a>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
</section>
<br />

<hr class="clearBoth" />

<section>
	<div id="descriptionTexts">
		<div class="adDescDiv">
			<h2>Room Description</h2>
			<p>${shownAuction.roomDescription}</p>
		</div>
		<br />
		<div class="adDescDiv">
			<h2>Preferences</h2>
			<p>${shownAuction.preferences}</p>
		</div>
		<br />
		<!-- <div id="visitList" class="adDescDiv">
			<h2>Visiting times</h2>
			<table>
				<c:forEach items="${visits }" var="visit">
					<tr>
						<td>
							<fmt:formatDate value="${visit.startTimestamp}" pattern="dd-MM-yyyy " />
							&nbsp; from
							<fmt:formatDate value="${visit.startTimestamp}" pattern=" HH:mm " />
							until
							<fmt:formatDate value="${visit.endTimestamp}" pattern=" HH:mm" />
						</td>
						<td><c:choose>
								<c:when test="${loggedIn}">
									<c:if test="${loggedInUserEmail != shownAuction.user.username}">
										<button class="thinButton" type="button" data-id="${visit.id}">Send
											enquiry to advertiser</button>
									</c:if>
								</c:when>
								<c:otherwise>
									<a href="/login"><button class="thinInactiveButton" type="button"
										data-id="${visit.id}">Login to send enquiries</button></a>
								</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<br /> -->
	</div>
	
	

	<table id="checkBoxTable" class="adDescDiv">
		<tr>
			<td><h2>Smoking inside allowed</h2></td>
			<td><c:choose>
					<c:when test="${shownAuction.smokers}">
						<img src="/img/check-mark.png">
					</c:when>
					<c:otherwise>
						<img src="/img/check-mark-negative.png">
					</c:otherwise>
				</c:choose></td>
		</tr>

		<tr>
			<td><h2>Animals allowed</h2></td>
			<td><c:choose>
					<c:when test="${shownAuction.animals}">
						<img src="/img/check-mark.png">
					</c:when>
					<c:otherwise>
						<img src="/img/check-mark-negative.png">
					</c:otherwise>
				</c:choose></td>
		</tr>

		<tr>
			<td><h2>Furnished Room</h2></td>
			<td><c:choose>
					<c:when test="${shownAuction.furnished}">
						<img src="/img/check-mark.png">
					</c:when>
					<c:otherwise>
						<img src="/img/check-mark-negative.png">
					</c:otherwise>
				</c:choose></td>
		</tr>

		<tr>
			<td><h2>WiFi available</h2></td>
			<td><c:choose>
					<c:when test="${shownAuction.internet}">
						<img src="/img/check-mark.png">
					</c:when>
					<c:otherwise>
						<img src="/img/check-mark-negative.png">
					</c:otherwise>
				</c:choose></td>
		</tr>

		<tr>
			<td><h2>Cable TV</h2></td>
			<td><c:choose>
					<c:when test="${shownAuction.cable}">
						<img src="/img/check-mark.png">
					</c:when>
					<c:otherwise>
						<img src="/img/check-mark-negative.png">
					</c:otherwise>
				</c:choose></td>
		</tr>

		<tr>
			<td><h2>Garage</h2></td>
			<td><c:choose>
					<c:when test="${shownAuction.garage}">
						<img src="/img/check-mark.png">
					</c:when>
					<c:otherwise>
						<img src="/img/check-mark-negative.png">
					</c:otherwise>
				</c:choose></td>
		</tr>

		<tr>
			<td><h2>Cellar</h2></td>
			<td><c:choose>
					<c:when test="${shownAuction.cellar}">
						<img src="/img/check-mark.png">
					</c:when>
					<c:otherwise>
						<img src="/img/check-mark-negative.png">
					</c:otherwise>
				</c:choose></td>
		</tr>

		<tr>
			<td><h2>Balcony</h2></td>
			<td><c:choose>
					<c:when test="${shownAuction.balcony}">
						<img src="/img/check-mark.png">
					</c:when>
					<c:otherwise>
						<img src="/img/check-mark-negative.png">
					</c:otherwise>
				</c:choose></td>
		</tr>

		<tr>
			<td><h2>Garden</h2></td>
			<td><c:choose>
					<c:when test="${shownAuction.garden}">
						<img src="/img/check-mark.png">
					</c:when>
					<c:otherwise>
						<img src="/img/check-mark-negative.png">
					</c:otherwise>
				</c:choose></td>
		</tr>

	</table>
</section>

<div class="clearBoth"></div>
<br>

<table id="advertiserTable" class="adDescDiv">
	<tr>
		<td><h2>Advertiser</h2>
			<br /></td>
	</tr>

	<tr>
		<td><c:choose>
				<c:when test="${shownAuction.user.picture.filePath != null}">
					<img src="${shownAuction.user.picture.filePath}">
				</c:when>
				<c:otherwise>
					<img src="/img/avatar.png">
				</c:otherwise>
			</c:choose></td>

		<td>${shownAuction.user.username}</td>

		<td id="advertiserEmail"><c:choose>
				<c:when test="${loggedIn}">
					<a href="/user?id=${shownAuction.user.id}"><button
							type="button">Visit profile</button></a>
				</c:when>
				<c:otherwise>
					<a href="/login"><button class="thinInactiveButton"
							type="button">Login to visit profile</button></a>
				</c:otherwise>
			</c:choose>
	</tr>
</table>

<div id="confirmationDialog">
	<form>
	<p>Send enquiry to advertiser?</p>
	<button type="button" id="confirmationDialogSend">Send</button>
	<button type="button" id="confirmationDialogCancel">Cancel</button>
	</form>
</div>

<c:import url="template/footer.jsp" />
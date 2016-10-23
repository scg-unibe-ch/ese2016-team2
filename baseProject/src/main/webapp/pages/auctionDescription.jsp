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

<!-- format the dates -->
<fmt:formatDate value="${shownAuction.moveInDate}" var="formattedMoveInDate"
	type="date" pattern="dd.MM.yyyy" />
<fmt:formatDate value="${shownAuction.creationDate}" var="formattedCreationDate"
	type="date" pattern="dd.MM.yyyy" />
<c:choose>
	<c:when test="${empty shownAuction.moveOutDate }">
		<c:set var="formattedMoveOutDate" value="unlimited" />
	</c:when>
	<c:otherwise>
		<fmt:formatDate value="${shownAuction.moveOutDate}"
			var="formattedMoveOutDate" type="date" pattern="dd.MM.yyyy" />
	</c:otherwise>
</c:choose>


<h1 id="shownAdTitle">${shownAuction.title}</h1>

<section>
	<c:choose>
		<c:when test="${loggedIn}">
			<c:if test="${loggedInUserEmail == shownAuction.user.username }">
				<!-- <a href="<c:url value='/profile/editAuction?id=${shownAuction.id}' />"> -->
					<button type="button">Edit Auction</button>
			</c:if>
		</c:when>
	</c:choose>
	<br>
	<br>

	<table id="adDescTable" class="adDescDiv">
		<tr>
			<td><h2>Type</h2></td>
			<td>${shownAuction.roomType}</td>
		</tr>

		<tr>
			<td><h2>Address</h2></td>
			<td>
				<a class="link" href="http://maps.google.com/?q=${shownAuction.street}, ${shownAuction.zipcode}, ${shownAuction.city}">${shownAuction.street},
						${shownAuction.zipcode} ${shownAuction.city}</a>
			</td>
		</tr>

		<tr>
			<td><h2>Available from</h2></td>
			<td>${formattedMoveInDate}</td>
		</tr>

		<tr>
			<td><h2>Move-out Date</h2></td>
			<td>${formattedMoveOutDate}</td>
		</tr>

		<tr>
			<td><h2>Square Meters</h2></td>
			<td>${shownAuction.squareFootage}&#32;m²</td>
		</tr>
		<tr>
			<td><h2>Ad created on</h2></td>
			<td>${formattedCreationDate}</td>
		</tr>
	</table>
</section>

<hr class="clearBoth" />

<section>
	<table id="adDescTable" class="adDescDiv">
		<tr>
			<td><h2>Highest bid:</h2></td>
			<td>${shownAuction.prize}</td>
		</tr>
		<tr>
			<td><h2>New bid:</h2></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td><button type="submit">Submit</button></td>
		</tr>
	</table>
</section>

<hr class="clearBoth" />

<section>
	<div id="descriptionTexts">
		<div class="adDescDiv">
			<h2>Room Description</h2>
			<p>${shownAuction.roomDescription}</p>
		</div>
		<br />

		<div class="adDescDiv">
			<h2>Roommates</h2>
			<p>${shownAuction.roommates}</p>
			<c:forEach var="mate" items="${shownAuction.registeredRoommates}">
				<div class="roommate">
				<table id="mate">
					<tr>
						<td>
						<a href="/user?id=${mate.id}">
						<c:choose>
							<c:when test="${mate.picture.filePath != null}">
								<img src="${mate.picture.filePath}">
							</c:when>
							<c:otherwise>
								<img src="/img/avatar.png">
							</c:otherwise>
						</c:choose>
						</a>
						</td>
						<td>${mate.firstName} ${mate.lastName}</td>
						<td>${mate.username}</td>
						<td>
						<c:choose>
							<c:when test="${mate.gender == 'MALE'}">
								male
							</c:when>
							<c:otherwise>
								female
							</c:otherwise>
						</c:choose></td>
					</tr>
				</table>
			</div>
			</c:forEach>
		</div>
		<br />

		<div class="adDescDiv">
			<h2>Preferences</h2>
			<p>${shownAuction.preferences}</p>
		</div>
		<br />
	</div>
		
<table id="checkBoxTable" class="adDescDiv">
		<tr>
			<td><h2>Smoking inside allowed</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAuction.smokers}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Animals allowed</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAuction.animals}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Furnished Room</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAuction.furnished}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>
		
		<tr>
			<td><h2>WiFi available</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAuction.internet}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Cable TV</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAuction.cable}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Garage</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAuction.garage}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Cellar</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAuction.cellar}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Balcony</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAuction.balcony}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Garden</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAuction.garden}"><img src="/img/check-mark.png"></c:when>
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
				<c:when test="${shownAuction.user.picture.filePath != null}">
					<img src="${shownAuction.user.picture.filePath}">
				</c:when>
				<c:otherwise>
					<img src="/img/avatar.png">
				</c:otherwise>
			</c:choose></td>
		
		<td>${shownAuction.user.username}</td>
		
		<td id="advertiserEmail">
		<c:choose>
			<c:when test="${loggedIn}">
				<a href="/user?id=${shownAuction.user.id}"><button type="button">Visit profile</button></a>
			</c:when>
			<c:otherwise>
				<a href="/login"><button class="thinInactiveButton" type="button">Login to visit profile</button></a>
			</c:otherwise>
		</c:choose>

		<td>
			<form>
				<c:choose>
					<c:when test="${loggedIn}">
						<c:if test="${loggedInUserEmail != shownAuction.user.username }">
							<button id="newMsg" type="button">Contact Advertiser</button>
						</c:if>
					</c:when>
					<c:otherwise>
						<a href="/login"><button class="thinInactiveButton" type="button">Login to contact advertiser</button></a>
					</c:otherwise>
				</c:choose>
			</form>
		</td>
	</tr>
</table>

<div id="msgDiv">
<form class="msgForm">
	<h2>Contact the advertiser</h2>
	<br>
	<br>
	<label>Subject: <span>*</span></label>
	<input  class="msgInput" type="text" id="msgSubject" placeholder="Subject" />
	<br><br>
	<label>Message: </label>
	<textarea id="msgTextarea" placeholder="Message" ></textarea>
	<br/>
	<button type="button" id="messageSend">Send</button>
	<button type="button" id="messageCancel">Cancel</button>
	</form>
</div>

<div id="confirmationDialog">
	<form>
	<p>Send enquiry to advertiser?</p>
	<button type="button" id="confirmationDialogSend">Send</button>
	<button type="button" id="confirmationDialogCancel">Cancel</button>
	</form>
</div>

<c:import url="template/footer.jsp" />
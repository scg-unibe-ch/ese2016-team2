<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="template/header.jsp" />

<script src="/js/jquery.ui.widget.js"></script>
<script src="/js/jquery.iframe-transport.js"></script>
<script src="/js/jquery.fileupload.js"></script>

<script src="/js/pictureUploadEditAuction.js"></script>

<script src="/js/editAuction.js"></script>


<script>
	$(document).ready(function() {		
		$("#field-city").autocomplete({
			minLength : 2
		});
		$("#field-city").autocomplete({
			source : <c:import url="getzipcodes.jsp" />
		});
		$("#field-city").autocomplete("option", {
			enabled : true,
			autoFocus : true
		});
		$("#field-moveInDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#field-moveOutDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		
		$("#field-visitDay").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		
		$("#addVisitButton").click(function() {
			var date = $("#field-visitDay").val();
			if(date == ""){
				return;
			}
			
			var startHour = $("#startHour").val();
			var startMinutes = $("#startMinutes").val();
			var endHour = $("#endHour").val();
			var endMinutes = $("#endMinutes").val();
			
			if (startHour > endHour) {
				alert("Invalid times. The visit can't end before being started.");
				return;
			} else if (startHour == endHour && startMinutes >= endMinutes) {
				alert("Invalid times. The visit can't end before being started.");
				return;
			}
			
			var newVisit = date + ";" + startHour + ":" + startMinutes + 
				";" + endHour + ":" + endMinutes; 
			var newVisitLabel = date + " " + startHour + ":" + startMinutes + 
			" to " + endHour + ":" + endMinutes; 
			
			var index = $("#addedVisits input").length;
			
			var label = "<p>" + newVisitLabel + "</p>";
			var input = "<input type='hidden' value='" + newVisit + "' name='visits[" + index + "]' />";
			
			$("#addedVisits").append(label + input);
		});
	});
</script>

<!-- format the dates -->
<fmt:formatDate value="${auction.moveInDate}" var="formattedMoveInDate"
	type="date" pattern="dd-MM-yyyy" />
<fmt:formatDate value="${auction.moveOutDate}" var="formattedMoveOutDate"
	type="date" pattern="dd-MM-yyyy" />
	
<pre><a href="/">Home</a>   &gt;   <a href="/profile/myRooms">My Rooms</a>   &gt;   <a href="/auction?id=${auction.id}">Auction Description</a>   &gt;   Edit Auction</pre>


<h1>Edit Auction</h1>
<hr />

<form:form method="post" modelAttribute="placeAuctionForm"
	action="/profile/editAuction" id="placeAuctionForm" autocomplete="off"
	enctype="multipart/form-data">

<input type="hidden" name="adId" value="${auction.id }" />

	<fieldset>
		<legend>Change General info</legend>
		<table class="placeAdTable">
			<tr>
				<td><label for="field-title">Auction Title</label></td>
				<td><label for="type-room">Type:</label></td>
			</tr>

			<tr>
				<td><form:input id="field-title" path="title" value="${auction.title}" /></td>
				<td><c:choose>
						<c:when test="${auction.roomType == 'Room'}">
							<form:radiobutton id="type-room" path="roomType" value="Room" checked="checked"/>Room 
							<form:radiobutton id="type-room" path="roomType" value="Studio" />Studio
							<form:radiobutton id="type-room" path="roomType" value="House" />House
						</c:when>
						<c:when test="${auction.roomType == 'Studio'}">
							<form:radiobutton id="type-room" path="roomType" value="Room" />Room 
							<form:radiobutton id="type-room" path="roomType" value="Studio" checked="checked"/>Studio
							<form:radiobutton id="type-room" path="roomType" value="House" />House
						</c:when>
						<c:otherwise>
							<form:radiobutton id="type-room" path="roomType" value="Room" />Room 
							<form:radiobutton id="type-room" path="roomType" value="Studio" />Studio
							<form:radiobutton id="type-room" path="roomType" value="House" checked="checked"/>House
						</c:otherwise>
					</c:choose></td>
			</tr>

			<tr>
				<td><label for="field-street">Street</label></td>
				<td><label for="field-city">City / Zip code</label></td>
			</tr>

			<tr>
				<td><form:input id="field-street" path="street"
						value="${auction.street}" /></td>
				<td>
					<form:input id="field-city" path="city" value="${auction.zipcode} - ${auction.city}" />
					<form:errors path="city" cssClass="validationErrorText" />
				</td>
			</tr>

			<tr>
				<td><label for="moveInDate">Move-in date</label></td>
			</tr>
			<tr>
				<td>
					<form:input type="text" id="field-moveInDate"
						path="moveInDate" value="${formattedMoveInDate }"/>
				</td>
			</tr>

			<tr>
				<td><label for="field-Prize">Minimal bid Prize</label></td>
				<td><label for="field-SquareFootage">Square Meters</label></td>
			</tr>
			<tr>
				<td>
					<form:input id="field-Prize" type="number" path="prize"
						placeholder="Prize per month" step="50" value="${auction.prize}"/> <form:errors
						path="prize" cssClass="validationErrorText" />
				</td>
				<td>
					<form:input id="field-SquareFootage" type="number"
						path="squareFootage" placeholder="Prize per month" step="5" 
						value="${auction.squareFootage }"/> <form:errors
						path="squareFootage" cssClass="validationErrorText" />
				</td>
			</tr>
			<tr>
				<td><label for="field-endDate">Auction end-date</label></td>
				<td><label for="field-endTime">Auction end-Time</label></td>
			</tr>
			<tr>
				<td><form:input id="field-endDate" type="text" path="endDate" value="${fn:substring(auction.endTime, 7, 17)}"/></td>
				<td><form:input id="field-endTime" type="text" path="endTime" value="${fn:substring(auction.endTime, 0, 5)}"/></td>
			</tr>
		</table>
	</fieldset>


	<br />
	<fieldset>
		<legend>Change Room Description</legend>

		<table class="placeAdTable">
			<tr>
				<td>
					<c:choose>
						<c:when test="${auction.smokers}">
							<form:checkbox id="field-smoker" path="smokers" checked="checked" /><label>Smoking
							inside allowed</label>
						</c:when>
						<c:otherwise>
							<form:checkbox id="field-smoker" path="smokers" /><label>Smoking
							inside allowed</label>
						</c:otherwise>
					</c:choose>
				</td>
				
				<td>
					<c:choose>
						<c:when test="${auction.animals}">
							<form:checkbox id="field-animals" path="animals"  checked="checked" /><label>Animals
						allowed</label>
						</c:when>
						<c:otherwise>
							<form:checkbox id="field-animals" path="animals" /><label>Animals
						allowed</label>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>
					<c:choose>
						<c:when test="${auction.garden}">
							<form:checkbox id="field-garden" path="garden" checked="checked" /><label>Garden
							(co-use)</label>
						</c:when>
						<c:otherwise>
							<form:checkbox id="field-garden" path="garden" /><label>Garden
							(co-use)</label>
						</c:otherwise>
					</c:choose>
				</td>
				
				<td>
					<c:choose>
						<c:when test="${auction.balcony}">
							<form:checkbox id="field-balcony" path="balcony"  checked="checked" /><label>Balcony
						or Patio</label>
						</c:when>
						<c:otherwise>
							<form:checkbox id="field-balcony" path="balcony" /><label>Balcony
						or Patio</label>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>
					<c:choose>
						<c:when test="${auction.cellar}">
							<form:checkbox id="field-cellar" path="cellar" checked="checked" /><label>Cellar
						or Attic</label>
						</c:when>
						<c:otherwise>
							<form:checkbox id="field-cellar" path="cellar" /><label>Cellar
						or Atticd</label>
						</c:otherwise>
					</c:choose>
				</td>
				
				<td>
					<c:choose>
						<c:when test="${auction.furnished}">
							<form:checkbox id="field-furnished" path="furnished"  checked="checked" /><label>Furnished
							</label>
						</c:when>
						<c:otherwise>
							<form:checkbox id="field-furnished" path="furnished" /><label>Furnished</label>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>
					<c:choose>
						<c:when test="${auction.cable}">
							<form:checkbox id="field-cable" path="cable" checked="checked" /><label>Cable TV</label>
						</c:when>
						<c:otherwise>
							<form:checkbox id="field-cable" path="cable" /><label>Cable TV</label>
						</c:otherwise>
					</c:choose>
				</td>
				
				<td>
					<c:choose>
						<c:when test="${auction.garage}">
							<form:checkbox id="field-garage" path="garage"  checked="checked" /><label>Garage
							</label>
						</c:when>
						<c:otherwise>
							<form:checkbox id="field-garage" path="garage" /><label>Garage</label>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>
					<c:choose>
						<c:when test="${auction.internet}">
							<form:checkbox id="field-internet" path="internet"  checked="checked" /><label>WiFi available
							</label>
						</c:when>
						<c:otherwise>
							<form:checkbox id="field-internet" path="internet" /><label>WiFi available</label>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>

		</table>
		<br />
		<form:textarea path="roomDescription" rows="10" cols="100" value="${auction.roomDescription}" />
		<form:errors path="roomDescription" cssClass="validationErrorText" />
	</fieldset>

	<br />
	<fieldset>
		<legend>Change preferences</legend>
		<form:textarea path="preferences" rows="5" cols="100"
			value="${auction.preferences}" ></form:textarea>
	</fieldset>

	
	<!-- <fieldset>
		<legend>Add visiting times</legend>
		
		<table>
			<tr>
				<td>
					<input type="text" id="field-visitDay" />
					
					<select id="startHour">
 					<% 
 						for(int i = 0; i < 24; i++){
 							String hour = String.format("%02d", i);
							out.print("<option value=\"" + hour + "\">" + hour + "</option>");
 						}
 					%>
					</select>
					
					<select id="startMinutes">
 					<% 
 						for(int i = 0; i < 60; i++){
 							String minute = String.format("%02d", i);
							out.print("<option value=\"" + minute + "\">" + minute + "</option>");
 						}
 					%>
					</select>
					
					<span>to&thinsp; </span>
					
					<select id="endHour">
 					<% 
 						for(int i = 0; i < 24; i++){
 							String hour = String.format("%02d", i);
							out.print("<option value=\"" + hour + "\">" + hour + "</option>");
 						}
 					%>
					</select>
					
					<select id="endMinutes">
 					<% 
 						for(int i = 0; i < 60; i++){
 							String minute = String.format("%02d", i);
							out.print("<option value=\"" + minute + "\">" + minute + "</option>");
 						}
 					%>
					</select>
			

					<div id="addVisitButton" class="smallPlusButton">+</div>
					
					<div id="addedVisits"></div>
				</td>
				
			</tr>
			
		</table>
		<br>
	</fieldset> -->

	<br />

	<fieldset>
		<legend>Change pictures</legend>
		<h3>Delete existing pictures</h3>
		<br />
		<div>
			<c:forEach items="${auction.pictures }" var="picture">
				<div class="pictureThumbnail">
					<div>
					<img src="${picture.filePath}" />
					</div>
					<button type="button" data-ad-id="${auction.id }" data-picture-id="${picture.id }">Delete</button>
				</div>
			</c:forEach>
		</div>
		<p class="clearBoth"></p>
		<br /><br />
		<hr />
		<h3>Add new pictures</h3>
		<br />
		<label for="field-pictures">Pictures</label> <input
			type="file" id="field-pictures" accept="image/*" multiple="multiple" />
		<table id="uploaded-pictures" class="styledTable">
			<tr>
				<th id="name-column">Uploaded picture</th>
				<th>Size</th>
				<th>Delete</th>
			</tr>
		</table>
		<br>
	</fieldset>

	<div>
		<button type="submit">Submit</button>
		<a href="<c:url value='/auction?id=${auction.id}' />"> 
			<button type="button">Cancel</button>
		</a>
	</div>

</form:form>


<c:import url="template/footer.jsp" />
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/header.jsp" />

<pre><a href="/">Home</a>   &gt;   Alerts</pre>

<script>
function deleteAlert(button) {
	var id = $(button).attr("data-id");
	$.get("/profile/alerts/deleteAlert?id=" + id, function(){
		$("#alertsDiv").load(document.URL + " #alertsDiv");
	});
}
</script>

<script>
function validateType(form)
{
	var room = document.getElementById('room');
	var studio = document.getElementById('studio');
	var house = document.getElementById('house');
	var neither = document.getElementById('neither');
	var r = document.getElementById('r');
	var s = document.getElementById('s');
	var h = document.getElementById('h');
	var ras = document.getElementById('ras');
	var rah = document.getElementById('rah');
	var sah = document.getElementById('sah');
	var all = document.getElementById('all');
	
	r.checked = false;
	h.checked = false;
	s.checked = false;
	ras.checked = false;
	rah.checked = false;
	sah.checked = false;
	all.checked = false;
	
	if(!room.checked && !studio.checked && !house.checked) {
		neither.checked = true;
	}
	else if(room.checked && !studio.checked && !house.checked) {
		r.checked = true;
	}
	else if(!room.checked && studio.checked && !house.checked) {
		s.checked = true;
	}
	else if(!room.checked && !studio.checked && house.checked) {
		h.checked = true;
	}
	else if(room.checked && studio.checked && !house.checked) {
		ras.checked = true;
	}
	else if(room.checked && !studio.checked && house.checked) {
		rah.checked = true;
	}
	else if(!room.checked && studio.checked && house.checked) {
		sah.checked = true;
	}
	else {
		all.checked = true;
	}
}
</script>

<script>
function typeOfAlert(alert) {
	if(alert.getBothRoomAndStudio())
		return "Both"
	else if(alert.getStudio())
		return "Studio"
	else
		return "Room"
}	
</script>
	
<script>
	$(document).ready(function() {
		$("#city").autocomplete({
			minLength : 2
		});
		$("#city").autocomplete({
			source : <c:import url="getzipcodes.jsp" />
		});
		$("#city").autocomplete("option", {
			enabled : true,
			autoFocus : true
		});
		
		var price = document.getElementById('priceInput');
		var radius = document.getElementById('radiusInput');
		
		if(price.value == null || price.value == "" || price.value == "0")
			price.value = "500";
		if(radius.value == null || radius.value == "" || radius.value == "0")
			radius.value = "5";
	});
</script>

<h1>Create and manage alerts</h1>
<hr />

<h2>Create new alert</h2><br />
<form:form method="post" modelAttribute="alertForm" action="/profile/alerts"
	id="alertForm" autocomplete="off">

	<fieldset>
		<form:checkbox name="room" id="room" path="room" /><label>Room</label>
		<form:checkbox name="studio" id="studio" path="studio" /><label>Studio</label>
		<form:checkbox name="house" id="house" path="house" /><label>House</label>
		
		<form:checkbox style="display:none" name="neither" id="neither" path="noRoomNoStudio" />
		<form:checkbox style="display:none" name="r" id="r" path="alertType" value="Room" />
		<form:checkbox style="display:none" name="s" id="s" path="alertType" value="Studio" />
		<form:checkbox style="display:none" name="h" id="h" path="alertType" value="House" />
		<form:checkbox style="display:none" name="ras" id="ras" path="alertType" value="Room and Studio" />
		<form:checkbox style="display:none" name="rah" id="rah" path="alertType" value="Room and House" />
		<form:checkbox style="display:none" name="sah" id="sah" path="alertType" value="Studio and House" />
		<form:checkbox style="display:none" name="all" id="all" path="alertType" value="All" />
		<form:errors path="noRoomNoStudio" cssClass="validationErrorText" /><br />
		
		<label for="city">City / zip code:</label>
		<form:input type="text" name="city" id="city" path="city"
			placeholder="e.g. Bern" tabindex="3" />
		<form:errors path="city" cssClass="validationErrorText" />
		
		<label for="radius">Within radius of (max.):</label>
		<form:input id="radiusInput" type="number" path="radius"
			placeholder="e.g. 5" step="5" />
		km
		<form:errors path="radius" cssClass="validationErrorText" />
		<br /> <label for="price">Price (max.):</label>
		<form:input id="priceInput" type="number" path="price"
			placeholder="e.g. 5" step="50" />
		CHF
		<form:errors path="price" cssClass="validationErrorText" />
		<br />

		<button type="submit" tabindex="7" onClick="validateType(this.form)">Subscribe</button>
		<button type="reset" tabindex="8">Cancel</button>
	</fieldset>

</form:form> <br />
<h2>Your active alerts</h2>

<div id="alertsDiv" class="alertsDiv">			
<c:choose>
	<c:when test="${empty alerts}">
		<p>You currently aren't subscribed to any alerts.
	</c:when>
	<c:otherwise>
		<table class="styledTable" id="alerts">
			<thead>
			<tr>
				<th>Type</th>
				<th>City</th>
				<th>Radius</th>
				<th>max. Price</th>
				<th>Action</th>
			</tr>
			</thead>
		<c:forEach var="alert" items="${alerts}">
			<tr>
				<td>${alert.alertType}</td>
				<td>${alert.city}</td>
				<td>${alert.radius} km</td>
				<td>${alert.price} Chf</td>
				<td><button class="deleteButton" data-id="${alert.id}" onClick="deleteAlert(this)">Delete</button></td>
			</tr>
		</c:forEach>
		</table>
	</c:otherwise>
</c:choose>
</div>

<c:import url="template/footer.jsp" />
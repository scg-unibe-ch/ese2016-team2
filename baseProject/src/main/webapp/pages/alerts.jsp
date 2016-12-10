<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<script type="text/javascript">
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
<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="Create an alert..." />
	</c:import>
	<div class="container">
		<div class="row">
			<div class="span-half">
				<div class="form form-search form-filter form-max-height">
					<form:form method="post" modelAttribute="alertForm" action="/profile/alerts"
						id="alertForm" autocomplete="off">
						<div class="container-scroll">
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
							</fieldset>

						</div>
						<div class="row">
							<div class="tile tile-half">
								<button type="submit" tabindex="7" onClick="validateType(this.form)">Subscribe</button>
							</div>
							<div class="tile tile-half">
								<button type="reset" tabindex="8">Cancel</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
			<div class="span-half container-pad page-max-height">
				<div class="container-scroll">
					<h3>Your active alerts</h2>
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
				</div>
			</div>
		</div> <%-- .row END --%>
	</div> <%-- .container --%>
</main>

<c:import url="template/~bottom.jsp">
	<c:param name="js" value="alerts" />
</c:import>

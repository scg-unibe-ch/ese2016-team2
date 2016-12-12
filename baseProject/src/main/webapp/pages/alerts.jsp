<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

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

							<form:input
								type="text"
								name="city"
								id="city"
								path="city"
								placeholder="City / Zip"
								tabindex="1"
								value="" />
							<div class="validator error-city">
								<span class="validationErrorText"></span>
							</div>
							<form:errors path="city" cssClass="validationErrorText" />

							<div class="row checkboxes">
								<div class="tile tile-third">
									<form:checkbox name="room" id="room" path="room" />
									<label for="room">Room</label>
								</div>
								<div class="tile tile-third">
									<form:checkbox name="studio" id="studio" path="studio" />
									<label for="studio">Studio</label>
								</div>
								<div class="tile tile-third">
									<form:checkbox name="house" id="house" path="house" />
									<label for="house">House</label>
								</div>
							</div>

							<form:checkbox style="display:none" name="neither" id="neither" path="noRoomNoStudio" />
							<form:checkbox style="display:none" name="r" id="r" path="alertType" value="Room" />
							<form:checkbox style="display:none" name="s" id="s" path="alertType" value="Studio" />
							<form:checkbox style="display:none" name="h" id="h" path="alertType" value="House" />
							<form:checkbox style="display:none" name="ras" id="ras" path="alertType" value="Room and Studio" />
							<form:checkbox style="display:none" name="rah" id="rah" path="alertType" value="Room and House" />
							<form:checkbox style="display:none" name="sah" id="sah" path="alertType" value="Studio and House" />
							<form:checkbox style="display:none" name="all" id="all" path="alertType" value="All" />
							<form:errors path="noRoomNoStudio" cssClass="validationErrorText" />

							<form:input
								id="radiusInput"
								type="number"
								path="radius"
								placeholder="Radius in km (5, 10, 15,... , 500)"
								tabindex="2"
								step="5"
								min="5"
								max="500"
								value="" />
							<div class="validator error-radiusInput">
								<span class="validationErrorText"></span>
							</div>
							<form:errors path="radius" cssClass="validationErrorText" />

							<form:input
								id="priceInput"
								type="number"
								path="price"
								placeholder="Maximum Price in CHF"
								step="50"
								tabindex="3"
								min="50" />
							<div class="validator error-priceInput">
								<span class="validationErrorText"></span>
							</div>
							<form:errors path="price" cssClass="validationErrorText" />

						</div> <%-- .container-scoll END --%>

						<div class="row">
							<div class="tile tile-half">
								<button type="submit" tabindex="7">Subscribe</button>
							</div>
							<div class="tile tile-half">
								<a href="/profile/alerts" tabindex="8">
									<button type=button>Cancel</button>
								</a>
							</div>
						</div>

					</form:form>

				</div> <%-- .form END --%>
			</div> <%-- .span-half END --%>

			<div class="span-half container-pad page-max-height">
				<div class="container-scroll">

					<h3>Your active alerts...</h3>
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
										<td>${alert.price} CHF</td>
										<td><button class="deleteButton" data-id="${alert.id}">Delete</button></td>
									</tr>
								</c:forEach>
								</table>
							</c:otherwise>
						</c:choose>
					</div>

				</div> <%-- .container-scoll END --%>
			</div> <%-- .span-half END --%>
		</div> <%-- .row END --%>
	</div> <%-- .container --%>
</main>

<c:import url="template/~bottom.jsp">
	<c:param name="js" value="alerts" />
</c:import>

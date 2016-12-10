<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="template/~top.jsp" />
<c:import url="template/~header_wo_search.jsp" />

<main role="main">
	<c:import url="template/~top_bar.jsp">
		<c:param name="instr" value="Edit this ad..." />
	</c:import>
	<div class="container">
		<div class="row">
			<div class="span-half">
				<div class="form form-search form-edit form-max-height">
					<!-- format the dates -->
					<fmt:formatDate value="${ad.moveInDate}" var="formattedMoveInDate"
						type="date" pattern="dd-MM-yyyy" />
					<fmt:formatDate value="${ad.moveOutDate}" var="formattedMoveOutDate"
						type="date" pattern="dd-MM-yyyy" />
					<form:form
						method="post"
						modelAttribute="placeAdForm"
						action="/profile/editAd"
						id="placeAdForm"
						autocomplete="off"
						enctype="multipart/form-data">
						<div class="container-scroll">
							<c:choose>
								<c:when test="${ad.roomType == 'Room'}">
									<div class="row checkboxes edit-section">
										<div class="tile tile-third">
											<form:radiobutton id="room" path="roomType" value="Room" checked="checked" />
											<label for="room">Room</label>
										</div>
										<div class="tile tile-third">
											<form:radiobutton id="studio" path="roomType" value="Studio" />
											<label for="studio">Studio</label>
										</div>
										<div class="tile tile-third">
											<form:radiobutton id="house" path="roomType" value="House" />
											<label for="house">House</label>
										</div>
									</div>
								</c:when>
								<c:when test="${ad.roomType == 'Studio'}">
									<div class="row checkboxes edit-section">
										<div class="tile tile-third">
											<form:radiobutton id="room" path="roomType" value="Room" />
											<label for="room">Room</label>
										</div>
										<div class="tile tile-third">
											<form:radiobutton id="studio" path="roomType" value="Studio" checked="checked" />
											<label for="studio">Studio</label>
										</div>
										<div class="tile tile-third">
											<form:radiobutton id="house" path="roomType" value="House" />
											<label for="house">House</label>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="row checkboxes edit-section">
										<div class="tile tile-third">
											<form:radiobutton id="room" path="roomType" value="Room" />
											<label for="room">Room</label>
										</div>
										<div class="tile tile-third">
											<form:radiobutton id="studio" path="roomType" value="Studio" />
											<label for="studio">Studio</label>
										</div>
										<div class="tile tile-third">
											<form:radiobutton id="house" path="roomType" value="House" checked="checked" />
											<label for="house">House</label>
										</div>
									</div>
								</c:otherwise>
							</c:choose>
							<input type="hidden" name="adId" value="${ad.id }" />
							<c:choose>
								<c:when test="${!ad.buyable}">
									<div class="row checkboxes edit-section">
										<div class="tile tile-half">
											<form:radiobutton id="rent" path="buyable" value="0" checked="checked" />
											<label for="rent">Rent</label>
										</div>
										<div class="tile tile-half">
											<form:radiobutton id="buy" path="buyable" value="1" />
											<label for="buy">Sell</label>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="row checkboxes edit-section">
										<div class="tile tile-half">
											<form:radiobutton id="rent" path="buyable" value="0" />
											<label for="rent">Rent</label>
										</div>
										<div class="tile tile-half">
											<form:radiobutton id="buy" path="buyable" value="1" checked="checked" />
											<label for="buy">Sell</label>
										</div>
									</div>
								</c:otherwise>
							</c:choose>
							<form:input
								type="text"
								id="field-street"
								path="street"
								value="${ad.street}"
								placeholder="Address"
								tabindex="1" />
							<form:input
								class="edit-section"
								type="text"
								id="field-city"
								path="city"
								value="${ad.zipcode} - ${ad.city}"
								placeholder="City"
								tabindex="2" />
							<form:errors path="city" cssClass="validationErrorText" />
							
							<%-- @Jerome: for some reason it autosets '0' as value. W/A: set
							to '' by js. --%>
							<form:input
								id="field-Prize"
								type="number"
								path="prize"
								placeholder="Rent or Price in CHF"
								tabindex="3"
								value="${ad.prize}"
								min="1" />
							<form:errors path="prize" cssClass="validationErrorText" />
							<form:input
								class="edit-section"
								id="field-SquareFootage"
								type="number"
								path="squareFootage"
								value="${ad.squareFootage }"
								placeholder="Space in m²"
								tabindex="4"
								min="5" />
							<form:errors path="squareFootage" cssClass="validationErrorText" />
						<div class="row dates">
	              			<div class="tile tile-half">
	                			<div class="row">
	                  				<div class="tile tile-full">
	                    				<label>Move-in</label>
	                  				</div>
					                <div class="tile tile-full">
					                    <form:input
					                      class="js-has-label"
					                      type="text"
					                      id="field-moveInDate"
					                      path="moveInDate"
																value="${formattedMoveInDate }"
					                      tabindex="5"
					                      placeholder="Choose from datepicker..." />
	                  				</div>
	                			</div>
	              			</div>
	              			<div class="tile tile-half">
	                			<div class="datepicker" id="moveInDate">
	                				</div>
	              				</div>
	            			</div>
							<div class="row dates fields-optional fields-optional-sell js-show edit-section">
		              			<div class="tile tile-half">
		                			<div class="row">
	                  					<div class="tile tile-full">
	                    					<label>Move-out (optional when selling)</label>
	                  					</div>
	                  					<div class="tile tile-full">
						                    <form:input
						                      class="js-has-label"
						                      type="text"
						                      id="field-moveOutDate"
						                      path="moveOutDate"
																	value="${formattedMoveOutDate }"
						                      tabindex="6"
						                      placeholder="Choose from datepicker..." />
	                  					</div>
	                				</div>
	              				</div>
	              				<div class="tile tile-half">
	                				<div class="datepicker" id="moveOutDate">

	                				</div>
	              				</div>
	           	 			</div>
							<div class="row checkboxes">
								<div class="tile tile-half">
									<c:choose>
										<c:when test="${ad.smokers}">
											<form:checkbox id="field-smoker" path="smokers" checked="checked" />
										</c:when>
										<c:otherwise>
											<form:checkbox id="field-smoker" path="smokers" />
										</c:otherwise>
									</c:choose>
									<label for="field-smoker">Smoking allowed</label>
								</div>
								<div class="tile tile-half">
									<c:choose>
										<c:when test="${ad.animals}">
											<form:checkbox id="field-animals" path="animals"  checked="checked" />
										</c:when>
										<c:otherwise>
											<form:checkbox id="field-animals" path="animals" />
										</c:otherwise>
									</c:choose>
									<label for="field-animals">Animals allowed</label>
								</div>
							</div>
							<div class="row checkboxes">
								<div class="tile tile-half">
									<c:choose>
										<c:when test="${ad.balcony}">
											<form:checkbox id="field-balcony" path="balcony"  checked="checked" />
										</c:when>
										<c:otherwise>
											<form:checkbox id="field-balcony" path="balcony" />
										</c:otherwise>
									</c:choose>
									<label for="field-balcony">Balcony or Patio</label>
								</div>
								<div class="tile tile-half">
									<c:choose>
										<c:when test="${ad.garden}">
											<form:checkbox id="field-garden" path="garden" checked="checked" />
										</c:when>
										<c:otherwise>
											<form:checkbox id="field-garden" path="garden" />
										</c:otherwise>
									</c:choose>
									<label for="field-garden">Garden (co-use)</label>
								</div>
							</div>
							<div class="row checkboxes">
								<div class="tile tile-half">
									<c:choose>
										<c:when test="${ad.cellar}">
											<form:checkbox id="field-cellar" path="cellar" checked="checked" />
										</c:when>
										<c:otherwise>
											<form:checkbox id="field-cellar" path="cellar" />
										</c:otherwise>
									</c:choose>
									<label for="field-cellar">Cellar or Attic</label>
								</div>
								<div class="tile tile-half">
									<c:choose>
										<c:when test="${ad.furnished}">
											<form:checkbox id="field-furnished" path="furnished"  checked="checked" />
										</c:when>
										<c:otherwise>
											<form:checkbox id="field-furnished" path="furnished" />
										</c:otherwise>
									</c:choose>
									<label for="field-furnished">Furnished</label>
								</div>
							</div>
							<div class="row checkboxes">
								<div class="tile tile-half">
									<c:choose>
										<c:when test="${ad.cable}">
											<form:checkbox id="field-cable" path="cable" checked="checked" />
										</c:when>
										<c:otherwise>
											<form:checkbox id="field-cable" path="cable" />
										</c:otherwise>
									</c:choose>
									<label for="field-cable">Cable TV</label>
								</div>
								<div class="tile tile-half">
									<c:choose>
										<c:when test="${ad.garage}">
											<form:checkbox id="field-garage" path="garage"  checked="checked" />
										</c:when>
										<c:otherwise>
											<form:checkbox id="field-garage" path="garage" />
										</c:otherwise>
									</c:choose>
									<label for="field-garage">Garage</label>
								</div>
							</div>
							<div class="row checkboxes edit-section">
								<div class="tile tile-half">
									<c:choose>
										<c:when test="${ad.internet}">
											<form:checkbox id="field-internet" path="internet"  checked="checked" />
										</c:when>
										<c:otherwise>
											<form:checkbox id="field-internet" path="internet" />
										</c:otherwise>
									</c:choose>
									<label for="field-internet">WiFi</label>
								</div>
							</div>
							<form:input
								type="text"
								id="field-title"
								path="title"
								value="${ad.title}"
								placeholder="Title"
								tabindex="7" />
							<form:textarea
								id="roomDescription"
								path="roomDescription"
								value="${ad.roomDescription}"
								rows="10"
								tabindex="8"
								placeholder="Room Description" />
							<form:errors path="roomDescription" cssClass="validationErrorText" />
							<form:textarea
								class="edit-section"
								path="preferences"
								value="${ad.preferences}"
								rows="5"
								tabindex="9"
								placeholder="Preferences" />
							<input
								type="hidden"
								id="field-pictures"
								accept="image/*"
								multiple="multiple" />
							<h3 class="edit-section-title">
								Viewing times
								<span>
									Choose a date and a time span, then click the
									<i class="fa fa-plus-circle" aris-hidden="true"></i> button below.
									To add another one, just change the values and click the button again.
								</span>
							</h3>
							<div class="row dates related">
						    	<div class="tile tile-half">
						           	<div class="row">
						                <div class="tile tile-full">
						                    <input
																	tabindex="10"
						                      class="js-has-label"
						                      type="text"
						                      id="field-visitDay"
						                      placeholder="Choose from datepicker..." />
	                  					</div>
	                				</div>
	              				</div>
				            	<div class="tile tile-half">
				                	<div class="datepicker" id="visitDay">
				                	</div>
				            	</div>
				            </div>
							<div class="row times fill-parent edit-section">
								<div class="tile tile-three-quarter">
									<div class="row">
										<div class="tile tile-half">
											<div class="row">
												<div class="tile tile-full">
			                    					<label>From: Hour</label>
			                  					</div>
												<div class="tile tile-full action action-tile">
													<select id="startHour">
														<% for (int i = 0; i < 24; i++) {
															String hour = String.format("%02d", i);
															out.print("<option value=\"" + hour + "\">" + hour +"</option>");
														} %>
													</select>
												</div>
											</div>
										</div>
										<div class="tile tile-half">
											<div class="row">
												<div class="tile tile-full">
			                    					<label>Minute</label>
			                  					</div>
												<div class="tile tile-full action action-tile">
													<select id="startMinutes">
														<% for (int i = 0; i < 60; i++) {
															String minute = String.format("%02d", i);
															out.print("<option value=\"" + minute + "\">" + minute +"</option>");
														} %>
													</select>
			                  					</div>
											</div>
										</div>
									</div>
									<div class="row times">
										<div class="tile tile-half">
											<div class="row">
												<div class="tile tile-full">
			                    					<label>To: Hour</label>
			                  					</div>
												<div class="tile tile-full action action-tile">
													<select id="endHour">
														<% for (int i = 0; i < 24; i++) {
															String hour = String.format("%02d", i);
															out.print("<option value=\"" + hour + "\">" + hour +"</option>");
														} %>
													</select>
												</div>
											</div>
										</div>
										<div class="tile tile-half">
											<div class="row">
												<div class="tile tile-full">
			                    					<label>Minute</label>
			                  					</div>
												<div class="tile tile-full action action-tile">
													<select id="endMinutes">
														<% for (int i = 0; i < 60; i++) {
															String minute = String.format("%02d", i);
															out.print("<option value=\"" + minute + "\">" + minute +"</option>");
														} %>
													</select>
			                  					</div>
											</div>
										</div>
									</div>
								</div> <%-- .tile-three-quarter END --%>
								<div class="tile tile-quarter fill-parent-child">
									<div id="addVisitButton" class="action action-icon action-add">
										<span title="Add viewing time" class="fa fa-plus-circle fa-3x"></span>
									</div>
								</div>
							</div> <%-- .row.times END --%>
						</div> <%-- .container-scroll END --%>
						<div>
							<button type="submit">Submit</button>
							<a href="<c:url value='/ad?id=${ad.id}' />">
								<button type="button">Cancel</button>
							</a>
						</div>
					</form:form>
				</div> <%-- .form.form-search END --%>
			</div> <%-- .span-half END --%>
			<div class="span-half page-max-height">
				<div class="container-scroll">
					<h3 class="edit-section-title">
						Drop images here...
						<span>
							Drag and drop images onto the window.<br>
							You can remove an image by double clicking it.
						</span>
					</h3>
					<div class="row">
						<div class="tile tile-full action-dropzone">
							<div id="image-preview"><c:forEach items="${ad.pictures }" var="picture">
								<div
									data-ad-id="${ad.id }"
									data-picture-id="${picture.id }"
					        		style="height:128px"
					        		class="image-preview-wrap">
					        		<span
										title="Remove image by double clicking it."
										class="fa fa-times fa-2x action-delete"></span>
									<img src="${picture.filePath}" alt="" />
								</div>
							</c:forEach></div>
						</div>
					</div>
					<h3 class="edit-section-title">
						Your viewing times...
						<span>
							Add viewing times from the panel to your left at the bottom of the
							form. You can remove a viewing time by double clicking the corresponding
							<i class="fa fa-times base-color-opposite"></i>.
					</h3>
					<div class="row">
						<div class="tile tile-full action-viewing-delete">
							<div id="viewing-preview"></div>
						</div>
					</div>
					<div class="row">
						<div class="tile tile-half">
						</div>
					</div>
				</div> <%-- .container-scroll END --%>
			</div> <%-- .span-half END --%>
		</div> <%-- .row END --%>
	</div> <%-- .container END --%>
</main>

<%-- <c:import url="template/footer.jsp" /> --%>
<c:import url="template/~bottom.jsp">
	<c:param name="js" value="editAd" />
</c:import>
